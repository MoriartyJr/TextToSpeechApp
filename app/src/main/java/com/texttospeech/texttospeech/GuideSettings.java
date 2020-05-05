package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuideSettings extends DeviceInteraction {

    private String header, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_settings);

        header = res.getString(R.string.GuideSettingsHeader_AuditoryOutput);
        info = res.getString(R.string.GuideSettingsInfo_AuditoryOutput);
        super.speakOutOnStart(header, info);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, info);
    }

    public void startActivityGuideCameraMode(View view) {
        Intent intent = new Intent(GuideSettings.this, GuideCameraMode.class);
        startActivity(intent);
    }
}
