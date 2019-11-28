package io.github.paveldt.booleanthread;

import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanThread extends Thread {
    // AtomicBoolean used to track what thread is allowed to execute
    AtomicBoolean inUse;
    // the thread's name
    String name;
    RandomAccessFile raf;

    // the constructor
    // takes 3 parameters
    // an AtomicInteger, a String and a RandomAccessFile
    public BooleanThread(AtomicBoolean inUseFromRunner, String nameFromRunner, RandomAccessFile rafFromRunner) {
        inUse = inUseFromRunner;
        name = nameFromRunner;
        raf = rafFromRunner;
    }

    @Override
    public void run() {

        int loopCounter = 0;
        while (loopCounter < 50) {

            // only one thread is allowed to execute
            if (inUse.get() == false) {
                System.out.println("EXECUTING thread " + name);
                // create a manual version of a lock by setting AtomicBoolean to true
                // this will prevent other threads from accessing the code block.
                inUse.set(true);
                try {
                    raf.seek(0);
                    int val = raf.read();
                    raf.seek(0);
                    // increment on a single line, avoid using val++
                    val = val + 1;
                    raf.write(val);

                    System.out.println(name + " total: " + val);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // increment the counter
                loopCounter++;

                // release the manually created lock by setting the AtomicBoolean to false
                inUse.set(false);

            } else {
                System.out.println("BLOCKED thread " + name);
            }
        }





    }
}
