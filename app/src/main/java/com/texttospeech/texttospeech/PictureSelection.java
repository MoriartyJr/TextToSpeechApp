package com.texttospeech.texttospeech;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class PictureSelection extends DeviceInteraction {

    //Control object
    TesseractControl controlObj;

    // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;

    private String header, question, choices; //, gallery, camera

    GlobalKontextVariables globalKontextVariables = GlobalKontextVariables.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selection);

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

        header = res.getString(R.string.PictureSelectionHeader_AuditoryOutput);
        question = res.getString(R.string.PictureSelectionQuestion_AuditoryOutput);
        choices = res.getString(R.string.PictureSelectionChoices_AuditoryOutput);
        super.speakOutOnStart(header, question, choices);


        //Singleton - Check if object already exists
        if (controlObj == null)
        {
            //Create the control object
            controlObj = new TesseractControl();
            //Set datapath in the control objekt
            controlObj.setDatapath(String.valueOf(getFilesDir()));
            //Set asset manager to asset of context
            controlObj.setAssetManager(getAssets());
            //Start configuration of the tesseract engine
            controlObj.configureTessearct();
        }

        super.setGestureObject();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header,question, choices);
    }

    //Method which is called when an image is selected from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {

            // Read picked image data -> URI
            Uri pickedImage = data.getData();

            // Read picked file path
            String[] filePath = { MediaStore.Images.Media.DATA };

            Cursor cursor = null;
            String imagePath = null;
            if (pickedImage != null) {
                cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            }
            if (cursor != null) {
                cursor.moveToFirst();
                //Get picked image path
                imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            }


            //Set control object image to picked image(Bitmap)
            controlObj.setImage(imagePath);

            //Method to get the analysed string out of picture
            controlObj.processImage();

            // At the end remember to close the cursor or you will end with the RuntimeException!
            if (cursor != null) {
                cursor.close();
            }
            gotoTextOutput();
        }
    }

    public void onButtonGalleryClick(View view) {
        // Create the Intent for Image Gallery.
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    public void onButtonCameraClick(View view) {
        Intent intent = new Intent(PictureSelection.this, CameraMode.class);
        startActivity(intent);
    }

    public void gotoTextOutput() {
        Intent intent = new Intent(PictureSelection.this, TextOutput.class);
        startActivity(intent);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }
}
