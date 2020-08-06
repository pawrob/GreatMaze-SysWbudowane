package com.example.wbudyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        /*start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startOfGame = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(startOfGame);
            }
        });*/
    }

    public void startGame(View view) //metoda ta musi byc publiczna, byc voidem i miec jeden argument View
    {
        Intent startOfGame = new Intent(this,SecondActivity.class);
        startActivity(startOfGame);
    }
}