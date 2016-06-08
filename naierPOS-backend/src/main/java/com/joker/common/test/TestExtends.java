package com.joker.common.test;

import com.joker.core.dto.SystemLog;

/**
 * Created by chenqi on 16/4/27.
 */
public class TestExtends {
    public static void main(String[] args){

        EX t = new EX();
    }

    int i;

    TestExtends(){
        i = 1;
        int x = getXX();
        System.out.println(x);
    }

    int getXX(){
        return i;
    }
}

class EX extends TestExtends{
    int i;
    EX(){
        i = 2;
        int x = getXX();
    }

    int getXX(){
        return i;
    }
}
