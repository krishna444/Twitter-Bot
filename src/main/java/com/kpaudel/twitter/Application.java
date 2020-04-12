package com.kpaudel.twitter;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Application {
    public static void main(String... args) {
        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("Tweeting..." + Arrays.toString(args));
                    TwitterUtils.retweetTopics(args);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 1000, 5000);

    }

    public void testClass(){
        System.out.println("test");
    }
}
