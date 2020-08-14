package com.example.vo;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class providers {
    private String providerid;
    private String name;
    private String address;
    private String relates;

    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelates() {
        return relates;
    }

    public void setRelates(String relates) {
        this.relates = relates;
    }

}
