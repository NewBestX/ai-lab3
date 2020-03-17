package me.ai.ga;

import java.util.Random;

class Utils {
    private static Random random = new Random();

    protected static Integer getRandomCommunity(int maxN) {
        return random.nextInt(maxN) + 1;
    }

    protected static Random getRandom() {
        return random;
    }
}
