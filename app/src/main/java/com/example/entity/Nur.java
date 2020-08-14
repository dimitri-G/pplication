package com.example.entity;

public class Nur {
    String title;
    String real;
    String std;

    public Nur(){

    }

    public Nur(String _title,String _real,String _std){
        title=_title;
        real=_real;
        std=_std;
    }

    public String getReal() {
        return real;
    }

    public String getStd() {
        return std;
    }

    public String getTitle() {
        return title;
    }
}
