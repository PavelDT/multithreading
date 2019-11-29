package io.github.paveldt.practice;

import java.util.Random;

public class RandGen {
    public static char Rando() {
        Random rand = new Random();
        char num = (char) (100 + rand.nextInt(99));
        return num;
    }
}
