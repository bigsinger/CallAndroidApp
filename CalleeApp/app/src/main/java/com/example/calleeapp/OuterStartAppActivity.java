package com.example.calleeapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class OuterStartAppActivity extends MainActivity {
    private static final String TAG = "CalleeApp/OuterStartAppActivity";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getActivityName(){
        return this.TAG;
    }
}
