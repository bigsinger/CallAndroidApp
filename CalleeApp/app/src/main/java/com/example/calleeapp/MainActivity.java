package com.example.calleeapp;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CalleeApp/MainActivity";
    private TextView mTextView = null;
    private TextView tv_activity_name = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.display_caller_app);
        tv_activity_name = findViewById(R.id.tv_activity_name);
        tv_activity_name.setText(getActivityName());
        checkCallingApp();
    }

    protected String getActivityName() {
        return this.TAG;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void checkCallingApp() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("\n\n");
        // Binder.getCallingUid/Pid
        stringBuffer.append("1. caller uid = ").append(Binder.getCallingUid()).append(", pid = ")
                .append(Binder.getCallingPid()).append("\n\n");

        Uri referrer = getReferrer();
        stringBuffer.append("2. caller (referrer uri) : ");
        if (referrer != null) {
            stringBuffer.append(referrer.toString());
        } else {
            stringBuffer.append("null");
        }
        stringBuffer.append("\n\n");

        String referrerStr = reflectGetReferrer();
        stringBuffer.append("3. caller (reflect mReferrer) : ").append(referrerStr).append("\n\n");

        String callerPackage = getCallingPackage();
        stringBuffer.append("4. callerPackage: ").append(callerPackage).append("\n\n");

        ComponentName componentName = getCallingActivity();
        stringBuffer.append("5. caller componentName: ");
        if (componentName != null) {
            stringBuffer.append(componentName.toString());
        } else {
            stringBuffer.append("null");
        }
        stringBuffer.append("\n\n");


        Log.d(TAG, stringBuffer.toString());
        mTextView.setText(getString(R.string.caller_name, stringBuffer.toString()));

        Intent intent = getIntent();

        if (intent.getBooleanExtra("GET_DATA", false)) {
            Intent resultData = new Intent();
            resultData.putExtra("RESULT_DATA", "OK.");

            setResult(RESULT_OK, resultData);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String reflectGetReferrer() {
        try {
            Class activityClass = Class.forName("android.app.Activity");
            Field refererField = activityClass.getDeclaredField("mReferrer");
            refererField.setAccessible(true);
            String referrer = (String) refererField.get(MainActivity.this);
            return referrer;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return "No referrer";
        }
    }
}
