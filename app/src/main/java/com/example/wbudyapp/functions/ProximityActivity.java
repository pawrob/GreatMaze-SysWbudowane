package com.example.wbudyapp.functions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbudyapp.R;


public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private boolean isProximitySensor;
    private TextView proxValue,proxValueMax;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proxValue = findViewById(R.id.proxValue);
        proxValueMax = findViewById(R.id.proxValueMax);
        // dostęp do sensora
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // tworzenie obiektu Sensor dla proximity sensora
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            proxValueMax.setText(proximitySensor.getMaximumRange()+"cm");
            isProximitySensor = true;
        }   // jeśli sensor niedostępny koniec apki
        else {
            Toast.makeText(getApplicationContext(),"Proximity sensor is not avaliable", Toast.LENGTH_SHORT).show();
            isProximitySensor = false;
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        proxValue.setText(sensorEvent.values[0]+"cm");
        androidx.constraintlayout.widget.ConstraintLayout rl = findViewById(R.id.activity_proximity);

        if(sensorEvent.values[0] < proximitySensor.getMaximumRange() ) {
            // Detected something nearby
            rl.setBackgroundColor(Color.RED);
        }
        else {
            // Nothing is nearby
            rl.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isProximitySensor){
            // utworzenie nasłuchiwacza z odczytem co 2s
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isProximitySensor){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}