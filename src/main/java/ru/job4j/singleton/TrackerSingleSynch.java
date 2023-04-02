package ru.job4j.singleton;

public class TrackerSingleSynch {

    private static TrackerSingleSynch instance;

    private TrackerSingleSynch() {
    }

    public static synchronized TrackerSingleSynch getInstance() {
        if (instance == null) {
            instance = new TrackerSingleSynch();
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingleSynch tracker = TrackerSingleSynch.getInstance();
    }

}
/* Ленивая загрузка. Инициализация и проверка экземпляра происходит внутри критической секции. Этот шаблон деградирует производительность. !! НЕ РЕКОМЕНДУЕТСЯ !! */