package com.cac.alex.circleprogress;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.Handler;
import android.widget.Toast;

public class HoldButtonThread implements Runnable {
    private String threadName;
    private ProgressBar circleProgress;
    private Thread thread;
    private TextView resultText;
    private Handler handler = new Handler();
    //Volatile boolean to exit thread
    public static volatile boolean exit = false;

    public HoldButtonThread(String threadName, ProgressBar circleProgress, TextView resultText) {
        this.threadName = threadName;
        this.circleProgress = circleProgress;
        this.resultText = resultText;
    }

    public void start(final Activity activity) {
        // Get instance of Vibrator from current Context
        final Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);

        resultText.setText("You're not done filling progress!");
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                exit = false;
                while (counter <= 100 && !exit) {
                    //runOnUiThread to dynamically edit UI
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circleProgress.setProgress(circleProgress.getProgress()+1);
                            //Vibrate at each quarter progress increment
                            if (circleProgress.getProgress() % 25 == 0) {
                                v.vibrate(400* circleProgress.getProgress()/25);
                            }
                            //On done
                            if (circleProgress.getProgress() == 100) {
                                resultText.setText("Filled progress!");
                                circleProgress.setProgress(0);
                                exit = true;
                            }


                        }
                    });
                    try {
                        Thread.sleep(30);
                    }
                    catch(InterruptedException e) {
                        System.out.println("fdsa");
                    }
                    counter++;

                }
                exit = true;
            }
        });
        thread.start();

    }
    public void run() {
        System.out.println("sfdkl");

    }
}

