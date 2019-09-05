package com.example.callerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CallerApp/MainActivity";
    private static final int REQUEST_CODE_TEST = 1;
    private static final String CALLEE_PACKAGE = "com.example.calleeapp";
    private static final String CALLEE_ACTIVITY = "com.example.calleeapp.MainActivity";

    private Context context;
    private WebView webView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initViews();
    }

    private void initViews() {
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                        intent.setSelector(null);
                    }
                    List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent,0);
                    if(resolves.size()>0){
                        startActivityIfNeeded(intent, -1);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

//                Uri uri = Uri.parse(url);
//                if (uri.getScheme().equals("")) {
//                } else {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    //webView.loadUrl(url);
//                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (uri.getScheme().equals("")) {

                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.loadUrl("https://a.kaola.com");

        Button start_main_app_Button = findViewById(R.id.start_main_app);
        start_main_app_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(CALLEE_PACKAGE, CALLEE_ACTIVITY);
                startActivity(intent);
            }
        });

        Button start_main_app_set_referer_Button = findViewById(R.id.start_main_app_set_referer);
        start_main_app_set_referer_Button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(CALLEE_PACKAGE, CALLEE_ACTIVITY);
                // case 1
                intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://com.test.app1"));
                startActivity(intent);
            }
        });

        Button start_main_app_set_referer2_Button = findViewById(R.id.start_main_app_set_referer2);
        start_main_app_set_referer2_Button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(CALLEE_PACKAGE, CALLEE_ACTIVITY);
                // case 2
                intent.putExtra(Intent.EXTRA_REFERRER_NAME, "android-app://com.test.app2");
                startActivity(intent);
            }
        });

        Button start_main_app_for_result_Button = findViewById(R.id.start_main_app_for_result);
        start_main_app_for_result_Button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(CALLEE_PACKAGE, CALLEE_ACTIVITY);
                // case 3
                intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://com.test.app3"));
                intent.putExtra("GET_DATA", true);
                startActivityForResult(intent, REQUEST_CODE_TEST);
            }
        });

        Button start_main_app_by_scheme_uri = findViewById(R.id.start_main_app_by_scheme_uri);
        start_main_app_by_scheme_uri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("kaola://a.kaola.com");
                Uri uri = Uri.parse("http://a.kaola.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button start_main_app_by_web = findViewById(R.id.start_main_app_by_web);
        start_main_app_by_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://b.kaola.com");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TEST) {
            Log.d(TAG, "resultCode: " + requestCode + ", data: " + data);
        }
    }
}
