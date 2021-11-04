package com.example.goodstuff;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyListData {
    private final String day, date;
    private double hours;

    public MyListData(String day, String date, double hours) {
        this.day = day;
        this.date = date;
        this.hours = hours;
    }

    public static MyListData construct(Date date, double hours) {
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

    public double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
