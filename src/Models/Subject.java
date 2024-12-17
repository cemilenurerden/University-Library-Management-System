package Models;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observers = new ArrayList<>();

    default void addObserver(Observer observer){
        observers.add(observer);
    }
    default void removeObserver(Observer observer){
        observers.remove(observer);
    }
    default void notifyObserver(String  message){
        for(Observer observer : observers) {
            observer.update(message);
        }
    }

}
