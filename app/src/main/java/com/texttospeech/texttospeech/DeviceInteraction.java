package com.texttospeech.texttospeech;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class DeviceInteraction extends Activity  {

    private int backPressed = 0;
    private GestureDetectorCompat gestureObject;
    protected GlobalKontextVariables globaleVariable = GlobalKontextVariables.getInstance();


    /**##################################Variables for the TTS functionality############################# **/
    protected TextToSpeech toSpeech;
    protected String text;
    protected Resources res;
    /**################################################################################################# **/

    /**###################################Variables for the STT functionality########################### **/
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayList<String> recordedResult = null;
    /**################################################################################################# **/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        res = getResources();
    }

    /**####################Start of TTS logic #################################################**/

    //Method to output the Strings on the start of the Activity
    protected void speakOutOnStart(final TextView... Tviews)
    {
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false)) {
            //This is needed because the TTS object is not so quickly initialized
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakOut(Tviews);
                }
            }, 1000);
        }
    }

    //Method to output the Strings on the start of the Activity
    protected void speakOutOnStart(final String... Strings)
    {
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false)) {
            //This is needed because the TTS object is not so quickly initialized
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakOut(Strings);
                }
            }, 1000);
        }
    }

    //Auditive output for Strings. Per String max. a length of 4000
    protected void speakOut (String ... strings)
    {
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false)) {
            if (strings != null) //Check if there is no NPE
            {
                //if (!globaleVariable.getToSpeech().isSpeaking()) //Check if TTS is speaking
                {
                    //Checks if the Lang_Data is available
                    if (globaleVariable.getResultTTS() == TextToSpeech.LANG_MISSING_DATA || globaleVariable.getResultTTS() == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                    } else {
                        //If the Lang_Data is available, then output the text
                        for (String n : strings) {
                            text = n;
                            globaleVariable.getToSpeech().speak(text, TextToSpeech.QUEUE_ADD, null); //Auditive Output

                            //Timeout between the other sentences
                            try {
                                Thread.sleep(1700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
//#################################################################################################################################
//Method to output the Strings on the start of the Activity
protected void speakOutOnStartOutput(final String... Strings)
{
    //This is needed because the TTS object is not so quickly initialized
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speakOutOutput(Strings);
            }
        }, 1000);

}

    //Auditive output for Strings. Per String max. a length of 4000
    protected void speakOutOutput (String ... strings)
    {
            if (strings != null) //Check if there is no NPE
            {
                if (!globaleVariable.getToSpeech().isSpeaking()) //Check if TTS is speaking
                {
                    //Checks if the Lang_Data is available
                    if (globaleVariable.getResultTTS() == TextToSpeech.LANG_MISSING_DATA || globaleVariable.getResultTTS() == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                    } else {
                        //If the Lang_Data is available, then output the text
                        for (String n : strings) {
                            text = n;
                            globaleVariable.getToSpeech().speak(text, TextToSpeech.QUEUE_ADD, null); //Auditive Output

                            //Timeout between the other sentences
                            try {
                                Thread.sleep(1700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    }

//#################################################################################################################################


    //Auditive output for Strings. per String max. 4000 length
    protected void speakOut (TextView ... Tviews)
    {
        if (getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuditoryFeedbakcOn", false)) {
            if (Tviews != null) {
                //Checks if the Lang_Data is available
                if (globaleVariable.getResultTTS() == TextToSpeech.LANG_MISSING_DATA || globaleVariable.getResultTTS() == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                } else {
                    //If the Lang_Data is available, then output the text
                    for (TextView n : Tviews) {
                        text = n.getText().toString(); //Get the Text from the Textviews
                        globaleVariable.getToSpeech().speak(text, TextToSpeech.QUEUE_ADD, null); //Auditive Output

                        //Timeout between the other sentences
                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



    //Stops and shutdown the TTS Engine if the Activity is finished
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (globaleVariable.getToSpeech() != null) {
            globaleVariable.getToSpeech().stop();
            globaleVariable.getToSpeech().shutdown();
        }
        /*
        if (toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }*/
    }


    //Stops the TTS Engine while a new Activity comes in the foreground
    @Override
    protected void onPause()
    {
        super.onPause();
        if (globaleVariable.getToSpeech() != null) {
            globaleVariable.getToSpeech().stop();
        }
    }


    //Stops the TTS Engine if the User tap on the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (globaleVariable.getToSpeech() != null) {
            globaleVariable.getToSpeech().stop();
        }
        return super.onTouchEvent(event);
    }




    /**####################End of TTS logic #################################################**/
    @Override
    public void onBackPressed() {
        backPressed++;
        if (globaleVariable.getToSpeech() != null) {
            globaleVariable.getToSpeech().stop();
        }
        if (backPressed > 1)    {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        int BACK_TIME_OUT = 500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceInteraction.super.onBackPressed();
            }
        }, BACK_TIME_OUT);
    }

    public void setGestureObject() {
        if (gestureObject == null) {
            this.gestureObject = new GestureDetectorCompat(DeviceInteraction.this, new DeviceInteraction.learnGesture());
        }
    }

    public void triggerEvent(MotionEvent pEvent) {
        gestureObject.onTouchEvent(pEvent);
    }


    class learnGesture extends GestureDetector.SimpleOnGestureListener  {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            // If swipe to the right open Camera
            if (event2.getX() - event1.getX() > 0) {

                // take new Pic
                Intent intent = new Intent(DeviceInteraction.this, PictureSelection.class);
                startActivity(intent);

            }
            // If swipe to the left open Settings
            else if (event2.getX() - event1.getX() < 0) {
                // return to Settings
                Intent intent = new Intent(DeviceInteraction.this, SettingsGeneral.class);
                startActivity(intent);
            }
            return true;
        }
        /**####################Start of STT logic #################################################**/
        @Override
        public void onLongPress(MotionEvent event)   {

            askSpeechInput();
        }
    }

    public void setRecordedResult(ArrayList<String> recordetResult) {
        this.recordedResult = recordetResult;

        doTaskFromResult();
    }

    private void doTaskFromResult() {

        int a = 0;
        int b = 4;
        if(recordedResult.size() < b)   {
            b = recordedResult.size();
        }

        while (a < b)   {
            if (recordedResult.get(a).toLowerCase().contains("hilfe"))  {
                // intent for guide here
                Intent intent = new Intent(DeviceInteraction.this, GuideSettings.class);
                startActivity(intent);
                break;
            }
            if (recordedResult.get(a).toLowerCase().contains("scannen"))  {
                // intent for cammeraActivity here
                Intent intent = new Intent(DeviceInteraction.this, CameraMode.class);
                startActivity(intent);
                break;
            }
            if (recordedResult.get(a).toLowerCase().contains("kamera"))  {
                // intent for cammeraActivity here
                Intent intent = new Intent(DeviceInteraction.this, CameraMode.class);
                startActivity(intent);
                break;
            }
            if (recordedResult.get(a).toLowerCase().contains("gallerie"))  {
                // intent for gallery here
                Intent intent = new Intent(DeviceInteraction.this, PictureSelection.class);
                startActivity(intent);
                break;
            }
            if (recordedResult.get(a).toLowerCase().contains("schließ") || recordedResult.get(a).toLowerCase().contains("close"))  {
                // intent fore close() here
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            }
            if (recordedResult.get(a).toLowerCase().contains("einstell") || recordedResult.get(a).toLowerCase().contains("setting"))  {
                // intent for settingsGeneral here
                Intent intent = new Intent(DeviceInteraction.this, SettingsGeneral.class);
                startActivity(intent);
                break;
            }

            if (recordedResult.get(a).toLowerCase().contains("sprache"))  {
                // intent for settingsLanguage here
                Intent intent = new Intent(DeviceInteraction.this, SettingsLanguage.class);
                startActivity(intent);
                break;
            }

            a++;
        }
    }

    // Showing google speech input dialog

    protected void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException ignored) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                    setRecordedResult(result);
                }
                break;
            }
        }
    }
    /**####################End of STT logic #################################################**/
}
