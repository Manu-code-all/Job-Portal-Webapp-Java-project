package com.example.jobportal.worker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationWorker implements Runnable {

    private static final Queue<String> queue = new ConcurrentLinkedQueue<>();
    private volatile boolean running = true;

    public static void enqueue(String msg){
        queue.add(msg);
    }

    public void shutdown(){ running=false; }

    @Override
    public void run() {
        while (running) {
            String msg = queue.poll();
            if (msg != null) {
                // send email/notification (mock)
                System.out.println("Sending notification: " + msg);
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            } else {
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }
}
