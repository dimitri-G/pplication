package com.example.vo;

import java.math.BigDecimal;

public class order {
    private String ordertime;
    private String ordernumber;
    private BigDecimal sumprices;
    private String nickname;

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public BigDecimal getSumprices() {
        return sumprices;
    }

    public void setSumprices(BigDecimal sumprices) {
        this.sumprices = sumprices;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
