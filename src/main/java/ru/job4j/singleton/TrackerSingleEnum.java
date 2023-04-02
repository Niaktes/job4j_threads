package ru.job4j.singleton;

public enum TrackerSingleEnum {

    INSTANCE;

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingleEnum tracker = TrackerSingleEnum.INSTANCE;
    }

}
/* Энергичная загрузка. Объект enum создается при загрузке класса и безопасно публикуется всем клиентам. */