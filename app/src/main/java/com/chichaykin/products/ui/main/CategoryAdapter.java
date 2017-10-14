package com.chichaykin.products.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.chichaykin.products.R;
import com.chichaykin.products.network.data.Category;

import java.util.ArrayList;


class CategoryAdapter extends ArrayAdapter<Category> implements ThemedSpinnerAdapter {
    private static final int LAYOUT = android.R.layout.simple_list_item_1;
    private final ThemedSpinnerAdapter.Helper dropDownHelper;
    private final LayoutInflater layoutInflater;

    public CategoryAdapter(Context context) {
        super(context, LAYOUT, new ArrayList<>());
        dropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = dropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(LAYOUT, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getName());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = layoutInflater.inflate(LAYOUT, parent, false);
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setTextColor(Color.WHITE);

        } else{
            view = convertView;
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getName());

        return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return dropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        dropDownHelper.setDropDownViewTheme(theme);
    }
}