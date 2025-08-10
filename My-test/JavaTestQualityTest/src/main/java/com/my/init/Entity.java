package com.my.init;

public class Entity {
    private final int id;

    Entity() {
        id = generateId();
    }

    protected int generateId() {
        return 0;
    }

}
