package io.github.paveldt.sleepandnotify;

public class Runner {

    public static void main(String[] args) {
        Runner r = new Runner();
        r.runApp();
    }

    public void runApp() {

        // Monitor object used for notification.
        // this object is extremely important, it's the connection between this
        // thread (main thread) and the thread we'll be making wait (t1 thread)
        // the object is used for both synchronization blocks as well.
        Object monitor = new Object();

        // create t1
        WaitThread t1 = new WaitThread("A", monitor);

        // start threads
        t1.start();


        try {

            while (t1.isAlive()) {
                // every 1 second make the t1 thread continue working
                // by using the monitor object to notify it to wake up and execute
                Thread.sleep(1000);

                // this is left commented out, but will cause a DEADLOCK if added in.
                // wait() is a blocking function, the main thread will sit here
                // and just wait until some other thread notifies it, but no other
                // thread will notify it... thus it's blocked forever, aka deadlock.
                // synchronized (monitor) {
                //     monitor.wait();
                // }
                // System.out.println("UNREACHABLE DUE TO DEADLOCK");

                // notify and wait HAVE TO BE IN SYNC BLOCKS
                // the sync block WILL BE on the object used for notifying / awaking
                synchronized (monitor) {
                    monitor.notify();
                    System.out.println("notified from main");
                }
            }

            // catch generic exception that will basically catch everything
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Application completed");
    }
}
