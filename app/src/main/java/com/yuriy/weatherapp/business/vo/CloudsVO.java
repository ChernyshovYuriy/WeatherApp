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
 * {@link com.yuriy.weatherapp.business.vo.CloudsVO} is a Value object that stores
 * data related to the Clouds.
 */
public class CloudsVO implements Serializable {

    /**
     * Default value of the Cloudiness.
     */
    public final static double DEFAULT_VALUE = Double.NaN;

    /**
     * Min value of the Cloudiness.
     */
    public final static double MIN_VALUE = 0;

    /**
     * Nax value of the Cloudiness.
     */
    public final static double MAX_VALUE = 100;

    /**
     * Tag value for the logging message.
     */
    private static final String CLASS_NAME = CloudsVO.class.getSimpleName();

    /**
     * Cloudiness, %
     */
    private double mAll;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private CloudsVO() { }

    /**
     * Constructor.
     *
     * @param all Cloudiness, %.
     */
    private CloudsVO(double all) {
        if (all > MAX_VALUE || all < MIN_VALUE) {
            Log.w(CLASS_NAME, "Constructor -> Cloudiness is out of bounds:"
                    + all + ". Reset it to default");
            all = DEFAULT_VALUE;
        }
        mAll = all;
    }

    /**
     * @return Cloudiness value in %.
     */
    public double getAll() {
        return mAll;
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.CoordVO}
     *
     * @param all Cloudiness, %..
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.CoordVO}
     */
    public static CloudsVO getInstance(final double all) {
        return new CloudsVO(all);
    }

    /**
     * Factory method to instantiate {@link com.yuriy.weatherapp.business.vo.CoordVO} with
     * default values.
     *
     * @return Instance of the {@link com.yuriy.weatherapp.business.vo.CoordVO} with default values.
     */
    public static CloudsVO getDefaultInstance() {
        return new CloudsVO(DEFAULT_VALUE);
    }
}
