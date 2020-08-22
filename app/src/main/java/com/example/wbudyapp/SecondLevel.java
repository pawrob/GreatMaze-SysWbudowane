package com.example.wbudyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.logging.Level;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class SecondLevel extends LevelActivity implements SensorEventListener {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.second_level);
        super.onCreate(savedInstanceState);//metoda z Activity

        walls.add(findViewById(R.id.wall1));
        walls.add(findViewById(R.id.wall2));
        walls.add(findViewById(R.id.wall3));
        walls.add(findViewById(R.id.wall4));
        walls.add(findViewById(R.id.wall5));
        walls.add(findViewById(R.id.left));
        walls.add(findViewById(R.id.right));
        walls.add(findViewById(R.id.top));
        walls.add(findViewById(R.id.bottom));
    }
    public void startShakeActivity()
    {
        super.startShakeActivity();
        ShakeActivity.nextLevel = "third";
    }
    public void startShakeActivity()
    {
        super.startShakeActivity();
        ShakeActivity.nextLevel = "third";
    }
}