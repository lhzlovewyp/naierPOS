package com.joker.common.test;

/**
 * Created by chenqi on 16/5/2.
 */
public class PushTask implements Runnable{

    public String userName;

    public PushTask(String userName){
        this.userName = userName;
    }

    public void run() {
        System.out.println("正在执行task " + userName);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task执行完毕 " + userName);
    }
}
