package com.soft918.paintapp.domain.util;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class Theme {
    public static void setTheme(String themeId, SharedPreferences sharedPref){
        switch (themeId){
            case Constants.THEME_DAY: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                saveKeyThemeInDataStorePreferences(themeId,sharedPref);
                break;
            }
            case Constants.THEME_NIGHT: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                saveKeyThemeInDataStorePreferences(themeId,sharedPref);
                break;
            }
            case Constants.THEME_DEFAULT: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

                saveKeyThemeInDataStorePreferences(themeId,sharedPref);
                break;
            }
        }
    }
    private static void saveKeyThemeInDataStorePreferences(
            String themeId,
            SharedPreferences sharedPref
    ){
        sharedPref.edit().putString(Constants.THEME_PREFERENCES_KEY,themeId).apply();
    }
}
