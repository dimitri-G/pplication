package com.example.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pplication.R;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShopAdapter extends ArrayAdapter<Shop> {
    private int resourceId;

    public ShopAdapter(Context context, int textViewResourceId, List<Shop> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Shop shop = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView shopImage = view.findViewById(R.id.shop_image);
        TextView shopName = view.findViewById(R.id.shop_name);
        TextView shopAddr = view.findViewById(R.id.shop_addr);
        TextView shopOthers = view.findViewById(R.id.shop_others);

            //shopAddr.setVisibility(View.GONE);
        shopImage.setImageResource(shop.getShopImageId());
        shopName.setText(shop.getShopName());
        shopAddr.setText(shop.getShopAddr());
        shopOthers.setText(shop.getShopOthers());

        return view;
    }
}
