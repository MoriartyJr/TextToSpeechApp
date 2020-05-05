package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class SettingsGeneral extends DeviceInteraction {

    private String header, question, choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_general);

        header = res.getString(R.string.SettingsGeneralHeader_AuditoryOutput);
        question = res.getString(R.string.SettingsGeneralQuestion_AuditoryOutput);
        choices = res.getString(R.string.SettingsGeneralChoices_AuditoryOutput);
        super.speakOutOnStart(header, question, choices);

        super.setGestureObject();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, question, choices);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }

    public void startActivitySettingsDesign(View view) {
        Intent intent = new Intent(SettingsGeneral.this, SettingsDesign.class);
        startActivity(intent);
    }

    public void startActivitySettingsAccessibility(View view) {
        Intent intent = new Intent(SettingsGeneral.this, SettingsAccessibility.class);
        startActivity(intent);
    }

    public void startActivitySettingsInformation(View view) {
        Intent intent = new Intent(SettingsGeneral.this, SettingsInformation.class);
        startActivity(intent);
    }
}
