package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Accessibility extends DeviceInteraction {

    private GestureDetectorCompat gestureObject;
    private String auditoryTextConfirm, auditoryTextRefuse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        auditoryTextConfirm = res.getString(R.string.ConfirmAccessibillity_AuditoryOutput);
        auditoryTextRefuse = res.getString(R.string.RefuseAccessibillity_AuditoryOutput);
        super.speakOutOnStart(auditoryTextConfirm, auditoryTextRefuse);

        gestureObject = new GestureDetectorCompat(Accessibility.this, new Accessibility.learnGesture());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(auditoryTextConfirm, auditoryTextRefuse);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class learnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            // If swipe to the right
            if (event2.getX() - event1.getX() > 0) {
                // To start the Accessibility Activity
                Intent intent = new Intent(Accessibility.this, GuideSettings.class);
                startActivity(intent);

            }
            // If swipe to the left
            else if (event2.getX() - event1.getX() < 0) {
                // To start the Guides Activity
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuditoryFeedbakcOn", false).apply();
                Intent intent = new Intent(Accessibility.this, GuideSettings.class);
                startActivity(intent);
            }
            return true;
        }
    }
}
