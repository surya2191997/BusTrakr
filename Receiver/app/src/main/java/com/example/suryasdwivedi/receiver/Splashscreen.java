package com.example.suryasdwivedi.receiver;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.logging.Handler;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CountDownTimer(5000, 1000) {

            Intent intent = new Intent(Splashscreen.this, receiver.class);
            public void onFinish() {
               // Intent intent = new Intent(Splashscreen.this, receiver.class);
                startActivity(intent);
                finish();

            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();
    }


}


