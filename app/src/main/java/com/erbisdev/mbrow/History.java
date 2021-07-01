package com.erbisdev.mbrow;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class History extends Activity {

    WebView web1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        int pos = getIntent().getIntExtra("pos",0);

        web1 = (WebView) findViewById(R.id.webView1);
        web1.setBackgroundColor(0);
     //   web1.setBackgroundResource(R.drawable.bg);
        web1.getSettings().setBuiltInZoomControls(true);
        switch (pos) {
            case 0:
                web1.loadUrl("file:///android_asset/History.html");
                break;
            case 1:
                web1.loadUrl("file:///android_asset/Objective.html");
                break;
            case 2:
                web1.loadUrl("file:///android_asset/Principles.html");
                break;
            case 3:
                web1.loadUrl("file:///android_asset/Calender.html");
                break;
            case 4:
                web1.loadUrl("file:///android_asset/ShcoolSystem.html");
                break;

            default:
                web1.loadUrl("file:///android_asset/History.html");
                break;
        }

    }
}