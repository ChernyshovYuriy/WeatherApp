package com.yuriy.weatherapp.business.vo;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import android.util.Log;

import java.io.Serializable;

/**
 * {@link com.yuriy.weatherapp.business.vo.WeatherItem} is a Value Object that stores
 * data related to the Weather Conditions such as Id of the condition, Description and Icon.
 */
public class WeatherItem implements Serializable {

    /**
     * Default value of the Id.
     */
    public static final int DEFAULT_CONDITION_ID = 0;

    /**
     * Default value of the group of weather parameters.
     */
    public static final String DEFAULT_MAIN = "";

    /**
     * Default value of the weather condition within the group.
     */
    public static final String DEFAULT_DESCRIPTION = "";

    /**
     * Default value of the weather icon.
     */
    public static final String DEFAULT_ICON = "";

    /**
     * Tag for the logging message.
     */
    private static final String CLASS_NAME = WeatherItem.class.getSimpleName();

    /**
     * Weather condition Id.
     */
    private int mId;

    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.).
     */
    private String mMain;

    /**
     * Weather condition within the group.
     */
    private String mDescription;

    /**
     * Weather icon Id.
     */
    private String mIcon;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private WeatherItem() { }

    /**
     * Constructor.
     *
     * @param id          Weather condition Id.
     * @param main        Group of weather parameters (Rain, Snow, Extreme etc.).
     * @param description Description of the weather condition within the group.
     * @param icon        Weather icon Id.
     */
    private WeatherItem(int id, String main, String description, String icon) {
        if (id < 0) {
            Log.w(CLASS_NAME, "Constructor -> Id is less the zero. Reset to default");
            id = DEFAULT_CONDITION_ID;
        }
        if (main == null) {
            Log.w(CLASS_NAME, "Constructor -> Main value is null. Reset to default");
            main = DEFAULT_MAIN;
        }
        if (description == null) {
            Log.w(CLASS_NAME, "Constructor -> Description value is null. Reset to default");
            description = DEFAULT_DESCRIPTION;
        }
        if (icon == null) {
            Log.w(CLASS_NAME, "Constructor -> Icon value is null. Reset to default");
            icon = DEFAULT_ICON;
        }
        mId = id;
        mMain = main;
        mDescription = description;
        mIcon = icon;
    }

    /**
     * @return Weather condition Id.
     */
    public int getId() {
        return mId;
    }

    /**
     * @return Group of weather parameters (Rain, Snow, Extreme etc.).
     */
    public String getMain() {
        return mMain;
    }

    /**
     * @return Description of the weather condition within the group.
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @return Weather icon Id.
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.WeatherItem}
     *
     * @param id          Weather condition Id.
     * @param main        Group of weather parameters (Rain, Snow, Extreme etc.).
     * @param description Description of the weather condition within the group.
     * @param icon        Weather icon Id.
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.WeatherItem}
     */
    public static WeatherItem getInstance(final int id, final String main, final String description,
                                          final String icon) {
        return new WeatherItem(id, main, description, icon);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.WeatherItem} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.WeatherItem}
     *         with default values.
     */
    public static WeatherItem getDefaultInstance() {
        return new WeatherItem(
                DEFAULT_CONDITION_ID, DEFAULT_MAIN, DEFAULT_DESCRIPTION, DEFAULT_ICON
        );
    }
}
