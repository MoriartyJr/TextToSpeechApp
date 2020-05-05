package com.texttospeech.texttospeech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class CameraMode extends DeviceInteraction {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    //Control object
    TesseractControl controlObj;

    private String auditoryTextCameraMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_mode);

        auditoryTextCameraMode = res.getString(R.string.CameraMode_AuditoryOutput);
        super.speakOutOnStart(auditoryTextCameraMode);

        super.setGestureObject();

        dispatchTakePictureIntent();

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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(auditoryTextCameraMode);
    }

    private void dispatchTakePictureIntent()  {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            //Set control object image to picked image(Bitmap)
            controlObj.setImage(getRealPathFromURI(tempUri));

            //Method to get the analysed string out of picture
            controlObj.processImage();

            gotoTextOutput();
        }

        else if (resultCode==Activity.RESULT_CANCELED)  {
            Toast.makeText(this, "Scan canceled. Swipe right to retry.", Toast.LENGTH_LONG).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public void gotoTextOutput() {
        Intent intent = new Intent(CameraMode.this, TextOutput.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }
}