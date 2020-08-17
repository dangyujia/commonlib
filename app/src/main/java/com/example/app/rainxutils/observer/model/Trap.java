package com.example.app.rainxutils.observer.model;

import com.example.app.rainxutils.observer.Observer;

/**
 * @ClassName Trap
 * @Author DYJ
 * @Date 2020/7/6 11:31
 * @Version 1.0
 * @Description TODO
 */
public class Trap implements Observer {
    @Override
    public void updata() {
        if(inRange()){
            System.out.println("陷阱 困住主角！");
        }
    }
    public boolean inRange() {
        return true;
    }
}
