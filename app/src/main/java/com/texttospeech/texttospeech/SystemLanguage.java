package com.texttospeech.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Locale;

public class SystemLanguage extends DeviceInteraction {
    private GestureDetectorCompat gestureObject;

    private String header, confirm, refuse;

    GlobalKontextVariables globalKontextVariables = GlobalKontextVariables.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_language);

        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false)) {
            if (globalKontextVariables.getToSpeech() == null) {
                //Checks the system language and set the TTS language
                switch (globalKontextVariables.getSystemLanguage()) {
                    case "en":
                        globalKontextVariables.setToSpeech(new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) //Test if the TTS API is available
                                {
                                    globalKontextVariables.setResultTTS(globalKontextVariables.getToSpeech().setLanguage(Locale.ENGLISH));//set the language
                                } else { //ErrorMessage
                                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
                        break;

                    case "de":
                        globalKontextVariables.setToSpeech(new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) //Test if the TTS API is available
                                {
                                    globalKontextVariables.setResultTTS(globalKontextVariables.getToSpeech().setLanguage(Locale.GERMAN));//set the language
                                } else { //ErrorMessage
                                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
                        break;
                    default:
                }
            }
        }


        gestureObject = new GestureDetectorCompat(SystemLanguage.this, new SystemLanguage.learnGesture());
        header = res.getString(R.string.SystemLanguageHeader_AuditoryOutput);
        confirm = res.getString(R.string.SystemLanguageConfirm_AuditoryOutput);
        refuse = res.getString(R.string.SystemLanguageRefuse_AuditoryOutput);
        super.speakOutOnStart(header, confirm, refuse);
    }

   @Override
    public boolean onTouchEvent(MotionEvent event)  {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, confirm, refuse);
    }

    class learnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            // If swipe to the right
            if (event2.getX() - event1.getX() > 0) {
                // To start the Accessibility Activity
                Intent intent = new Intent(SystemLanguage.this, Accessibility.class);
                startActivity(intent);
            }
            // If swipe to the left
            else if (event2.getX() - event1.getX() < 0) {
                // To start the SettingsLanguage Activity
                Intent intent = new Intent(SystemLanguage.this, SettingsLanguage.class);
                startActivity(intent);
            }
            return true;
        }
    }
}