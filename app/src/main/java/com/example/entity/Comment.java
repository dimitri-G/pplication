package com.example.entity;

public class Comment {
    String orderNum;
    String orderTime;
    String orderComment;
    float rating;
    String foodComment;

    public Comment(){

    }

    public Comment(String _orderNum,String _orderTime,String _orderComment,float _rating,String a){
        orderComment=_orderComment;
        orderNum=_orderNum;
        orderTime=_orderTime;
        rating=_rating;
        foodComment=a;
    }

    public float getRating() {
        return rating;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getFoodComment() {
        return foodComment;
    }
}

