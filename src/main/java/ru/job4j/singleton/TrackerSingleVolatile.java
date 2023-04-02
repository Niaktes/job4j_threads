package ru.job4j.singleton;

public class TrackerSingleVolatile {

    private static volatile TrackerSingleVolatile INSTANCE;

    private TrackerSingleVolatile() {
    }

    public static TrackerSingleVolatile getInstance() {
        if (INSTANCE == null) {
            synchronized (TrackerSingleVolatile.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrackerSingleVolatile();
                }
            }
        }
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingleVolatile tracker = TrackerSingleVolatile.getInstance();
    }

}
/* Ленивая загрузка.
Поле экземпляра обозначено volatile. Это обеспечит решение проблемы видимости после инициализации поля.
Первая проверка экземпляра идет до блока синхронизации, что позволяет улучшить скорость работы по сравнению с single checked locking реализацией.
В критической секции происходит инициализация экземпляра и запись его в переменную.
Этот шаблон использовать !! не рекомендуется !!. Он уменьшает производительность системы при многопроцессорном окружении.
 */