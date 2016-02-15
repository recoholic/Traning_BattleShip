package com.digitalgame.battleship;

import java.util.Random;

public class Util {
    public static int getRandomValue(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}