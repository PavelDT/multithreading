package io.github.paveldt.threading;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicThread extends Thread {
    // AtomicIntger is initialized from Runner and passed in here. It has to be shared
    // between the two threads writing to the file
    AtomicInteger count;
    // the thread's name
    String name;
    RandomAccessFile raf;

    // the constructor
    // takes 3 parameters
    // an AtomicInteger, a String and a RandomAccessFile
    public AtomicThread(AtomicInteger countFromRunner, String nameFromRunner, RandomAccessFile rafFromRunner) {
        count = countFromRunner;
        name = nameFromRunner;
        raf = rafFromRunner;
    }

    @Override
    public void run() {

        for (int i = 0; i < 15; i++) {
            // you have to synchronize on a object that is passed in from outside
            // the object that is passed in via the constructor HAS TO BE shared between the threads
            //
            // we can use count or raf here, as they are both shared between the threads
            // and passed in from outside of this class
            synchronized (count) {

                try {
                    // remember to seek before every random access file operation
                    // seek(0) resets where we start reading from
                    raf.seek(0);
                    int val = raf.read();
                    raf.seek(0);
                    // increment on a single line, avoid using val++
                    val = val + 1;
                    raf.write(val);

                    // AtomicInteger is complicated, you can't just ++ an AtomicInteger
                    // use incrementAndGet instead of ++
                    // this will increment the AtomicInteger and return the incremented value
                    int countVal = count.incrementAndGet();

                    // we're going to re-read the value to insure the success of the write.
                    raf.seek(0);
                    System.out.println(name + " " + countVal + " t:" + raf.read());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // this is just to make the process human-readable
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
