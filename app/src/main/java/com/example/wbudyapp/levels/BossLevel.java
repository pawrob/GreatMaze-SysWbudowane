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
import android.os.Bundle;

import com.example.wbudyapp.functions.GpsActivity;
import com.example.wbudyapp.functions.GpsTracker;
import com.example.wbudyapp.functions.TimeHandler;
import com.example.wbudyapp.mainMenu.ShakeActivity;
import com.example.wbudyapp.mainMenu.TestLevels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wbudyapp.R;

import java.util.Date;

import static java.lang.Math.abs;

public class BossLevel extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private long score;
    private TextView scoreText;
    private ProgressBar progressBar;
    private boolean defeated = false;
private int count,count2,count3,count4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(BossLevel.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();

        count=0;
        count2=0;
        count3=0;
        count4=0;
        super.onCreate(savedInstanceState);
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

//        else{
//            setContentView(R.layout.boss_level);
//        }

        score = 10000;
        scoreText = findViewById(R.id.score);
        progressBar = findViewById(R.id.progress);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null )
        {
            sensorManager.registerListener( (SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_GAME );
        }

        MediaPlayer mp= MediaPlayer.create(this,R.raw.sound);
        mp.start();


    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {

        Sensor sensor = sensorEvent.sensor;
        final Button playButton = (Button) findViewById(R.id.button);
        playButton.setVisibility(View.INVISIBLE);
        if( sensor.getType() == Sensor.TYPE_GYROSCOPE ) {
        score = (long) (score - abs(sensorEvent.values[0])- abs(sensorEvent.values[1]) - abs(sensorEvent.values[2]) );
        }

        if(score<=100) {
            scoreText.setText("HP: 0" );
            defeated=true;
        }
        else{
            scoreText.setText("HP: " + score);
        }
        if(score<10000&score>8000)
        {
            if(count==0){
                MediaPlayer mp= MediaPlayer.create(this,R.raw.leopard3);
                mp.start();
                count=1;
            }

        }
        if(score<4999&score>2500){
            if(count2==0){
                MediaPlayer mp= MediaPlayer.create(this,R.raw.sound);
                mp.start();
                count2=1;
            }
        }
        if(score<7999&score>5000){
            if(count3==0){
                MediaPlayer mp= MediaPlayer.create(this,R.raw.leopard7);
                mp.start();
                count3=1;
            }
        }
        if(score<2499&score>100){
            if(count4==0){
                MediaPlayer mp= MediaPlayer.create(this,R.raw.leopard3);
                mp.start();
                count4=1;
            }
        }

        progressBar.setProgress((int) score);

        if(defeated){
            score=0;

            playButton.setVisibility(View.VISIBLE);
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShakeActivity();
            }
        });
    }


    private  void startShakeActivity(){
        Intent startOfGame2 = new Intent(this,ShakeActivity.class);
        startActivity(startOfGame2);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy) {}
}