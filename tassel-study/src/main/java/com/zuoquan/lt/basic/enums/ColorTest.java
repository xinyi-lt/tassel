package com.zuoquan.lt.basic.enums;

/**
 * Created by Administrator on 2017/6/6.
 */
public class ColorTest {

    public void printColor(Color color){
        switch (color){
            case RED:
                System.out.println(color.getName());
                break;
            case BULE:
                System.out.println(color.getName());
                break;
            case GREEN:
                System.out.println(color.getName());
                break;
        }
    }

    public static void main(String[] args){
        new ColorTest().printColor(Color.RED);
        Color[] colors = Color.values();
        for (Color color : colors) {
            System.out.println(color.getName());
        }

    }
}
