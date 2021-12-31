package com.app.bubbleeasy.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.app.bubbleeasy.R;
import com.app.bubbleeasy.Utills.Constant;

public class SplashActivity extends Activity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        changeStatusBarColor("#14182b");
        Handler handler = new Handler();
        sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        if (sharedpreferences.getString(Constant.Vibrate, "").equals("")) {
            editor.putString(Constant.Vibrate, "1");
            editor.commit();
        } else {
             // do nothing
        }

        if (sharedpreferences.getString(Constant.Sound, "").equals("")) {
            editor.putString(Constant.Sound, "1");
            editor.commit();
        } else {
            // do nothing
        }

        if (sharedpreferences.getString(Constant.numberOfRows, "").equals("")) {
            editor.putString(Constant.numberOfRows, "10");
            editor.commit();
        } else {
            // do nothing
        }

        if (sharedpreferences.getString(Constant.numberOfColumns, "").equals("")) {
            editor.putString(Constant.numberOfColumns, "8");
            editor.commit();
        } else {
            // do nothing
        }


        Runnable run = new Runnable() {

            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        };

        handler.postDelayed(run, 3000);

    }


    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

}


