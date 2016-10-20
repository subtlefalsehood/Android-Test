package com.example;

/**
 * Created by chenqiuyi on 16/8/4.
 */
class NewThread implements Runnable {
    Thread t;

    NewThread() {
        t = new Thread(this, "Test Thread");
        System.out.println("child thread: " + t);
        t.start();
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("child thread:" + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println("Child interrupted.");
        }

    }
}

public class ThreadTest {
    public static void main(String a[]) {
        new NewThread();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("main thread:" + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println("main interrupted.");
        }
    }
}
