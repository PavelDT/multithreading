package io.github.paveldt.booleanthread;

import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicBoolean;

public class Runner {

    public static void main(String[] args) {
        Runner r = new Runner();
        r.runApp();
    }

    public void runApp() {


        RandomAccessFile a = null;
        try {
            a = new RandomAccessFile("file.txt", "rw");
            a.seek(0);
            a.write(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Application started");
        AtomicBoolean inUse = new AtomicBoolean(false);
        // create t1
        BooleanThread t1 = new BooleanThread(inUse,"a", a);
        // create t2
        BooleanThread t2 = new BooleanThread(inUse, "b", a);

        // start threads
        t1.start();
        t2.start();


        try {
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
