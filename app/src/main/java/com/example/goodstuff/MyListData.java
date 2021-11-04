package com.example.goodstuff;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyListData {
    private final String day, date;
    private long hours;

    public MyListData(String day, String date, long hours) {
        this.day = day;
        this.date = date;
        this.hours = hours;
    }

    public static MyListData construct(Date date, long hours) {
        @SuppressLint("SimpleDateFormat") String dy = (new SimpleDateFormat("EE")).format(date);
        @SuppressLint("SimpleDateFormat") String dt = (new SimpleDateFormat("dd-MM-yyyy")).format(date);
        return new MyListData(dy, dt, hours);
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return this.date;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }
}
