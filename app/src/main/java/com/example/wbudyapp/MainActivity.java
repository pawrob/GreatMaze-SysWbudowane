package com.example.wbudyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

    }



    public void startGame(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this,LevelActivity.class);
        startOfGame.putExtra("LEVELNUMBER",1);
                startActivity(startOfGame);
    }

    public void checkGps(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent gpsCheck = new Intent(this,GpsActivity.class);
        startActivity(gpsCheck);
    }
    public void checkShake(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent shakeCheck = new Intent(this,ShakeActivity.class);
        startActivity(shakeCheck);
    }

    public void checkLevel(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent lvlCheck = new Intent(this,TestLevels.class);
        startActivity(lvlCheck);
    }
}