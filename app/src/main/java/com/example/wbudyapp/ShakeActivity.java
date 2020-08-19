package com.example.wbudyapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

//                        Toast.makeText(getApplicationContext(),"Shake!" , Toast.LENGTH_SHORT).show();
public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    public String nextLevel;
    private TextView xValue, yValue, zValue,levelInfo;
    private SensorManager sensorManager;
    private Sensor accrelerometerSensor;
    private boolean isAccelerometer,notFirst=false;
    private float currentX, currentY, currentZ, lastX,lastY,lastZ,xDiv,yDiv,zDiv;
    float shakeStep = 5f;
    private Vibrator vibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        levelInfo = findViewById(R.id.level_info);
        xValue = findViewById(R.id.xAxis);
        yValue = findViewById(R.id.yAxis);
        zValue = findViewById(R.id.zAxis);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        nextLevel = intent.getStringExtra("LEVELNUMBER");
        System.out.println(nextLevel);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accrelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometer=true;
        }
        else{
            xValue.setText("Accelerometer is not avaliable");
            isAccelerometer=false;
        }
    }
    public void levelPick(String level){


        if(level.equals("second")){
            Intent startOfGame = new Intent(this,LevelActivity.class);
            startOfGame.putExtra("LEVELNUMBER",2);
            startActivity(startOfGame);
        }
        if(level.equals("third")){
            Intent startOfGame = new Intent(this,LevelActivity.class);
            startOfGame.putExtra("LEVELNUMBER",3);
            startActivity(startOfGame);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        levelInfo.setText("Poziom: "+ nextLevel);
        xValue.setText(sensorEvent.values[0]+"m/s2");
        yValue.setText(sensorEvent.values[1]+"m/s2");
        zValue.setText(sensorEvent.values[2]+"m/s2");
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

            if(notFirst){
                xDiv = Math.abs(lastX-currentX);
                yDiv = Math.abs(lastY-currentY);
                zDiv = Math.abs(lastZ-currentZ);

                    if((xDiv>shakeStep & yDiv>shakeStep)||(xDiv>shakeStep & zDiv>shakeStep) || (yDiv>shakeStep & zDiv>shakeStep)){
                        vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE)); //<----------------------- Tutaj wpisujemy do funkcji to co ma sie dziac po shake

                        levelPick(nextLevel);

                    }
            }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAccelerometer){
            sensorManager.registerListener(this,accrelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isAccelerometer){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}