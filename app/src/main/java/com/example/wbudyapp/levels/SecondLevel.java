package com.example.wbudyapp.levels;

import android.hardware.SensorEventListener;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.wbudyapp.mainMenu.LevelActivity;
import com.example.wbudyapp.R;
import com.example.wbudyapp.mainMenu.ShakeActivity;

import java.util.Date;

import static java.lang.Math.abs;

public class SecondLevel extends LevelActivity implements SensorEventListener {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.second_level);
        super.onCreate(savedInstanceState);//metoda z Activity
        startTime = new Date().getTime();
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
}