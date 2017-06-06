package com.zuoquan.lt.basic.enums;

import com.zuoquan.lt.entity.User;

/**
 * Created by Administrator on 2017/6/6.
 */
public enum Color {
    RED(1, "red"), GREEN(2, "green"), BULE(3, "bule");

    private int index;
    private String name;
    Color(int index, String name){
        this.index  = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}