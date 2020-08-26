package com.example.wbudyapp.functions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.wbudyapp.R;


public class GpsActivity extends AppCompatActivity {
    Button btnLoc;
    private TextView longitude,latitude,distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        btnLoc = (Button) findViewById(R.id.btnGetLoc);
        ActivityCompat.requestPermissions(GpsActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);


        btnLoc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {

                    longitude = findViewById(R.id.lat);
                    latitude = findViewById(R.id.lon);
                    distance = findViewById(R.id.dystDoPl);
                    latitude.setText("Lat: "+l.getLatitude());
                    longitude.setText("Lon: "+l.getLongitude());
                    distance.setText("Distance: " + gt.getDistanceToTUL(l));
                }
            }
        });
    }

}