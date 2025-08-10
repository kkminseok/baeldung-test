package com.my.init;

import java.util.Random;

public class RandomIdEntity extends Entity{
    final Random random = new Random();

    @Override
    protected int generateId() {
        return random.nextInt();
    }
}
