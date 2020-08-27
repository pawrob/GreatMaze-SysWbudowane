package com.example.wbudyapp.functions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbudyapp.R;
import com.example.wbudyapp.mainMenu.TestLevels;


public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private boolean isProximitySensor;
    private TextView proxValue,proxValueMax;
    Button btnLoc;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proxValue = findViewById(R.id.proxValue);
        proxValueMax = findViewById(R.id.proxValueMax);
        btnLoc = (Button) findViewById(R.id.button2);
        // dostęp do sensora
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // tworzenie obiektu Sensor dla proximity sensora
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            proxValueMax.setText(getString(R.string.prox_value_max) + proximitySensor.getMaximumRange()+"cm");
            isProximitySensor = true;
        }   // jeśli sensor niedostępny koniec apki
        else {
            Toast.makeText(getApplicationContext(),"Proximity sensor is not avaliable", Toast.LENGTH_SHORT).show();
            isProximitySensor = false;
        }

    }
    public void pressCheck(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Pressed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        proxValue.setText(getString(R.string.prox_value) + sensorEvent.values[0]+"cm");
//        androidx.constraintlayout.widget.ConstraintLayout rl = findViewById(R.id.activity_proximity);

        if(sensorEvent.values[0] < proximitySensor.getMaximumRange() ) {
            // Detected something nearby
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(getApplicationContext(),"Touch locked!" , Toast.LENGTH_SHORT).show();
//            rl.setBackgroundColor(Color.RED);
        }
        else {
            // Nothing is nearby
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(getApplicationContext(),"Touch unlocked!" , Toast.LENGTH_SHORT).show();
//            rl.setBackgroundColor(Color.GREEN);
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

    public void checkPress(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {

    }
}