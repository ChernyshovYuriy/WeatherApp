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
 * {@link com.yuriy.weatherapp.business.vo.WindVO} is a Value Object that stores
 * data related to Wind.
 */
public class WindVO implements Serializable {

    /**
     * Default value for the Wind Speed
     */
    public static final double DEFAULT_SPEED = Double.NaN;

    /**
     * Default value for the Wind Direction
     */
    public static final double DEFAULT_DEG = Double.NaN;

    /**
     * Min value for the Wind Speed
     */
    public static final double MIN_SPEED = 0;

    /**
     * Tag for the logging message.
     */
    private static final String CLASS_NAME = WindVO.class.getSimpleName();

    /**
     * Wind speed, mps.
     */
    private double mSpeed;

    /**
     * Wind direction, degrees (meteorological).
     */
    private double mDeg;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private WindVO() { }

    /**
     * Constructor,
     *
     * @param speed Wind speed value.
     * @param deg   Wind direction value.
     */
    public WindVO(double speed, final double deg) {
        if (speed < MIN_SPEED) {
            Log.w(CLASS_NAME, "Constructor -> Speed is less then minimum. Reset it to default");
            speed = DEFAULT_SPEED;
        }
        mSpeed = speed;
        mDeg = deg;
    }

    /**
     * @return Wind speed value.
     */
    public double getSpeed() {
        return mSpeed;
    }

    /**
     * @return Wind direction value.
     */
    public double getDeg() {
        return mDeg;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.WindVO}
     *
     * @param speed  Wind speed value.
     * @param deg    Wind direction value.
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.WindVO}
     */
    public static WindVO getInstance(final double speed, final double deg) {
        return new WindVO(speed, deg);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.WindVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.WindVO} with default values.
     */
    public static WindVO getDefaultInstance() {
        return new WindVO(DEFAULT_SPEED, DEFAULT_DEG);
    }
}
