package com.example.app.rainxutils.observer.model;

import com.example.app.rainxutils.observer.Observer;

/**
 * @ClassName Treasure
 * @Author DYJ
 * @Date 2020/7/6 11:32
 * @Version 1.0
 * @Description TODO
 */
public class Treasure implements Observer {
    @Override
    public void updata() {
        if (inRange()) {
            System.out.println("宝物 为主角加血！");
        }
    }

    public boolean inRange() {
        return true;
    }
}
