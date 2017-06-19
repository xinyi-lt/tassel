package com.zuoquan.lt.basic.enums;

/**
 * Created by Administrator on 2017/6/6.
 */
public class DirectionTest {
    Direction direction;

    public DirectionTest(Direction direction){
        this.direction = direction;
    }

    public void showDirection(){
        switch (direction){
            case NORTH:
                System.out.println("north"); break;
            case SOUTH:
                System.out.println("south"); break;
            case EAST:
                System.out.println("east"); break;
            case WEST:
                System.out.println("wast"); break;
            default:
                System.out.println("...");
                break;

        }
    }


}
