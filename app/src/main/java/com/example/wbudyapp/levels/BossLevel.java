package com.example.wbudyapp.levels;

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
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.wbudyapp.functions.TimeHandler;
import com.example.wbudyapp.mainMenu.ShakeActivity;
import com.example.wbudyapp.mainMenu.TestLevels;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int random = (int )(Math.random() * 4 + 1);
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
//        else{
//            setContentView(R.layout.boss_level);
//        }

        score = 1000;
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