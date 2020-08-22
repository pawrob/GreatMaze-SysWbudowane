package com.example.wbudyapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FirstLevel extends LevelActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.first_level);
        super.onCreate(savedInstanceState);//metoda z Activity


        walls.add(findViewById(R.id.wall1 ) );
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
