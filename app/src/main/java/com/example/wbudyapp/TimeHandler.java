package com.example.wbudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeHandler extends AppCompatActivity {
    TextView currentTime,calculatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_handler);

        currentTime = findViewById(R.id.CurrentTime);
        calculatedTime = findViewById(R.id.calcTime);

        long startTime = startTimer();
        currentTime.setText(String.valueOf(startTime));

        //5s czekania
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long endTime = calcTime(startTime);
        calculatedTime.setText(String.valueOf(endTime));

    }

    public static long startTimer(){
        return new Date().getTime();
    }

    public static long calcTime(long startTime){

        long endTime = new Date().getTime();
        return endTime - startTime;
    }

}