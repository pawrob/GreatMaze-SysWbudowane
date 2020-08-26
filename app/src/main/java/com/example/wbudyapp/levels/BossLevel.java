package com.example.wbudyapp.levels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wbudyapp.R;

import static java.lang.Math.abs;

public class BossLevel extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private long score;
    private TextView scoreText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_level);



        scoreText = findViewById(R.id.score);
        score = 10000;
        progressBar = findViewById(R.id.progress);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null )
        {
            sensorManager.registerListener( (SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_GAME );
        }

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        Sensor sensor = sensorEvent.sensor;
        if( sensor.getType() == Sensor.TYPE_GYROSCOPE ) {
        score = (long) (score - abs(sensorEvent.values[0])- abs(sensorEvent.values[1]) - abs(sensorEvent.values[2]) );
        }
        if(score>0){
            scoreText.setText("HP: " + score);
        }else {
            scoreText.setText("HP: 0" );
        }

        progressBar.setProgress((int) score);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy)
    {

    }
}