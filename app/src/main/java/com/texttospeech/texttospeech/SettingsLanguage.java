package com.texttospeech.texttospeech;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Objects;

public class SettingsLanguage extends DeviceInteraction {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    GlobalKontextVariables globaleVariable = GlobalKontextVariables.getInstance();

    private String header, question, choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_language);

        header = res.getString(R.string.SettingsLanguageHeader_AuditoryOutput);
        question = res.getString(R.string.SettingsLanguageQuestion_AuditoryOutput);
        choices = res.getString(R.string.SettingsLanguageChoices_AuditoryOutput);
        super.speakOutOnStart(header, question, choices);

        super.setGestureObject();

        //Hier wird das drop-down initialisert
        spinner = findViewById(R.id.spLanguage);
        adapter = ArrayAdapter.createFromResource(this, R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(globaleVariable.getInternLanguage());

                switch (i) {
                    case 1:
                        if (!Objects.equals("de", getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("appLanguage", null))) {
                            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("appLanguage", "de").apply();
                            globaleVariable.setAppLanguage("de");
                        }
                        break;
                    case 2:
                        if (!Objects.equals("en", getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("appLanguage", null))) {
                            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("appLanguage", "en").apply();
                            globaleVariable.setAppLanguage("en");
                        }
                        break;
                }
                spinner.setSelection(globaleVariable.getInternLanguage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, question, choices);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }
}
