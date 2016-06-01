package com.joker.common.test;

import com.joker.core.dto.SystemLog;

/**
 * Created by chenqi on 16/4/27.
 */
public class TestSwitch {
    public static void main(String[] args){
        int i = 2;
        switch (i){
            case 1 :
                System.out.println("one");
            case 2 :
                System.out.println("two");
            case 3 :
                System.out.println("three");
            default:
                System.out.println("default");
        }
    }
}
