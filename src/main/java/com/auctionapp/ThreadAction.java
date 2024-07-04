package com.auctionapp;

import java.util.Scanner;

public class ThreadAction implements Runnable {
    private int time;

    public ThreadAction(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        count(time);
    }

    public void count(int time) {
        for (int i = time; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Starting countdown: " + i + " seconds");
        }
    }
}
