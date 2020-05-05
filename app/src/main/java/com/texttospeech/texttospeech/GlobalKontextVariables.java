package com.texttospeech.texttospeech;

import android.content.res.Resources;
import android.speech.tts.TextToSpeech;

class GlobalKontextVariables {
    private static GlobalKontextVariables instance;
    private static String analysedText;
    private static String systemLanguage;
    private static int internLanguage;
    private static TextToSpeech toSpeech;
    private static int resultTTS;
    private static String systemLanguageLastUse;
    private static String appLanguage;

    String getSystemLanguageLastUse() {
        return systemLanguageLastUse;
    }

    void setSystemLanguageLastUse(String systemLanguageLastUse) {
        GlobalKontextVariables.systemLanguageLastUse = systemLanguageLastUse;
    }

    void setAppLanguage(String appLanguage) {
        GlobalKontextVariables.appLanguage = appLanguage;
        switch (GlobalKontextVariables.appLanguage)
        {
            case "de":
                setInternLanguage(1);
                break;
            case "en":
                setInternLanguage(2);
                break;
        }
    }

    TextToSpeech getToSpeech() {
        return toSpeech;
    }

    void setToSpeech(TextToSpeech toSpeech) {
        GlobalKontextVariables.toSpeech = toSpeech;
    }

    int getResultTTS() {
        return resultTTS;
    }

    void setResultTTS(int resultTTS) {
        GlobalKontextVariables.resultTTS = resultTTS;
    }

    String getAnalysedText() {
        return GlobalKontextVariables.analysedText;
    }

    void setAnalysedText(String pAnalysedText) {
        GlobalKontextVariables.analysedText = pAnalysedText;
    }

    String getSystemLanguage() {return GlobalKontextVariables.systemLanguage; }

    private static void setSystemLanguage()
    {
        GlobalKontextVariables.systemLanguage = Resources.getSystem().getConfiguration().locale.getLanguage();
    }

    static synchronized GlobalKontextVariables getInstance(){
        if (instance== null) {
            instance = new GlobalKontextVariables();
            setSystemLanguage();
        }
        return instance;
    }

    private void setInternLanguage(int language) {
        GlobalKontextVariables.internLanguage=language;
    }

    int getInternLanguage(){
        return GlobalKontextVariables.internLanguage;
    }
}
