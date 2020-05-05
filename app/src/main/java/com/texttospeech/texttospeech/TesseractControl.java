package com.texttospeech.texttospeech;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TesseractControl extends Activity {

    private Bitmap image;
    private TessBaseAPI mTess;
    private String datapath = "";
    private String language = "";
    private AssetManager assetManager;
    private GlobalKontextVariables globaleVariable;


    public void configureTessearct() {

        //initialize Tesseract API

        setDatapath(getDatapath() + "/tesseract/");
        setmTess(new TessBaseAPI());

        globaleVariable = GlobalKontextVariables.getInstance();
        switch (globaleVariable.getSystemLanguage())
        {
            case "en":
                language = "eng";
                break;
            case "de":
                language = "deu";
                break;
            default:
        }

        checkFile(new File(getDatapath() + "tessdata/"));
        getmTess().init(getDatapath(), language);
        /*
        OEM_TESSERACT_ONLY,           // Run Tesseract only - fastest
        OEM_CUBE_ONLY,                // Run Cube only - better accuracy, but slower
        OEM_TESSERACT_CUBE_COMBINED,  // Run both and combine results - best accuracy
        OEM_DEFAULT */

    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = getDatapath() + "/tessdata/"+language+".traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = getDatapath() + "/tessdata/"+language+".traineddata";
            //setAssetManager(getAssets());

            InputStream instream = getAssetManager().open("tessdata/"+language+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }

            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processImage(){
        getmTess().setImage(getImage());
        globaleVariable.setAnalysedText(getmTess().getUTF8Text());
        getmTess().getUTF8Text();
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(String imagePath) {
        this.image = BitmapFactory.decodeFile(imagePath);
    }

    public TessBaseAPI getmTess() {
        return mTess;
    }

    public void setmTess(TessBaseAPI mTess) {
        this.mTess = mTess;
    }

    public String getDatapath() {
        return datapath;
    }

    public void setDatapath(String datapath) {
        this.datapath = datapath;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
