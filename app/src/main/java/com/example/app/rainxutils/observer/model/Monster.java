package com.example.app.rainxutils.observer.model;

import com.example.app.rainxutils.observer.Observer;

/**
 * @ClassName Monster
 * @Author DYJ
 * @Date 2020/7/6 11:29
 * @Version 1.0
 * @Description TODO
 */
public class Monster implements Observer {
    @Override
    public void updata() {
        if (inRange()){
            System.out.println("怪物 对主角攻击！");
        }
    }

    public boolean inRange() {
        return true;
    }
}
