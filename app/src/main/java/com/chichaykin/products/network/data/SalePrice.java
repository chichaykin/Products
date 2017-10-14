
package com.chichaykin.products.network.data;

import com.google.gson.annotations.SerializedName;

public class SalePrice {

    @SerializedName("amount")
    private String amount;
    @SerializedName("currency")
    private String currency;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "SalePrice{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
