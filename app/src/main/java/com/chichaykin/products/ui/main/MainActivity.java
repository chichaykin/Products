package com.chichaykin.products.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chichaykin.products.App;
import com.chichaykin.products.R;
import com.chichaykin.products.network.data.Category;
import com.chichaykin.products.network.data.Product;
import com.chichaykin.products.ui.detail.ProductDetailsActivity;
import com.chichaykin.products.ui.detail.ProductModel;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainView, ProductListFragment.ProductClickListener {

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MainPresenter presenter;

    ProductListFragment fragment;
    //CategoryAdapter spinnerAdapter;
    CategoryAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ((App) getApplication()).getComponent().inject(this);

        setupSpinnerAdapter();

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.handleRefresh());
        swipeRefreshLayout.setRefreshing(true);

        fragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.product_list);
    }

    private void setupSpinnerAdapter() {
        spinnerAdapter = new CategoryAdapter(this);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.handleCategorySelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Timber.d("onNothingSelected");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(int selected, List<Category> categories) {
        spinnerAdapter.clear();
        spinnerAdapter.addAll(categories);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setSelection(selected);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setProducts(List<Product> products) {
        fragment.setProducts(products);
    }

    @Override
    public void showProductDetail(ProductModel model) {
        ProductDetailsActivity.startActivity(this, model);
    }

    @Override
    public void onClick(Product product) {
        presenter.handleProductSelection(product);
    }
}