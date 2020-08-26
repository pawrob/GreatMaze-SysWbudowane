package com.example.wbudyapp.mainMenu;

import androidx.appcompat.app.AppCompatActivity;
import com.example.wbudyapp.R;
import com.example.wbudyapp.functions.GpsActivity;
import com.example.wbudyapp.functions.ProximityActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SensorTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);
    }

    public void checkProx(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent proxCheck = new Intent(this, ProximityActivity.class);
        startActivity(proxCheck);
    }

    public void checkGps(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent gpsCheck = new Intent(this, GpsActivity.class);
        startActivity(gpsCheck);
    }


    public void checkLevel(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent lvlCheck = new Intent(this,TestLevels.class);
        startActivity(lvlCheck);
    }
}