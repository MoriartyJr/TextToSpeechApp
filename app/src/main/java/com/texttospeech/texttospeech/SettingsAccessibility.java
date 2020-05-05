package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsAccessibility extends DeviceInteraction {

    private String header, question, choices;

    ToggleButton hapticTButton;
    ToggleButton auditoryTButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_accessibility);

        header = res.getString(R.string.SettingsAccessibilityHeader_AuditoryOutput);
        question = res.getString(R.string.SettingsAccessibilityQuestion_AuditoryOutput);
        choices = res.getString(R.string.SettingsAccessibilityChoices_AuditoryOutput);
        super.speakOutOnStart(header, question, choices);

        hapticTButton = findViewById(R.id.tbtHapticFeedback);

        auditoryTButton = findViewById(R.id.tbtAuditoryFeedback);

        hapticTButton.setChecked(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isHapticFeedbakcOn", false));

        auditoryTButton.setChecked(getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false));

        hapticTButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    //Toast.makeText(getApplicationContext(), "Wahr", Toast.LENGTH_LONG).show();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isHapticFeedbakcOn", true).apply();
                } else {
                    // The toggle is disabled
                    //Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_LONG).show();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isHapticFeedbakcOn", false).apply();
                }
            }
        });

        auditoryTButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    //Toast.makeText(getApplicationContext(), "Wahr", Toast.LENGTH_LONG).show();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuditoryFeedbakcOn", true).apply();
                    Intent intent = new Intent(SettingsAccessibility.this, PictureSelection.class);
                    startActivity(intent);
                } else {
                    // The toggle is disabled
                    //Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_LONG).show();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuditoryFeedbakcOn", false).apply();
                }
            }
        });
        hapticTButton.setEnabled(false);
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

    public void startActivitySettingsLanguage(View view) {
        Intent intent = new Intent(SettingsAccessibility.this, SettingsLanguage.class);
        startActivity(intent);
    }
}
