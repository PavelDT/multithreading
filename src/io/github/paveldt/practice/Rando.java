package io.github.paveldt.practice;

public class Rando extends Thread {

    public Rando() {
        while(true) {
            System.out.println(RandGen.Rando() + RandGen.Rando());
        }
    }

}
