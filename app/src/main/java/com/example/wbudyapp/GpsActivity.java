package com.example.wbudyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.location.Location;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


public class GpsActivity extends AppCompatActivity {
    Button btnLoc;
    private TextView longitude;
    private TextView latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        btnLoc = (Button) findViewById(R.id.btnGetLoc);
        ActivityCompat.requestPermissions(GpsActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
//                    double lat = l.getLatitude();
//                    double lon = l.getLongitude();
//                    Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
                    longitude = findViewById(R.id.lat);
                    latitude = findViewById(R.id.lon);
                    latitude.setText("Lat: "+l.getLatitude());
                    longitude.setText("Lon: "+l.getLongitude());
                }
            }
        });
    }
}