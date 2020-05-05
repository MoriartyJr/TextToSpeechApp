package com.texttospeech.texttospeech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.Objects;

public class Bootscreen extends Activity {

    public static int BOOT_TIME_OUT = 4000; //for the Boottime

    private Intent intent = null;

    GlobalKontextVariables globalKontextVariables = GlobalKontextVariables.getInstance();

    Boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootscreen);

        globalKontextVariables.setSystemLanguageLastUse(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("systemLanguageLastUse", null));

        if (!Objects.equals(globalKontextVariables.getSystemLanguage(), globalKontextVariables.getSystemLanguageLastUse()))
        {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("systemLanguageLastUse", globalKontextVariables.getSystemLanguage()).apply();
            globalKontextVariables.setAppLanguage(globalKontextVariables.getSystemLanguage());
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("appLanguage", globalKontextVariables.getSystemLanguage()).apply();
        }

        globalKontextVariables.setAppLanguage(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("appLanguage", null));

        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);

        new Handler().postDelayed(new Runnable() {
            /**Handler for a Delay of 4 Seconds**/
            @Override
            public void run() {
                startActivitySystemLanguage();
                finish();
            }
        }, BOOT_TIME_OUT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startActivitySystemLanguage();
        return super.onTouchEvent(event);
    }

    public void startActivitySystemLanguage() {
        if (intent == null) {
            if (isFirstRun) {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuditoryFeedbakcOn", true).apply();
                intent = new Intent(Bootscreen.this, SystemLanguage.class);
                startActivity(intent);
            } else {
                intent = new Intent(Bootscreen.this, PictureSelection.class);
                startActivity(intent);
            }
        }
    }
}

