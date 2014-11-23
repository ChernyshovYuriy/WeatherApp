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
 * {@link com.yuriy.weatherapp.business.vo.MainVO} is a Value Object that stores
 * data related to main information about weather, such as Temperature, Humidity and so on .
 */
public class MainVO implements Serializable {

    /**
     * Default value for the Temperature;
     */
    public static final double DEFAULT_TEMPERATURE = Double.NaN;

    /**
     * Default value for the Minimum Temperature;
     */
    public static final double DEFAULT_MIN_TEMPERATURE = Double.NaN;

    /**
     * Default value for the Maximum Temperature;
     */
    public static final double DEFAULT_MAX_TEMPERATURE = Double.NaN;

    /**
     * Default value for the Humidity;
     */
    public static final double DEFAULT_HUMIDITY = Double.NaN;

    /**
     * Default value for the Pressure;
     */
    public static final double DEFAULT_PRESSURE = Double.NaN;

    /**
     * Tag for the logging message.
     */
    private static final String CLASS_NAME = MainVO.class.getSimpleName();

    /**
     * Temperature, Kelvin (subtract 273.15 to convert to Celsius)
     */
    private double mTemperature;

    /**
     * Humidity, %
     */
    private double mHumidity;

    /**
     * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     */
    private double mPressure;

    /**
     * Minimum temperature at the moment
     * This is deviation from current temp that is possible for large cities and
     * megalopolises geographically expanded (use these parameter optionally)
     */
    private double mMinTemperature;

    /**
     * Maximum temperature at the moment.
     * This is deviation from current temp that is possible for large cities and
     * megalopolises geographically expanded (use these parameter optionally)
     */
    private double mMaxTemperature;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private MainVO() { }

    /**
     * Constructor.
     *
     * @param temperature    Temperature, Kelvin (subtract 273.15 to convert to Celsius).
     * @param humidity       Humidity, %
     * @param pressure       Atmospheric pressure (on the sea level,
     *                        if there is no sea_level or grnd_level data), hPa
     * @param minTemperature Minimum temperature at the moment
     *                        This is deviation from current temp that is possible for large cities
     *                        and megalopolises geographically expanded
     *                        (use these parameter optionally)
     * @param maxTemperature Maximum temperature at the moment
     *                        This is deviation from current temp that is possible for large cities
     *                        and megalopolises geographically expanded
     *                        (use these parameter optionally)
     */
    private MainVO(final double temperature, double humidity, double pressure,
                   final double minTemperature, final double maxTemperature) {
        if (humidity < 0) {
            Log.w(CLASS_NAME, "Constructor -> Humidity is less the 0. Reset it to default");
            humidity = DEFAULT_HUMIDITY;
        }
        if (pressure < 0) {
            Log.w(CLASS_NAME, "Constructor -> Pressure is less the 0. Reset it to default");
            pressure = DEFAULT_PRESSURE;
        }
        mTemperature = temperature;
        mHumidity = humidity;
        mPressure = pressure;
        mMinTemperature = minTemperature;
        mMaxTemperature = maxTemperature;
    }

    /**
     * @return Temperature, Kelvin (subtract 273.15 to convert to Celsius).
     */
    public double getTemperature() {
        return mTemperature;
    }

    /**
     * @return Humidity, %
     */
    public double getHumidity() {
        return mHumidity;
    }

    /**
     * @return Atmospheric pressure (on the sea level, if there is no sea_level or
     *         grnd_level data), hPa
     */
    public double getPressure() {
        return mPressure;
    }

    /**
     * @return Minimum Temperature.
     */
    public double getMinTemperature() {
        return mMinTemperature;
    }

    /**
     * @return Maximum Temperature.
     */
    public double getMaxTemperature() {
        return mMaxTemperature;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.MainVO}
     *
     * @param temperature    Temperature, Kelvin (subtract 273.15 to convert to Celsius).
     * @param humidity       Humidity, %
     * @param pressure       Atmospheric pressure (on the sea level there is no sea_level or
     *                       grnd_level data), hPa
     * @param minTemperature Minimum temperature at the This deviation from current temp that is
     *                       possible for large and megalopolises geographically
     *                       (use these parameter optionally)
     * @param maxTemperature Maximum temperature at the This deviation from current temp that is
     *                       possible for large and megalopolises geographically
     *                       (use these parameter optionally)
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.MainVO}
     */
    public static MainVO getInstance(final double temperature, final double humidity,
                                     final double pressure, final double minTemperature,
                                     final double maxTemperature) {
        return new MainVO(temperature, humidity, pressure, minTemperature, maxTemperature);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.MainVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.MainVO} with default values.
     */
    public static MainVO getDefaultInstance() {
        return new MainVO(DEFAULT_TEMPERATURE, DEFAULT_HUMIDITY, DEFAULT_PRESSURE,
                DEFAULT_MIN_TEMPERATURE, DEFAULT_MAX_TEMPERATURE
        );
    }
}
