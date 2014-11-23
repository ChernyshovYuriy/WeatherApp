package com.yuriy.weatherapp.business;

import android.content.Context;
import android.content.SharedPreferences;

import com.yuriy.weatherapp.business.vo.TemperatureFormat;

/**
 * Created with Android Studio.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 12/27/13
 * Time: 10:51 AM
 */
public class AppPreferencesManager {

    /**
     * Name of the Preferences file.
     */
    private static final String PREFS_NAME = "WeatherAppPreferences";

    private static final String PREF_KEY_TEMP_FORMAT = "PREF_KEY_TEMP_FORMAT";

    /**
     * application context to use in the preferences.
     */
    private static Context sContext;

    /**
     * Set Application context
     *
     * @param value Application context.
     */
    public static void setAppContext(final Context value) {
        sContext = value;
    }

    /**
     * Set current format of the Temperature.
     * @param value Temperature format {@link com.yuriy.weatherapp.business.vo.TemperatureFormat}.
     */
    public static void setTemperatureFormat(final TemperatureFormat value) {
        final SharedPreferences.Editor editor = getEditor();
        editor.putString(PREF_KEY_TEMP_FORMAT, value.toString());
        editor.apply();
    }

    /**
     * @return Current format of the Temperature.
     */
    public static TemperatureFormat getTemperatureFormat() {
        final String value = getSharedPreferences().getString(
                PREF_KEY_TEMP_FORMAT, TemperatureFormat.FAHRENHEIT.toString()
        );
        return TemperatureFormat.valueOf(value);
    }

    /**
     * @return the Context of the Application
     */
    private static Context getAppContext() {
        return sContext;
    }

    /**
     * @return {@link android.content.SharedPreferences} of the Application
     */
    private static SharedPreferences getSharedPreferences() {
        return getAppContext().getSharedPreferences(PREFS_NAME, 0);
    }

    /**
     * @return {@link android.content.SharedPreferences.Editor}
     */
    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }
}