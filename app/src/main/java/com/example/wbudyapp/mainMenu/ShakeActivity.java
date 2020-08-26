package com.example.wbudyapp.mainMenu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.wbudyapp.R;
import com.example.wbudyapp.levels.SecondLevel;
import com.example.wbudyapp.levels.ThirdLevel;

import java.util.Vector;

//                        Toast.makeText(getApplicationContext(),"Shake!" , Toast.LENGTH_SHORT).show();
public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    public static String nextLevel;
    private TextView xValue, yValue, zValue,levelInfo,scoreInfo;
    private SensorManager sensorManager;
    private Sensor magnetometerSensor;
    private boolean isMagnetometer,notFirst=false;
    private float currentX, currentY, currentZ, lastX,lastY,lastZ,xDiv,yDiv,zDiv;
    float shakeStep = 5f;
    private Vibrator vibrator;
    public static long time;
    public static TextView lvltime;
    private Vector<Long> points;
//    private long ;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        levelInfo = findViewById(R.id.level_info);
        xValue = findViewById(R.id.xAxis);
        yValue = findViewById(R.id.yAxis);
        zValue = findViewById(R.id.zAxis);
        scoreInfo = findViewById(R.id.totalScore);

        lvltime = findViewById(R.id.lvltime);
//        time= (long) pow((Math.E),1/time*3) ;
        LevelActivity.totalScore=+time;
        lvltime.setText("Score: " + time);
        scoreInfo.setText("Total score: " + LevelActivity.totalScore);
//        time = 0;


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!=null){
            magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            isMagnetometer =true;
        }
        else{
            xValue.setText("Accelerometer is not avaliable");
            isMagnetometer =false;
        }
    }
    public void levelPick(String level){

        if(level.equals("second")){
            Intent startOfGame = new Intent(this, SecondLevel.class);
            startActivity(startOfGame);
        }
        else if(level.equals("third")){
            Intent startOfGame = new Intent(this, ThirdLevel.class);
            startActivity(startOfGame);
        }
        else{
            Intent startOfGame = new Intent(this,MainActivity.class);
            startActivity(startOfGame);
        }
    }

    @SuppressLint("SetTextI18n")
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
                        vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));

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
        if(isMagnetometer){
            sensorManager.registerListener(this, magnetometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isMagnetometer){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}