package com.example.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pplication.R;

import java.util.List;

public class commentAdapter extends ArrayAdapter<Comment> {
    private int resourceId;

    public commentAdapter(Context context, int textViewResourceId, List<Comment> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comment comment = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView orderName=view.findViewById(R.id.comOrdNum);
        TextView orderTime=view.findViewById(R.id.comOrdTime);
        TextView orderCom=view.findViewById(R.id.comOrd);
        TextView foodCom=view.findViewById(R.id.comFood);
        RatingBar star=view.findViewById(R.id.ratingBar);
        //commentAddr.setVisibility(View.GONE);
        orderName.setText(comment.getOrderNum());
        orderCom.setText(comment.getOrderComment());
        orderTime.setText(comment.getOrderTime());
        foodCom.setText(comment.getFoodComment());
        star.setRating(comment.getRating());
        return view;
    }
}
