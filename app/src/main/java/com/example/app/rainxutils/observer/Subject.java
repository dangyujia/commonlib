package com.example.app.rainxutils.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Subject
 * @Author DYJ
 * @Date 2020/7/6 11:23
 * @Version 1.0
 * @Description 被观察者
 */
abstract public class Subject {

    private List<Observer> observerList = new ArrayList<>();

    public void attachObserver(Observer observer) {
        observerList.add(observer);
    }

    public void detachObserver(Observer observer) {
        observerList.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.updata();
        }
    }

}
