package com.example.wbudyapp.levels;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.wbudyapp.mainMenu.LevelActivity;
import com.example.wbudyapp.R;
import com.example.wbudyapp.mainMenu.ShakeActivity;

import java.util.Date;

public class FirstLevel extends LevelActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.first_level);
        super.onCreate(savedInstanceState);//metoda z Activity

        startTime = new Date().getTime();
        walls.add(findViewById(R.id.wall1 ) );
        walls.add(findViewById(R.id.left ) );
        walls.add(findViewById(R.id.right ) );
        walls.add(findViewById(R.id.top ) );
        walls.add(findViewById(R.id.bottom ) );
    }
    public void startShakeActivity()
    {
        super.startShakeActivity();
        ShakeActivity.nextLevel = "second";

    }
}
