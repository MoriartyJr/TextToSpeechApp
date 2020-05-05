package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SettingsInformation extends DeviceInteraction {

    private String header, question, choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_information);

        header = res.getString(R.string.SettingsInformationHeader_AuditoryOutput);
        question = res.getString(R.string.SettingsInformationQuestion_AuditoryOutput);
        choices = res.getString(R.string.SettingsInformationChoices_AuditoryOutput);
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

    public void onButtonVersionClick(View view) {
        Toast.makeText(getApplicationContext(), "Version 1.0, 07.11.2017", Toast.LENGTH_LONG).show();
    }

    public void startActivityGuide(View view) {
        Intent intent = new Intent(SettingsInformation.this, GuideSettings.class);
        startActivity(intent);
    }
}
