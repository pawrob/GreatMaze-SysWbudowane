package com.example.wbudyapp.mainMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wbudyapp.R;
import com.example.wbudyapp.levels.FirstLevel;
import com.example.wbudyapp.levels.SecondLevel;
import com.example.wbudyapp.levels.ThirdLevel;

public class TestLevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_levels);
    }


    public void lvl1(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this, FirstLevel.class);
        startActivity(startOfGame);
    }

    public void lvl2(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this, SecondLevel.class);
        startActivity(startOfGame);
    }

    public void lvl3(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this, ThirdLevel.class);
        startActivity(startOfGame);
    }
}