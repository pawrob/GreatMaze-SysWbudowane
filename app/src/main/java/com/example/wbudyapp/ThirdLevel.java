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

public class ThirdLevel extends LevelActivity implements SensorEventListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.third_level);
        super.onCreate(savedInstanceState);//metoda z Activity
        walls.add(findViewById(R.id.wall ) );
        walls.add(findViewById(R.id.wall1 ) );
//        walls.add(findViewById(R.id.wall2 ) );
//        walls.add(findViewById(R.id.wall3 ) );
//        walls.add(findViewById(R.id.wall4 ) );
//        walls.add(findViewById(R.id.wall5 ) );
        walls.add(findViewById(R.id.wall6 ) );
        walls.add(findViewById(R.id.wall7 ) );
        walls.add(findViewById(R.id.wall8 ) );
        walls.add(findViewById(R.id.wall9 ) );
        walls.add(findViewById(R.id.wall10 ) );
        walls.add(findViewById(R.id.wall12 ) );
        walls.add(findViewById(R.id.wall13 ) );
        walls.add(findViewById(R.id.wall14 ) );
        walls.add(findViewById(R.id.wall16 ) );
        walls.add(findViewById(R.id.wall17 ) );
        walls.add(findViewById(R.id.wall18 ) );
        walls.add(findViewById(R.id.wall19 ) );
        walls.add(findViewById(R.id.wall20 ) );
        walls.add(findViewById(R.id.wall21 ) );
        walls.add(findViewById(R.id.left ) );
        walls.add(findViewById(R.id.right ) );
        walls.add(findViewById(R.id.top ) );
        walls.add(findViewById(R.id.bottom ) );

    }
    public void startShakeActivity()
    {
        super.startShakeActivity();
        ShakeActivity.nextLevel = "second";
    }

}
