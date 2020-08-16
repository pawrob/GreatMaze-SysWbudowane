package com.example.wbudyapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class SecondLevel extends Activity implements SensorEventListener {

    //Deklaracje tego, czego będziemy używać
    public String TAG = "My app ";
    private SensorManager sensorManager;
    private Sensor magnetometer;
    //Poniżej po prostu obiekty, które możemy znaleźć w activity_main.xml
    private ImageView ball,studnia;
    private TextView xMagValue, yMagValue, zMagValue, ballX, ballY;
    //Początkowe wartości bedziemy przchowywać w ArrayList bo nie ma co się jebać ze zwykłą tablicą
    private ArrayList<Float> initialMagnetometerValues;
    private ArrayList<View> walls;
    private int screenWidth, screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//metoda z Activity
        setContentView(R.layout.second_level);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        ball = findViewById(R.id.ball);
        studnia = findViewById(R.id.studnia);
        walls = new ArrayList<View>();

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

        walls.add(findViewById(R.id.wall1 ) );
        walls.add(findViewById(R.id.wall2 ) );
        walls.add(findViewById(R.id.wall3 ) );
        walls.add(findViewById(R.id.wall4 ) );
        walls.add(findViewById(R.id.wall5 ) );
        walls.add(findViewById(R.id.left ) );
        walls.add(findViewById(R.id.right ) );
        walls.add(findViewById(R.id.top ) );
        walls.add(findViewById(R.id.bottom ) );


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

            //Tutaj naturalnie dzielenie przez 5 jest tylko dlatego, żeby to nie zapierdalało jak się przechyli lekko ekran

            this.move(ball,walls,sensorEvent.values[0],sensorEvent.values[1]);
            /*float changeX = ( sensorEvent.values[0] - initialMagnetometerValues.get(0) ) /5;
            if(changeX < 0 && this.checkIfLeft(ball,walls) ) ball.setX( ball.getX() + changeX );
            if(changeX > 0 && this.checkIfRight(ball,walls) ) ball.setX( ball.getX() + changeX );

            ball.setY( ball.getY() + ( sensorEvent.values[1] - initialMagnetometerValues.get(1) )/5 );*/
            if(ball.getX() > screenWidth) ball.setX(0 - ball.getWidth());
            if(ball.getX() < ( 0 - ball.getWidth() ) ) ball.setX(screenWidth);
            if(ball.getY() > screenHeight ) ball.setY(0 + ball.getHeight());
            if(ball.getY() < ( 0 - ball.getHeight() ) ) ball.setY(screenHeight);
            boolean finished = this.checkIfStudnia(ball,studnia);
            if(finished)
            {
                ball.setX(100);
                ball.setY(100);
            }


        }

    }
    //Pusta deklaracja metody, ale musi być zadeklarowana bo to wirtualna metoda interfejsu SensorEventListener XD
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean checkIfStudnia(ImageView ball, ImageView studnia)
    {
        //Na sztywno zakodowany promien studni jako 25 oraz srednice kuli jako 30
        if ( pow( ( ball.getX() + ball.getWidth()/2 - (studnia.getX() + studnia.getWidth()/2) ),2)
                + pow( ( ball.getY() + ball.getHeight()/2 - (studnia.getY() + studnia.getHeight()/2) ),2) <
                pow(studnia.getWidth()/2 - ball.getWidth()/2,2) ) return true;
        return false;
    }
    public void move(ImageView ball, ArrayList<View> walls, float x, float y)
    {
        //changeX - zmiana w poziomie (lewo prawo)
        float changeX = ( x - initialMagnetometerValues.get(0) ) /5;
        //changeY - zmiana w pionie (góra dół)
        float changeY = ( y - initialMagnetometerValues.get(0) ) /5;
        if(changeX < 0)
        {
            if( ! ( doesThePointCoverAnyWall(ball.getX() - 3,ball.getY(),walls) ||
                    doesThePointCoverAnyWall(ball.getX() - 3,ball.getY()+ball.getHeight(),walls) ) )
            {
                ball.setX( ball.getX() + changeX );
            }
        }
        if(changeX > 0)
        {
            if( ! ( doesThePointCoverAnyWall(ball.getX() + ball.getWidth() + 3,ball.getY(),walls) ||
                    doesThePointCoverAnyWall(ball.getX() + ball.getWidth() + 3,ball.getY()+ball.getHeight(),walls) ) )
            {
                ball.setX( ball.getX() + changeX );
            }
        }
        if(changeY < 0)
        {
            if( ! ( doesThePointCoverAnyWall(ball.getX(),ball.getY() - 3,walls) ||
                    doesThePointCoverAnyWall(ball.getX() + ball.getWidth(),ball.getY() + 3,walls) ) )
            {
                ball.setY( ball.getY() + changeY );
            }
        }
        if(changeY > 0)
        {
            if( ! ( doesThePointCoverAnyWall(ball.getX(),ball.getY() + ball.getHeight() + 3 ,walls) ||
                    doesThePointCoverAnyWall(ball.getX() + ball.getWidth(),ball.getY() + ball.getHeight() + 3,walls) ) )
            {
                ball.setY( ball.getY() + changeY );
            }
        }

    }
    public boolean doesThePointCoverAnyWall(float x, float y, ArrayList<View> walls)
    {
        boolean covers = false;
        for(int i=0; i<walls.size();i++)
        {
            //cWall to currentWall
            View cWall = walls.get(i);
            if( cWall.getX() < x && (cWall.getX() + cWall.getWidth() > x ) && cWall.getY() < y && ( cWall.getY() + cWall.getHeight() > y ) )
            {
                covers = true;
                break;
            }
        }
        return covers;
    }
}