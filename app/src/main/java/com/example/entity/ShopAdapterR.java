package com.example.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pplication.R;
import com.example.pplication.SearchResultActivity;
import com.example.shoppingcart.ShoppingCartActivity;

import java.util.List;

public class ShopAdapterR extends RecyclerView.Adapter<ShopAdapterR.ViewHolder> implements View.OnClickListener{

    private List<Shop> mShopList;
    private ButtonInterface buttonInterface;
    private SearchResultActivity mContext;
    private OnItemClickListener mOnItemClickListener;

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int postition);
    }
    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if(mOnItemClickListener!=null){
        Toast.makeText(mContext, "Click " + mShopList.get((int)v.getTag()), Toast.LENGTH_SHORT).show();
        mOnItemClickListener.OnItemClick(v,position);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick( View view,int position);
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ShopImage;
        TextView ShopName;
        Button ShopButton;


        public ViewHolder (View view)
        {
            super(view);
            ShopImage = (ImageView) view.findViewById(R.id.shop_image);
            ShopName = (TextView) view.findViewById(R.id.shop_name);
            ShopButton=(Button) view.findViewById(R.id.shop_button);
        }

    }

    public  ShopAdapterR (List<Shop> ShopList,SearchResultActivity _mContext){
        mShopList = ShopList;
        mContext=_mContext;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.r_shop_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final SearchResultActivity activity = mContext;
        final Shop Shop = mShopList.get(position);
        holder.ShopImage.setImageResource(Shop.getShopImageId());
        holder.ShopName.setText(Shop.getShopName());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount(){
        return mShopList.size();
    }
}