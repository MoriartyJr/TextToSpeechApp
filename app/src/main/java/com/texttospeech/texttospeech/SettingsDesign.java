package com.texttospeech.texttospeech;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SettingsDesign extends DeviceInteraction {

    Boolean brightTheme;
    Boolean darkTheme;
    Boolean constrastTheme;

    private String header, question, choices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_design);

        header = res.getString(R.string.SettingsDesignHeader_AuditoryOutput);
        question = res.getString(R.string.SettingsDesignQuestionLight_AuditoryOutput);
        choices = res.getString(R.string.SettingsDesignChoices_AuditoryOutput);
        super.speakOutOnStart(header, question, choices);

        super.setGestureObject();

        getPrefTheme();
        setTheme();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        speakOut(header, question, choices);
    }

    private void getPrefTheme() {
        brightTheme = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("brightTheme", true);
        darkTheme = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("darkTheme", false);
        constrastTheme = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("constrastTheme", false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        super.triggerEvent(event);
        return super.onTouchEvent(event);
    }

    public void setBrightTheme(View view) {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("brightTheme", true).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("darkTheme", false).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("constrastTheme", false).apply();

        Button BrightTheme = this.findViewById(R.id.btBrightTheme);
        BrightTheme.setBackgroundResource(R.color.Active);

        Button DarkTheme = this.findViewById(R.id.btDarkTheme);
        DarkTheme.setBackgroundResource(R.color.Inactive);

        Button ConstrastTheme = this.findViewById(R.id.btConstrastTheme);
        ConstrastTheme.setBackgroundResource(R.color.Inactive);
    }

    public void setDarkTheme(View view) {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("brightTheme", false).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("darkTheme", true).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("constrastTheme", false).apply();

        Button DarkTheme = this.findViewById(R.id.btDarkTheme);
        DarkTheme.setBackgroundResource(R.color.Active);

        Button BrightTheme = this.findViewById(R.id.btBrightTheme);
        BrightTheme.setBackgroundResource(R.color.Inactive);

        Button ConstrastTheme = this.findViewById(R.id.btConstrastTheme);
        ConstrastTheme.setBackgroundResource(R.color.Inactive);
    }
    public void setConstrastTheme(View view) {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("brightTheme", false).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("darkTheme", false).apply();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("constrastTheme", true).apply();

        Button ConstrastTheme = this.findViewById(R.id.btConstrastTheme);
        ConstrastTheme.setBackgroundResource(R.color.Active);

        Button BrightTheme = this.findViewById(R.id.btBrightTheme);
        BrightTheme.setBackgroundResource(R.color.Inactive);

        Button DarkTheme = this.findViewById(R.id.btDarkTheme);
        DarkTheme.setBackgroundResource(R.color.Inactive);
    }

    private void setTheme()
    {
        if(brightTheme)
        {
            setBrightTheme(this.findViewById(R.id.btBrightTheme));
        }
        else if (darkTheme)
        {
            setDarkTheme(this.findViewById(R.id.btDarkTheme));
        }
        else if (constrastTheme)
        {
            setConstrastTheme(this.findViewById(R.id.btConstrastTheme));
        }
    }
}
