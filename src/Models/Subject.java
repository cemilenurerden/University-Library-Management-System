package Models;

import Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observers = new ArrayList<>();
    UserRepository userRepository = new UserRepository();

    default void addObserver(Observer observer){
        observers.add(observer);
    }
    default void removeObserver(Observer observer){
        observers.remove(observer);
    }
    default void notifyObserver(String  message){
        List<StudentModel> allStudents = userRepository.getAllStudents();
        for (StudentModel student : allStudents) {
            addObserver(student); // Observer olarak ekle
        }
    }

}
