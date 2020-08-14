package com.example.entity;

import java.util.List;

public class OrderRelate {
    private String food;
    private int num;
    private double goodPrice;

    public OrderRelate(String _food,int _num,double _goodPrice){
        food=_food;
        num=_num;
        goodPrice=_goodPrice;
    }

    public OrderRelate(){}

    public double getGoodPrice() {
        return goodPrice;
    }

    public int getNum() {
        return num;
    }

    public String getFood() {
        return food;
    }
}

