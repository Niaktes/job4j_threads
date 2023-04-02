package ru.job4j.singleton;

public class TrackerSingleFinal {

    private static final TrackerSingleFinal INSTANCE = new TrackerSingleFinal();

    private TrackerSingleFinal() {
    }

    public static TrackerSingleFinal getInstance() {
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingleFinal tracker = TrackerSingleFinal.getInstance();
    }

}
/* Энергичная загрузка. Статический объект поля создается при загрузке класса и получается через getInstance() */