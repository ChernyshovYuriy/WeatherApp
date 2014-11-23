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
 * {@link com.yuriy.weatherapp.business.vo.RainVO} is a Value Object that stores
 * data related to the Rain.
 */
public class RainVO implements Serializable {

    /**
     * Default value for the Precipitation.
     */
    public static final int DEFAULT_3H = 0;

    /**
     * Tag to use in logging message,
     */
    private static final String CLASS_NAME = RainVO.class.getSimpleName();

    /**
     * Precipitation volume for last 3 hours, mm
     */
    private int m3h;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private RainVO() { }

    /**
     * Constructor.
     *
     * @param m3h Precipitation volume for last 3 hours, mm
     */
    private RainVO(int m3h) {
        if (m3h < DEFAULT_3H) {
            Log.w(CLASS_NAME, "Constructor -> 3H value is less then default. Reset it to default.");
            m3h = DEFAULT_3H;
        }
        this.m3h = m3h;
    }

    /**
     * @return Precipitation volume for last 3 hours, mm.
     */
    public int get3h() {
        return m3h;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.RainVO}
     *
     * @param m3h Precipitation volume for last 3 hours, mm
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.RainVO}
     */
    public static RainVO getInstance(final int m3h) {
        return new RainVO(m3h);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.RainVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.RainVO} with default values.
     */
    public static RainVO getDefaultInstance() {
        return new RainVO(DEFAULT_3H);
    }
}
