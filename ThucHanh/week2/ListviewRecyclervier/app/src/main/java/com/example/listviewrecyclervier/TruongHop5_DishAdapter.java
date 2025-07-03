package com.example.listviewrecyclervier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TruongHop5_DishAdapter extends ArrayAdapter<TruongHop5_Dish> {

    private Activity context;

    public TruongHop5_DishAdapter(Activity context, int layoutID, List<TruongHop5_Dish> dishes) {
        super(context, layoutID, dishes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.truonghop5_dish, parent, false);
        }

        TruongHop5_Dish dish = getItem(position);
        ImageView dishImage = convertView.findViewById(R.id.dish_image);
        TextView dishName = convertView.findViewById(R.id.dish_name);
        ImageView promoIcon = convertView.findViewById(R.id.promo_icon);



        dishImage.setImageResource(dish.getImageResource());
        dishName.setText(dish.getName());
        promoIcon.setVisibility(dish.hasPromotion() ? View.VISIBLE : View.GONE);

        return convertView;
    }
}