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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Deklaracje tego, czego będziemy używać
    public String TAG = "My app ";
    private SensorManager sensorManager;
    private Sensor magnetometer;
    //Poniżej po prostu obiekty, które możemy znaleźć w activity_main.xml
    private Button right, left;
    private ImageView star;
    private TextView xMagValue, yMagValue, zMagValue;
    //Początkowe wartości bedziemy przchowywać w ArrayList bo nie ma co się jebać ze zwykłą tablicą
    private ArrayList<Float> initialMagnetometerValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//metoda z appCompatActivity
        setContentView(R.layout.activity_main);

        right = findViewById(R.id.rightButton);
        left = findViewById(R.id.leftButton);
        star = findViewById(R.id.star);

        xMagValue = findViewById(R.id.xMagValue);
        yMagValue = findViewById(R.id.yMagValue);
        zMagValue = findViewById(R.id.zMagValue);
        //Inicjalizacja sensor Managera
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Inicjalizacja arrayListy
        initialMagnetometerValues = new ArrayList<Float>();

        //deklaracja żyroskopu i stworzenie (zarejestrowanie) modułów nasłuchujących
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(magnetometer != null )
        {
            sensorManager.registerListener( (SensorEventListener) this, magnetometer, SensorManager.SENSOR_DELAY_UI );
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
    //Ogółem nasza aplikacja tutaj opiera się na tym, że eventy wywołują się jak tylko otrzymamy sygnał z sensora.
    //W naszej aplikacji sygnały otrzymujemy z częstością podaną przy rejestracji menedżera (SENSOR_DELAY_UI)
    //Zasadniczo nie wiem czy dobrze, żeby nasza aplikacja wykonywała czynności na podstawie tego, czy jest sygnał z sensora
    //ale nie wiem jak zrobić, aby jakaś czynność wykonywała się stale, niezależnie od sygnałów z sensora, tak jak na kompo
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        //Jeśli tablica jest pusta, to wpisujemy wartości inicjalizacyjne, w odniesieniu do których będzie poruszać się gwiazdka
        if(initialMagnetometerValues.size() == 0)
        {
            for(int i=0; i < 3; i++) initialMagnetometerValues.add(sensorEvent.values[i]);
        }
        //No i tutaj jeśli sygnał jest z sensora magnetometru, to wykonują się takie czynności
        if( sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ) {
            xMagValue.setText("X: " + sensorEvent.values[0]);
            yMagValue.setText("Y: " + sensorEvent.values[1]);
            zMagValue.setText("Z: " + sensorEvent.values[2]);
            //Tutaj naturalnie dzielenie przez 5 jest tylko dlatego, żeby to nie zapierdalało jak się przechyli lekko ekran
            star.setX( star.getX() + ( sensorEvent.values[0] - initialMagnetometerValues.get(0) )/5 );
            star.setY( star.getY() + ( sensorEvent.values[1] - initialMagnetometerValues.get(1) )/5 );

        }

    }
    //Pusta deklaracja metody, ale musi być zadeklarowana bo to wirtualna metoda interfejsu SensorEventListener XD
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}