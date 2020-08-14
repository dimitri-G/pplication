package com.example.entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pplication.MainOrderActivity;
import com.example.pplication.R;
import com.example.pplication.SearchResultActivity;

import java.util.List;

public class OrderAdapater extends RecyclerView.Adapter<OrderAdapater.ViewHolder>
        implements View.OnClickListener{

    private List<Order> orderList;
    private MainOrderActivity mContext;
    private OnItemClickListener mOnItemClickListener=null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }



    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView ShopImage;
        TextView ShopName;
        TextView OrderState;
        TextView ShopRelate;
        TextView OrderSumprice;

        public ViewHolder(View view){
            super(view);
            ShopImage = (ImageView) view.findViewById(R.id.imageView);
            ShopName = (TextView) view.findViewById(R.id.shop_name);
            OrderState = (TextView) view.findViewById(R.id.shop_state);
            ShopRelate = (TextView) view.findViewById(R.id.shop_relate);
            OrderSumprice=(TextView)view.findViewById(R.id.order_sumprice);
        }
    }

    public OrderAdapater(List<Order> OrderList,MainOrderActivity _mContext){
        this.orderList=OrderList;
        mContext=_mContext;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders,parent,false);
        ViewHolder holder =new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        String[] state={"已取消","已下单","已接受","制作完成","商家确认领取","已完成","投诉中","已评价"};
        final MainOrderActivity activity =mContext;
        final Order order=orderList.get(position);
        holder.ShopImage.setImageResource(order.getShopImage());
        holder.ShopName.setText(order.getShopName());
        holder.OrderState.setText(state[order.getState()]);
        holder.ShopRelate.setText(order.getOrderItemName());
        holder.OrderSumprice.setText(order.getSumprices());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public void onClick(View v) {
        int position = (int)v.getTag();
        //int position = v.getChildAdapterPosition(v);
        if(mOnItemClickListener!=null){
            Toast.makeText(mContext, "Click " + orderList.get((int)v.getTag()), Toast.LENGTH_SHORT).show();
            mOnItemClickListener.OnItemClick(v,position);
        }
    }
}
