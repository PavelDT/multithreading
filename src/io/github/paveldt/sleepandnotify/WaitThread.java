package io.github.paveldt.sleepandnotify;

// this thread will wait...
public class WaitThread extends Thread {

    private String name;
    private Object monitor;

    // this thread will wait for Runner's thread to notify it to continue working
    public WaitThread(String nameFromRunner, Object monitorFromRunner) {
        name = nameFromRunner;
        monitor = monitorFromRunner;
    }

    @Override
    public void run() {

        try {
            // the loop length has no significance
            for (int i = 0; i < 5; i++) {
                // just displays a time stamp
                System.out.println("Current time: " + System.currentTimeMillis());

                synchronized (monitor) {
                    System.out.println("starting to wait from child");
                    monitor.wait();
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Thread was interrupted " + ex.getMessage());
            // ex.printStackTrace();
        }
    }

}
