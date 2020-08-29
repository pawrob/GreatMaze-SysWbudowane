package com.example.wbudyapp.levels;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.example.wbudyapp.functions.GpsActivity;
import com.example.wbudyapp.functions.GpsTracker;
import com.example.wbudyapp.functions.TimeHandler;
import com.example.wbudyapp.mainMenu.ShakeActivity;
import com.example.wbudyapp.mainMenu.TestLevels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbudyapp.R;

import java.util.Date;

import static java.lang.Math.abs;

public class BossLevel extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope, proximitySensor, lightSensor, magnetometerSensor;
    private long score = 10000, maxHp = 10000;
    private TextView scoreText,eventText;
    private ProgressBar progressBar;
    private boolean defeated = false, state = false;
    private int count,count2,count3,count4;
    public String TAG = "My app ";
    private boolean isProximitySensor, isGyroscope, isLightSensor, isMagnetometerSensor, notFirst=false;;
    private float currentX, currentY, currentZ, lastX,lastY,lastZ,xDiv,yDiv,zDiv, firstVal, secondVal, thirdVal;
    float shakeStep = 5f;
    private Vibrator vibrator;
    Button screen, playButton;

    @Override
    public void onBackPressed() {
        Log.v(TAG,"Back blocked");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(BossLevel.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();

        count=0;
        count2=0;
        count3=0;
        count4=0;
        int random = (int )(Math.random() * 4 + 1);
        if(gt.getDistanceToTUL(l)<1){ // dystans do politechniki
            setContentView(R.layout.boss_level5);
        }
        else{
            if(random==1){
                setContentView(R.layout.boss_level);
            }
            else if(random==2){
                setContentView(R.layout.boss_level2);
            }
            else if(random==3){
                setContentView(R.layout.boss_level3);
            }
            else if(random==4){
                setContentView(R.layout.boss_level4);
            }
        }

        score = maxHp;

        screen = (Button) findViewById(R.id.hitbox);
        playButton = (Button) findViewById(R.id.button);
        playButton.setVisibility(View.INVISIBLE);
        scoreText = findViewById(R.id.score);
        eventText = findViewById(R.id.eventTxt);
        progressBar = findViewById(R.id.progress);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Gyroscope
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            isGyroscope = true;
        }
        else {
            Toast.makeText(getApplicationContext(),"Gyroscope is not avaliable", Toast.LENGTH_SHORT).show();
            isGyroscope = false;
        }
//        //Proximity
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
//            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//            isProximitySensor = true;
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Proximity sensor is not avaliable", Toast.LENGTH_SHORT).show();
//            isProximitySensor = false;
//        }
//        //Light
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
//            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//            isLightSensor = true;
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Light sensor is not avaliable", Toast.LENGTH_SHORT).show();
//            isLightSensor = false;
//        }
//        //Magnetometer
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
//            magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//            isMagnetometerSensor = true;
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Magnetometer sensor is not avaliable", Toast.LENGTH_SHORT).show();
//            isMagnetometerSensor = false;
//        }


        MediaPlayer mp = MediaPlayer.create(this,R.raw.sound);
        mp.start();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        Sensor sensor = sensorEvent.sensor;

        if( sensor.getType() == Sensor.TYPE_GYROSCOPE && (!state)) {
            firstVal = sensorEvent.values[0];
            secondVal = sensorEvent.values[1];
            thirdVal = sensorEvent.values[2];
            score = (long) (score - abs(firstVal)- abs(secondVal) - abs(thirdVal));
        }

        //damageBoss();

        //HP dependencies
        if(score <= (maxHp * 0.01)) {
            scoreText.setText("HP: 0" );
            defeated = true;
        } else {
            scoreText.setText("HP: " + score);
        }

        if(score < (maxHp) && score > (maxHp * 0.8))
        {
            if(count == 0) {
                MediaPlayer mp = MediaPlayer.create(this,R.raw.leopard3);
                mp.start();
                count = 1;
            }

        }
        //First event (proximity)
        if(score < (maxHp * 0.7999) && score > (maxHp * 0.6000)) {
            if(count2 == 0) {
                MediaPlayer mp= MediaPlayer.create(this,R.raw.leopard7);
                mp.start();
//                hpBarmanager(true);
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

//                eventText.setText("Place your hand above the phone!" );
//                if(sensor.getType() == Sensor.TYPE_PROXIMITY) {
//                    if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
//                        score = 6000;
//                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                        Toast.makeText(getApplicationContext(),"Great!" , Toast.LENGTH_SHORT).show();
//                        hpBarmanager(false);
//                        eventText.setText("");
//                    }
//                }
                count2 = 1;
            }
        }
        //Second event (light)
        if(score < (maxHp * 0.3999) && score > (maxHp * 0.25)) {
            if(count3 == 0) {
                MediaPlayer mp = MediaPlayer.create(this,R.raw.sound);
                mp.start();
//                hpBarmanager(true);
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

//                eventText.setText("Turn on the light!");
//                if(sensor.getType() == Sensor.TYPE_LIGHT) {
//                    if(sensorEvent.values[0] >= 150) {
//                        score = 2500;
//                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                        Toast.makeText(getApplicationContext(),"Amazing!" , Toast.LENGTH_SHORT).show();
//                        hpBarmanager(false);
//                        eventText.setText("");
//                    }
//                }
                count3 = 1;
            }
        }
        //Last event (shake)
        if(score < (maxHp * 0.0999) && score > 0){
            if(count4 == 0){
                MediaPlayer mp= MediaPlayer.create(this,R.raw.leopard3);
                mp.start();
//                hpBarmanager(true);
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

//                eventText.setText("Shake your phone to kill him!");
//                if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//                    currentX = sensorEvent.values[0];
//                    currentY = sensorEvent.values[1];
//                    currentZ = sensorEvent.values[2];
//
//                    if (notFirst) {
//                        xDiv = Math.abs(lastX - currentX);
//                        yDiv = Math.abs(lastY - currentY);
//                        zDiv = Math.abs(lastZ - currentZ);
//
//                        if ((xDiv > shakeStep & yDiv > shakeStep) || (xDiv > shakeStep & zDiv > shakeStep) || (yDiv > shakeStep & zDiv > shakeStep)) {
//                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                            score = 0;
//                            hpBarmanager(false);
//                            eventText.setText("");
//                        }
//                    }
//
//                    lastX = currentX;
//                    lastY = currentY;
//                    lastZ = currentZ;
//                    notFirst = true;
//                }
                count4 = 1;
            }
        }

        progressBar.setProgress((int) score);

        if(defeated){
            score = 0;
            playButton.setVisibility(View.VISIBLE);
        }
    }

    public void damageBoss(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        screen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                score -= 350; //damage per click
            }
        });
    }

    public  void nextActivity(View view) {
        playButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                startShakeActivity();
            }
        });
    }

    private void startShakeActivity() {
        Intent startOfGame2 = new Intent(this,ShakeActivity.class);
        startActivity(startOfGame2);
    }

    private  void hpBarmanager(boolean state) {
        //pause
        if(state){
            if(isGyroscope) {
                sensorManager.unregisterListener(this);
            }
            firstVal = 0;
            secondVal = 0;
            thirdVal = 0;
        }
        else {
            sensorManager.registerListener( this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        if(isGyroscope) {
            sensorManager.registerListener( this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
        }
//        else if(isProximitySensor) {
//            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_GAME);
//        } else if(isLightSensor) {
//            sensorManager.registerListener(this, lightSensor,  SensorManager.SENSOR_DELAY_FASTEST);
//        } else if(isMagnetometerSensor) {
//            sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isGyroscope) {
            sensorManager.unregisterListener(this);
        }
//        else if(isProximitySensor) {
//            sensorManager.unregisterListener(this);
//        } else if(isLightSensor) {
//            sensorManager.unregisterListener(this);
//        } else if(isMagnetometerSensor) {
//            sensorManager.unregisterListener(this);
//        }

    }
}