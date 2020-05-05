package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuideCameraMode extends DeviceInteraction {

    private String header, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_camera_mode);

        header = res.getString(R.string.GuideCameraModeHeader_AuditoryOutput);
        info = res.getString(R.string.GuideCameraModeInfo_AuditoryOutput);
        super.speakOutOnStart(header, info);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, info);
    }

    public void startActivityGuideVoiceControl(View view) {
        Intent intent = new Intent(GuideCameraMode.this, GuideVoiceControl.class);
        startActivity(intent);
    }
}
