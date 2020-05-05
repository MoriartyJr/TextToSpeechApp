package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuideVoiceControl extends DeviceInteraction {

    String header, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_voice_control);

        header = res.getString(R.string.GuideVoiceControlHeader_AuditoryOutput);
        info = res.getString(R.string.GuideVoiceControlInfo_AuditoryOutput);
        super.speakOutOnStart(header, info);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, info);
    }

    public void startActivityPictureSelection(View view) {
        Intent intent = new Intent(GuideVoiceControl.this, PictureSelection.class);
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
        startActivity(intent);
    }
}
