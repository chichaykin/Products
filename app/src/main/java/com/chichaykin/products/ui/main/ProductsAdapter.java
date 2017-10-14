package com.chichaykin.products.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chichaykin.products.R;
import com.chichaykin.products.network.data.Category;
import com.chichaykin.products.network.data.Product;
import timber.log.Timber;

import java.util.List;

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    private List<Product> products;
    private final View.OnClickListener onClickListener;

    ProductsAdapter(List<Product> products, View.OnClickListener onClickListener) {
        this.products = products;
        this.onClickListener = onClickListener;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_holder, parent, false);

        v.setOnClickListener(onClickListener);

        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.textView.setText(product.getName());
        Context context = holder.textView.getContext();
        SimpleTarget<Drawable> target = new SimpleTarget<Drawable>(100, 100) {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, resource, null);
            }
        };

        Timber.d("Try loading picture: %s", product.getUrl());

        Glide.with(context)
                .load(product.getUrl())
                .into(target);
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
    }

    public Product getProduct(int itemPosition) {
        return products.get(itemPosition);
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        public TextView textView;

        public ProductHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
