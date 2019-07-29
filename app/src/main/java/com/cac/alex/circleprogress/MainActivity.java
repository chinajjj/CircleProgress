package com.cac.alex.circleprogress;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ProgressBar circularProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        final TextView resultText = (TextView) findViewById(R.id.resultTxt);
        final Activity activity = this;


        //Gets drawable used for determinant progress bar
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);

        circularProgress.setProgress(0);   // Main Progress
        circularProgress.setSecondaryProgress(100); // Secondary Progress
        circularProgress.setMax(100); // Maximum Progress
        circularProgress.setProgressDrawable(drawable);




        circularProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Begin thread on pressing button
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    HoldButtonThread thread = new HoldButtonThread("Threaady", circularProgress, resultText);
                    thread.start(activity);
                }
                //End thread on release
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    HoldButtonThread.exit = true;
                    circularProgress.setProgress(0);
                }
                return true;
            }
        });




    }
}