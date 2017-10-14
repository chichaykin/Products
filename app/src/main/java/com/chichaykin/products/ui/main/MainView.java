package com.chichaykin.products.ui.main;


import com.chichaykin.products.network.data.Category;
import com.chichaykin.products.network.data.Product;
import com.chichaykin.products.ui.detail.ProductModel;

import java.util.List;

interface MainView {

    void showError(String message);

    void setData(int selected, List<Category> products);

    void setProducts(List<Product> products);

    void showProductDetail(ProductModel model);
}
