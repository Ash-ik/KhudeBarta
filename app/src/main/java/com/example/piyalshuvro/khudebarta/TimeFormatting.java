package com.example.piyalshuvro.khudebarta;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Piyal Shuvro on 8/26/2015.
 */
public class TimeFormatting {
    Date CurrentDate,MessageDate,d;
    String currentDateTimeString;
    String msgTime;
    String date_to_show;
    long timeInstance,seconds,minutes,hours;

    public String showTimediff(String msgdate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        currentDateTimeString = format.format(cal.getTime());


        CurrentDate=new Date();
        MessageDate=new Date();
        d=new Date();
        try {
            CurrentDate = format.parse(currentDateTimeString);
            MessageDate = format.parse(msgdate);
            Log.i("M", MessageDate.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        timeInstance = CurrentDate.getTime() - MessageDate.getTime();
        seconds = timeInstance / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        if(minutes<=1)
            msgTime = "Just now";
        else if (hours < 1) {
            msgTime = minutes + " mins ago";
        } else if (hours >= 1 && hours < 24) {

            SimpleDateFormat formatting = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date newDate = null;
            try {
                newDate = formatting.parse(msgdate);
                formatting = new SimpleDateFormat("hh:mma");
                msgTime= formatting.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

/*            if (hours == 1) {
                msgTime = hours + " hour ago";
            } else
                msgTime = hours + " hours ago";*/
        } else if (MessageDate.getDay()-CurrentDate.getDay()<=7) {
            SimpleDateFormat formatting = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date newDate = null;
            try {
                newDate = formatting.parse(msgdate);
                formatting = new SimpleDateFormat("EEE hh:mma");
                msgTime= formatting.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            msgTime=convert(msgdate);
        }
        return msgTime;
    }

    public String coversation_date(String date){
        date_to_show=date;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date newDate = format.parse(date_to_show);

            format = new SimpleDateFormat("dd MMM,yyyy hh:mm a");
            date_to_show= format.format(newDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("time",date_to_show);
        return date_to_show;

    }
    public String convert(String date){
        date_to_show=date;
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date newDate = format.parse(date_to_show);

            if(cal.getTime().getYear()==newDate.getYear())
                format = new SimpleDateFormat("dd MMM, HH:mma");
            else format = new SimpleDateFormat("dd MMM,yyyy");
            date_to_show= format.format(newDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("time",date_to_show);
        return date_to_show;

    }
}
