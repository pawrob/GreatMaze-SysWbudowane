package com.example.wbudyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
    public void startGame(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this,SecondActivity.class);
        startActivity(startOfGame);
    }

    public void checkGps(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent gpsCheck = new Intent(this,GpsActivity.class);
        startActivity(gpsCheck);
    }
}