package com.example.wbudyapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public String TAG = "My app ";
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Button right, left;
    private ImageView star;
    private TextView xGyroValue, yGyroValue, zGyroValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//metoda z appCompatActivity
        setContentView(R.layout.activity_main);

        right = findViewById(R.id.rightButton);
        left = findViewById(R.id.leftButton);
        star = findViewById(R.id.star);

        xGyroValue = findViewById(R.id.xGyroValue);
        yGyroValue = findViewById(R.id.yGyroValue);
        zGyroValue = findViewById(R.id.zGyroValue);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //deklaracja żyroskopu i stworzenie (zarejestrowanie) modułów nasłuchujących
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if( gyroscope != null )
        {
            sensorManager.registerListener( (SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_UI );
            Log.d(TAG,"On create: listener has been launched");
        }
        else
        {
            Log.v(TAG,"On create: listener is not available");
        }

        right.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.setX( star.getX() + 3 );
            }
        } );
        left.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.setX( star.getX() - 3 );
            }
        } );

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if( sensor.getType() == Sensor.TYPE_GYROSCOPE ) {
            xGyroValue.setText("X: " + sensorEvent.values[0]);
            yGyroValue.setText("Y: " + sensorEvent.values[1]);
            zGyroValue.setText("Z: " + sensorEvent.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}