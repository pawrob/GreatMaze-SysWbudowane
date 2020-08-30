package com.example.wbudyapp.functions;

import java.util.Date;

public class TimeHandler {

    public static long startTimer(){

        return new Date().getTime();
    }

    public static long calcTime(long startTime){
        long endTime = new Date().getTime();
        return endTime - startTime;
    }

}