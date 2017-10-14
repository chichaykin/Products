package com.chichaykin.products.ui.main;


import com.chichaykin.products.network.data.Product;

public interface MainPresenter  {

    void onAttach(MainView view);

    void onDetach();

    void handleCategorySelection(int position);

    void handleRefresh();

    void handleProductSelection(Product product);
}
