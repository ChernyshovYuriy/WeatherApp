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
 * {@link com.yuriy.weatherapp.business.vo.CoordVO} is a Value object that stores
 * data related to City Coordinates.
 */
public class CoordVO implements Serializable {

    /**
     * Default value for the Latitude
     */
    public static final double DEFAULT_LATITUDE = Double.NaN;

    /**
     * Default value for the Longitude
     */
    public static final double DEFAULT_LONGITUDE = Double.NaN;

    /**
     * Maximum value for the Longitude
     */
    public static final double MAX_LONGITUDE = 180;

    /**
     * Minimum value for the Longitude
     */
    public static final double MIN_LONGITUDE = -180;

    /**
     * Maximum value for the Latitude
     */
    public static final double MAX_LATITUDE = 90;

    /**
     * Minimum value for the Latitude
     */
    public static final double MIN_LATITUDE = -90;

    /**
     * Tag for the logging messages.
     */
    private static final String CLASS_NAME = CoordVO.class.getSimpleName();

    /**
     * City geo location, latitude.
     * Latitude measures the distance from the equator (LAT = 0) to either pole (LAT = 90N or 90S).
     */
    private double mLat;

    /**
     * City geo location, longitude.
     * Longitude measures the distance perpendicular to the equator.
     * The initial point runs through Greenwich England (LONG = 0).
     * You can then move either east or west to the opposite point on the earth's
     * circumference (LONG = 180W = 180E).
     */
    private double mLon;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private CoordVO() { }

    /**
     * Constructor.
     *
     * @param latitude  Latitude value.
     * @param longitude Longitude value.
     */
    private CoordVO(double latitude, double longitude) {
        if (latitude > MAX_LATITUDE || latitude < MIN_LATITUDE) {
            Log.w(CLASS_NAME, "Constructor -> Latitude is out of bounds:"
                    + latitude + ". Reset it to default");
            latitude = DEFAULT_LATITUDE;
        }
        if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
            Log.w(CLASS_NAME, "Constructor -> Longitude is out of bounds:"
                    + latitude + ". Reset it to default");
            longitude = DEFAULT_LATITUDE;
        }
        mLat = latitude;
        mLon = longitude;
    }

    /**
     * @return Latitude.
     */
    public double getLat() {
        return mLat;
    }

    /**
     * @return Longitude.
     */
    public double getLon() {
        return mLon;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.CoordVO}
     *
     * @param latitude  Latitude value.
     * @param longitude Longitude value.
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.CoordVO}
     */
    public static CoordVO getInstance(final double latitude, final double longitude) {
        return new CoordVO(latitude, longitude);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.CoordVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.CoordVO} with default values.
     */
    public static CoordVO getDefaultInstance() {
        return new CoordVO(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }
}
