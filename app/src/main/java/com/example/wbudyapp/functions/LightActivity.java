package com.example.wbudyapp.functions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbudyapp.R;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    public String TAG = "My app ";
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private ConstraintLayout backgroundColor;
    private TextView lightReading, lightLevel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        backgroundColor = (ConstraintLayout) findViewById(R.id.activity_light);
        backgroundColor.setKeepScreenOn(true);

        lightReading = findViewById(R.id.lightReading);
        lightLevel = findViewById(R.id.lightLevel);
        // dostęp do sensora
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // tworzenie obiektu Sensor dla proximity sensora
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG,"On create: light sensor listener has been launched");
        }
        else {
            Log.v(TAG,"On create: light sensor listener is not available");
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            lightReading.setText("Light reading: " + sensorEvent.values[0]);

            if(sensorEvent.values[0] < 10 ) {
                lightLevel.setText("It is DARK");
                backgroundColor.setBackgroundColor(getResources().getColor(R.color.colorDark));
            }
            else if(sensorEvent.values[0] >= 10 && sensorEvent.values[0] < 50) {
                lightLevel.setText("It is DIM");
                backgroundColor.setBackgroundColor(getResources().getColor(R.color.colorDIM));
            }
            else if(sensorEvent.values[0] >= 50 && sensorEvent.values[0] < 150) {
                lightLevel.setText("It is BRIGHT");
                backgroundColor.setBackgroundColor(getResources().getColor(R.color.colorBRIGHT));
            }
            else if(sensorEvent.values[0] >= 150) {
                lightLevel.setText("It is  VERY BRIGHT");
                backgroundColor.setBackgroundColor(getResources().getColor(R.color.colorVERYBRIGHT));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor lightSensor, int i) {}
}
