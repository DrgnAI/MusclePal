package com.example.musclepal;
import android.app.Application;

public class userSettings extends Application{

    public static final String PREFERENCES = "preferences";
    public static final String CUSTOM_THEME = "custom_Theme";
    public static final String LIGHT_THEME = "light_Theme";
    public static final String DARK_THEME = "dark_Theme";

    private String customTheme;

    public String getCustomTheme() {//stores and gets user preferences for theme
        return customTheme;
    }

    public void setCustomTheme(String customTheme) {
        this.customTheme = customTheme;
    }
}
