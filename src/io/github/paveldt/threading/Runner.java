package io.github.paveldt.threading;

import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;

public class Runner {

    public static void main(String[] args) {
        Runner r = new Runner();
        r.runApp();
    }

    public void runApp() {
        AtomicInteger globalCount = new AtomicInteger(0);

        RandomAccessFile a = null;
        try {
            a = new RandomAccessFile("file.txt", "rw");
            a.seek(0);
            a.write(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Application started");
        // create t1
        AtomicThread t1 = new AtomicThread(globalCount,"a", a);
        // create t2
        AtomicThread t2 = new AtomicThread(globalCount, "b", a);

        // start threads
        t1.start();
        t2.start();
        


        try {
            // you dont need to force threads to "join" back into the main thread
            // i do this so that we can see the print statement "Application Completed" come
            // after the threads complete
            t1.join();
            t2.join();

            // I also want to close the RandomAccessFile only once the two threads are done using it
            // this is where the join() function becomes useful.
            // it lets me wait until the two threads are done, so that i can safely close the RandomAccessFile
            a.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Application completed");
    }
}
