package com.chichaykin.products.ui.main;

import com.chichaykin.products.network.ConnectivityHelper;
import com.chichaykin.products.network.NetworkApi;
import com.chichaykin.products.network.data.Category;
import com.chichaykin.products.network.data.Product;
import com.chichaykin.products.ui.detail.ProductModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class MainPresenterImpl implements MainPresenter {
    private static String BASE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com";

    private final CompositeDisposable subscription = new CompositeDisposable();
    private final NetworkApi api;
    private final ConnectivityHelper helper;

    private MainView view;
    private List<Category> cachedCategories;
    private int selectedCategory;

    public MainPresenterImpl(NetworkApi api, ConnectivityHelper helper) {
        this.api = api;
        this.helper = helper;
    }

    @Override
    public void onAttach(MainView view) {
        this.view = view;
        updateView();
    }

    private void updateView() {
        if (cachedCategories == null) {
            refreshData();
        } else {
            view.setData(selectedCategory, cachedCategories);
        }
    }

    @Override
    public void onDetach() {
        view = null;
        subscription.clear();
    }

    @Override
    public void handleCategorySelection(int position) {
        selectedCategory = position;
        view.setProducts(cachedCategories.get(position).getProducts());
    }

    @Override
    public void handleRefresh() {
        refreshData();
    }

    @Override
    public void handleProductSelection(Product product) {
        view.showProductDetail(new ProductModel.Builder()
                .setName(product.getName())
                .setUrl(product.getUrl())
                .setSalePrice(product.getSalePrice())
                .build());
    }

    private void refreshData() {
        subscription.add(
                api.getDatum()
                        .subscribeOn(Schedulers.io())
                        .doOnNext(category -> patchUrlField(category))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> handleSuccess(categories),
                                error -> handleError(error))
        );
    }

    private void patchUrlField(List<Category> categories) {
        categories.forEach(category -> category.getProducts().forEach(product -> updateUrl(product)));
    }

    private void updateUrl(Product product) {
        String fullUrl = getFullUrl(BASE_URL, product.getUrl());
        product.setUrl(fullUrl);
    }

    private void handleError(Throwable error) {
        error.printStackTrace();

        if (view == null) {
            return;
        }

        if (helper.isNetworkAvailable()) {
            view.showError("Server is not available");
        } else {
            view.showError("No internet");
        }
    }

    private void handleSuccess(List<Category> categories) {
        cachedCategories = Collections.emptyList();

        if (categories == null || categories.isEmpty()) {
            return;
        }

        cachedCategories = categories;

        if (selectedCategory >= cachedCategories.size()) {
            selectedCategory = 0;
        }

        if (view == null) {
            return;
        }

        view.setData(selectedCategory, cachedCategories);
    }

    public static String getFullUrl(String mBaseUrl, String file) {
        URL url1;
        try {
            url1 = new URL(mBaseUrl);
            URL url2 = new URL(url1.getProtocol(), url1.getHost(), url1.getPort(), file, null);
            return url2.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
