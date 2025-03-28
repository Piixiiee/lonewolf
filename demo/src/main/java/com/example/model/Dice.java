package com.example.model;

import java.util.*;

public class Dice {
    private static int getBoundFromType(DiceType type) {
        switch (type) {
            case D20:
                return 20;
            case D12:
                return 12;
            case D10:
                return 10;
            case D8:
                return 8;
            case D6:
                return 6;
            case D4:
                return 4;
            default:
                return 10000000;
        }
    }

    public static int roll(DiceType type) {
        return random.nextInt(getBoundFromType(type)) + (type == DiceType.D10 ? 0 : 1);
    }

    public static int[] roll(DiceType type, int numDice) {
        int[] dices = new int[numDice];
        for (int i = 0; i < numDice; i++) {
            dices[i] = roll(type);
        }

        return dices;
    }

    public static float rollPercentile() {
        var twoD10 = roll(DiceType.D10, 2);
        if (twoD10[0] == 0 && twoD10[1] == 0)
            return 1.00f;
        return twoD10[0] * 0.1f + twoD10[1] * 0.01f;
    }

    private static Random random = new Random();
}
