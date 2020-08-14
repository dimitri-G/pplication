package com.example.entity;

public class Food {
    private String foodName;
    private int foodPrice;
    private int imageId;

    public Food(String _foodName, int _foodPrice,int _imageId){
        this.foodName = _foodName;
        this.foodPrice = _foodPrice;
        this.imageId = _imageId;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }



    public String getFoodName() {
        return foodName;
    }

    public  void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }
}
