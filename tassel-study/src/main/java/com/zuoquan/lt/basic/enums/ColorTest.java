package com.zuoquan.lt.basic.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

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
        //values
        Color[] colors = Color.values();
        for (Color color : colors) {
            System.out.println(color.getName());
        }

        //EnumSet
        EnumSet<Color> colorSet = EnumSet.allOf(Color.class);

        for (Color color : colorSet) {
            System.out.println(color.getName());
        }

        //EnumMap
        EnumMap<Color, String> enumMap =  new EnumMap<Color, String>(Color.class);

        enumMap.put(Color.RED, "red");
        enumMap.put(Color.BULE, "bule");
        for (Map.Entry<Color, String> entry: enumMap.entrySet()){
            System.out.println(entry.getKey().name() + ":" + entry.getValue());
        }
    }
}
