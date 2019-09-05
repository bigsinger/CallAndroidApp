package com.example.calleeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class OuterStartAppActivity extends MainActivity {
    private static final String TAG = "CalleeApp/OuterStartAppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getActivityName(){
        return this.TAG;
    }
}
