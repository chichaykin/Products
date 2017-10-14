package com.chichaykin.products.ui.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.chichaykin.products.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductDetailsActivityFragment extends Fragment {

    static final String PARAM_MODEL = "model";

    @BindView(R.id.name)
    TextView nameView;

    @BindView(R.id.price_value)
    TextView priceView;

    @BindView(R.id.image)
    ImageView imageView;


    public ProductDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, view);

        ProductModel model = getActivity().getIntent().getParcelableExtra(PARAM_MODEL);
        nameView.setText(model.getName());

        String price = String.format("%s %s", model.getAmount(), model.getCurrency());
        priceView.setText(price);

        Glide.with(getContext())
                .load(model.getUrl())
                .into(imageView);
        return view;
    }
}
