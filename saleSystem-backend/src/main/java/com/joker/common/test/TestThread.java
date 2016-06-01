package com.joker.common.test;

/**
 * Created by chenqi on 16/4/24.
 */
public class TestThread {
    public static void main(String[] args){
        new Thread(){
            public void run(){

            }
        }.start();

        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }
}
