package com.setarit.quietticketscanner.domain.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Setarit on 16/10/2017.
 * Subject
 */

public class Subject {
    private List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(Observer observer: observers){
            observer.update();
        }
    }
}
