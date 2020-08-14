package com.example.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pplication.R;

import java.util.List;

public class OrderRelateAdapter extends ArrayAdapter<OrderRelate> {
    private int resourceId;
   public OrderRelateAdapter(Context context, int textView,List<OrderRelate> ORList){
       super(context,textView,ORList);
       resourceId = textView;
   }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrderRelate or = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView goodImage = view.findViewById(R.id.good_image);
        TextView goodName = view.findViewById(R.id.good_name);
        TextView goodNum = view.findViewById(R.id.good_num);
        TextView goodSumprice = view.findViewById(R.id.good_sumprice);

        //shopImage.setImageResource(shop.getShopImageId());
        goodName.setText(or.getFood());
        goodNum.setText("x"+String.valueOf(or.getNum()));
        goodSumprice.setText("￥"+String.valueOf(or.getGoodPrice()*or.getNum()));

        return view;
    }
}
