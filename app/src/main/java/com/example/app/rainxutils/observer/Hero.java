package com.example.app.rainxutils.observer;

/**
 * @ClassName Hero
 * @Author DYJ
 * @Date 2020/7/6 11:34
 * @Version 1.0
 * @Description TODO
 */
public class Hero extends Subject {

    public void move(){
        System.out.println("主角向前移动");
        notifyObservers();
    }
}
