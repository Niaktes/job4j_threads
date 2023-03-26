package ru.job4j.threads;

public class SingletonRaceCondition {

    private static SingletonRaceCondition instance;

    private SingletonRaceCondition() {
    }

    public static SingletonRaceCondition getInstance() {
        if (instance == null) {
            instance = new SingletonRaceCondition();
        }
        return instance;
    }

}