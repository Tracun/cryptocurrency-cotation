package com.wb.tracun.braziliextest.Services;

import android.util.Log;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Tracun on 16/01/2018.
 */

public class FormatBR {

    public static String getTime(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

        Date data = new Date();

        return dateFormat.format(data);

    }

    public static String toReal(float value){

        Locale ptBR = new Locale("pt", "br");
        return NumberFormat.getCurrencyInstance(ptBR).format(value);
    }
}
