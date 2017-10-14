package com.chichaykin.products.ui.detail;


import android.os.Parcel;
import android.os.Parcelable;
import com.chichaykin.products.network.data.SalePrice;

public class ProductModel implements Parcelable {
    private String name;
    private String url;
    private String amount;
    private String currency;

    private ProductModel(String name, String url, SalePrice salePrice) {
        this.name = name;
        this.url = url;
        this.amount = salePrice.getAmount();
        this.currency = salePrice.getCurrency();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.amount);
        dest.writeString(this.currency);
    }

    protected ProductModel(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.amount = in.readString();
        this.currency = in.readString();
    }

    public static final Parcelable.Creator<ProductModel> CREATOR = new Parcelable.Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public static class Builder {
        private String name;
        private String url;
        private SalePrice salePrice;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setSalePrice(SalePrice salePrice) {
            this.salePrice = salePrice;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(name, url, salePrice);
        }
    }
}
