package com.texttospeech.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TextOutput extends DeviceInteraction {
    GlobalKontextVariables globalKontextVariables = GlobalKontextVariables.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_output);

        TextView tesseractOutputTextView = findViewById(R.id.tvTesseractOutputText);
        GlobalKontextVariables globaleVariable = GlobalKontextVariables.getInstance();
        tesseractOutputTextView.setText(globaleVariable.getAnalysedText());
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
        super.speakOutOnStartOutput(globaleVariable.getAnalysedText());
        super.setGestureObject();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.speakOutOnStartOutput(globaleVariable.getAnalysedText());

        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }


}
