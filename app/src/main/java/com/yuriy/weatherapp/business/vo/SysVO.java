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
 * {@link com.yuriy.weatherapp.business.vo.SysVO} is a Value Object that stores
 * data related to the Sys Information such as Country abbreviation, Sunset and Sunrise time.
 */
public class SysVO implements Serializable {

    /**
     * Default value of the Country abbreviation.
     */
    public static final String DEFAULT_COUNTRY = "";

    /**
     * Default value of the Sunrise.
     */
    public static final long DEFAULT_SUNRISE = 0;

    /**
     * Default value of the Sunset.
     */
    public static final long DEFAULT_SUNSET = 0;

    /**
     * Tag for the logging messages.
     */
    private static final String CLASS_NAME = SysVO.class.getSimpleName();

    /**
     * Country (GB, JP etc.)
     */
    private String mCountry;

    /**
     * Sunrise time, unix, UTC
     */
    private long mSunrise;

    /**
     * Sunset time, unix, UTC
     */
    private long mSunset;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private SysVO() { }

    /**
     * Constructor.
     *
     * @param country Country abbreviation.
     * @param sunrise Sunrise time.
     * @param sunset  Sunset time.
     */
    private SysVO(String country, long sunrise, long sunset) {
        if (country == null) {
            Log.w(CLASS_NAME, "Constructor -> Country is null. Reset it to default");
            country = DEFAULT_COUNTRY;
        }
        if (sunrise < 0) {
            Log.w(CLASS_NAME, "Constructor -> Sunrise value is less then zero. Reset it to default");
            sunrise = DEFAULT_SUNRISE;
        }
        if (sunset < 0) {
            Log.w(CLASS_NAME, "Constructor -> Sunset value is less then zero. Reset it to default");
            sunset = DEFAULT_SUNSET;
        }
        mCountry = country;
        mSunrise = sunrise;
        mSunset = sunset;
    }

    /**
     * @return Country abbreviation.
     */
    public String getCountry() {
        return mCountry;
    }

    /**
     * @return Sunrise time.
     */
    public long getSunrise() {
        return mSunrise;
    }

    /**
     * @return Sunset time.
     */
    public long getSunset() {
        return mSunset;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.SysVO}
     *
     * @param countryAbbreviation Country abbreviation.
     * @param sunrise             Latitude value.
     * @param sunset              Longitude value.
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.SysVO}
     */
    public static SysVO getInstance(final String countryAbbreviation,
                                    final long sunrise, final long sunset) {
        return new SysVO(countryAbbreviation, sunrise, sunset);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.CoordVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.SysVO} with default values.
     */
    public static SysVO getDefaultInstance() {
        return new SysVO(DEFAULT_COUNTRY, DEFAULT_SUNRISE, DEFAULT_SUNSET);
    }
}
