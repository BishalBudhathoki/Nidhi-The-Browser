package com.erbisdev.mbrow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            Thread mySplash = new Thread(){
                @Override
                public void run(){
                    try{
                        sleep(600);
                        Intent i = new Intent(getBaseContext(),MainActivity.class);

                        startActivity(i);
                        finish();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        mySplash.start();

        }
}
