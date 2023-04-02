package ru.job4j.singleton;

public class TrackerSingleHolder {

    private TrackerSingleHolder() {
    }

    public static TrackerSingleHolder getInstance() {
        return Holder.INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    private static final class Holder {
        private static final TrackerSingleHolder INSTANCE = new TrackerSingleHolder();
    }

    public static void main(String[] args) {
        TrackerSingleHolder tracker = TrackerSingleHolder.getInstance();
    }

}
/* Ленивая загрузка. Его работа стабильна и не влияет на производительность системы. */