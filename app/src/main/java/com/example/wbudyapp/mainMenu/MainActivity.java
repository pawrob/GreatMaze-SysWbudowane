package com.example.wbudyapp.mainMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wbudyapp.R;
import com.example.wbudyapp.functions.GpsActivity;
import com.example.wbudyapp.levels.FirstLevel;


public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

    }

    public void startGame(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this, FirstLevel.class);
        //startOfGame.putExtra("LEVELNUMBER",1);
        startActivity(startOfGame);
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