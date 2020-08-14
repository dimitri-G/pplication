package com.example.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FoodAdapter extends ArrayAdapter<Food> {
    private int resourceId;

    public FoodAdapter(Context context, int textViewResourceId, List<Food> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView foodImage = view.findViewById(R.id.food_image);
        TextView foodName = view.findViewById(R.id.food_name);
        TextView foodPrice = view.findViewById(R.id.food_price);


        foodImage.setImageResource(food.getImageId());
        foodName.setText(food.getFoodName());
        foodPrice.setText(String.valueOf(food.getFoodPrice()));


        return view;
    }
}
