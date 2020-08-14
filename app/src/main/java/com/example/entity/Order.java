package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String orderTime;
    private String sumprices;
    private String shopName;
    private int shopImage;
    private List<OrderRelate> OR= new ArrayList<>();
    private int state;
    private String orderItemName="";

    public Order(){
    }
    public Order(String _orderId,String _orderTime,
                 String _sumprices,int _state,String _shop,int _shopImage,String a){
        orderId=_orderId;
        orderTime=_orderTime;
        sumprices=_sumprices;
        state=_state;
        shopName=_shop;
        shopImage=_shopImage;
        String[] spite=a.split("-");
        for(int i=0;i<spite.length/3;i++)
        {
            orderItemName+=spite[3*i]+"x"+spite[3*i+1]+"\n";
            OrderRelate or=new OrderRelate(spite[3*i],
                    Integer.parseInt(spite[3*i+1]),Double.parseDouble(spite[2+3*i]));
            OR.add(or);
        }
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<OrderRelate> getOR() {
        return OR;
    }

    public int getShopImage() {
        return shopImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderItemName(){
        return orderItemName;
    }

    public String getOrderId(){
        return orderId;
    }

    public String getOrderTime(){
        return  orderTime;
    }

    public  String getSumprices(){
        return sumprices;
    }

    public int getState() {
        return state;
    }

}
