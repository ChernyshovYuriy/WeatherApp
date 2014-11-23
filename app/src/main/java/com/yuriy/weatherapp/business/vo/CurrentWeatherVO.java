package com.yuriy.weatherapp.business.vo;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link CurrentWeatherVO} is a Value Object which stores information about
 * weather condition in the concrete place, received from the online weather service.
 */
public class CurrentWeatherVO implements Serializable {

    /**
     * Default value for the Data receiving time.
     */
    public static final double DEFAULT_DT = Double.NaN;

    /**
     * Min value for the Data receiving time.
     */
    public static final double MIN_DT = 0.0;

    /**
     * Default value for the City identification.
     */
    public static final int DEFAULT_CITY_ID = 0;

    /**
     * Min value for the Data City identification.
     */
    public static final int MIN_CITY_ID = 0;

    /**
     * Default value for the City name.
     */
    public static final String DEFAULT_CITY_NAME = "";

    /**
     * Default value for the weather condition code.
     */
    public static final int DEFAULT_COD = 200;

    /**
     * Tag name to use in the logging.
     */
    private static final String CLASS_NAME = CurrentWeatherVO.class.getSimpleName();

    /**
     * This field stores City coordinates.
     */
    private CoordVO mCoordVO;

    /**
     * This field stores Sys information.
     */
    private SysVO mSysVO;

    /**
     * This field stores collection of the weather items.
     */
    private final List<WeatherItem> mWeather = new ArrayList<WeatherItem>();

    /**
     * This field stores Main information about weather.
     */
    private MainVO mMainVO;

    /**
     * This field stores information about Wind.
     */
    private WindVO mWindVO;

    /**
     * This field stores information about Rain.
     */
    private RainVO mRainVO;

    /**
     * This field stores information about Clouds.
     */
    private CloudsVO mCloudsVO;

    /**
     * Data receiving time, unix time, GMT
     */
    private double mDt = DEFAULT_DT;

    /**
     * City identification.
     */
    private int mCityId = DEFAULT_CITY_ID;

    /**
     * City name.
     */
    private String mCityName = DEFAULT_CITY_NAME;

    /**
     * Weather condition code.
     */
    private int mCod = DEFAULT_COD;

    /**
     * Private Constructor to protect unexpected instance.
     */
    private CurrentWeatherVO() { }

    /**
     * @return City coordinates stored in the {@link com.yuriy.weatherapp.business.vo.CoordVO}
     *         (default one is return when null is found).
     */
    public CoordVO getCoordVO() {
        if (mCoordVO == null) {
            setCoordVO(CoordVO.getDefaultInstance());
        }
        return mCoordVO;
    }

    /**
     * Set City coordinates stored in the {@link com.yuriy.weatherapp.business.vo.CoordVO}.
     *
     * @param value {@link com.yuriy.weatherapp.business.vo.CoordVO}.
     */
    public void setCoordVO(final CoordVO value) {
        mCoordVO = value;
    }

    /**
     * @return Sys information (default one is return when null is found).
     */
    public SysVO getSysVO() {
        if (mSysVO == null) {
            setSysVO(SysVO.getDefaultInstance());
        }
        return mSysVO;
    }

    /**
     * Set Sys information.
     *
     * @param value {@link com.yuriy.weatherapp.business.vo.SysVO}
     */
    public void setSysVO(final SysVO value) {
        mSysVO = value;
    }

    /**
     * Returns {@link com.yuriy.weatherapp.business.vo.WeatherItem} from the collection
     * at the specified position.
     *
     * @param index Index of the item in collection. Must be greater the or equal zero.
     * @return {@link com.yuriy.weatherapp.business.vo.WeatherItem} or default instance if index is
     *         less then zero or greater the size of the collection.
     */
    public WeatherItem getWeatherItemAt(final int index) {
        if (index < 0 || index > mWeather.size() - 1) {
            return WeatherItem.getDefaultInstance();
        }
        return mWeather.get(index);
    }

    /**
     * This method add {@link com.yuriy.weatherapp.business.vo.WeatherItem} to the collection of
     * items.
     *
     * @param weatherItem {@link com.yuriy.weatherapp.business.vo.WeatherItem}.
     */
    public void addWeatherItem(final WeatherItem weatherItem) {
        if (weatherItem == null) {
            Log.w(CLASS_NAME, "Can not add null as Weather Item");
            return;
        }
        mWeather.add(weatherItem);
    }

    /**
     * @return Size of the collection of the {@link com.yuriy.weatherapp.business.vo.WeatherItem}.
     */
    public int getWeatherItemsSize() {
        return mWeather.size();
    }

    /**
     * @return {@link com.yuriy.weatherapp.business.vo.MainVO}
     *         (default one is return when null is found).
     */
    public MainVO getMainVO() {
        if (mMainVO == null) {
            setMainVO(MainVO.getDefaultInstance());
        }
        return mMainVO;
    }

    /**
     * Set {@link com.yuriy.weatherapp.business.vo.MainVO}.
     *
     * @param value {@link com.yuriy.weatherapp.business.vo.MainVO}
     */
    public void setMainVO(final MainVO value) {
        mMainVO = value;
    }

    /**
     * Factory method to instantiate {@link CurrentWeatherVO}
     *
     * @return Instance of the {@link CurrentWeatherVO}
     */
    public static CurrentWeatherVO getInstance() {
        return new CurrentWeatherVO();
    }

    /**
     * @return Wind value (default one is return when null is found).
     */
    public WindVO getWindVO() {
        if (mWindVO == null) {
            setWindVO(WindVO.getDefaultInstance());
        }
        return mWindVO;
    }

    /**
     * Set Wind value.
     *
     * @param value {@link com.yuriy.weatherapp.business.vo.WindVO}.
     */
    public void setWindVO(final WindVO value) {
        mWindVO = value;
    }

    /**
     * @return Rain value (default one is return when null is found).
     */
    public RainVO getRainVO() {
        if (mRainVO == null) {
            setRainVO(RainVO.getDefaultInstance());
        }
        return mRainVO;
    }

    /**
     * Set Rain information.
     *
     * @param value Rain information.
     */
    public void setRainVO(final RainVO value) {
        mRainVO = value;
    }

    /**
     * @return Cloudiness information  (default one is return when null is found).
     */
    public CloudsVO getCloudsVO() {
        if (mCloudsVO == null) {
            setCloudsVO(CloudsVO.getDefaultInstance());
        }
        return mCloudsVO;
    }

    /**
     * Set Cloudiness information.
     *
     * @param value Cloudiness information.
     */
    public void setCloudsVO(final CloudsVO value) {
        mCloudsVO = value;
    }

    /**
     * @return Data receiving time, unix time, GMT
     */
    public double getDt() {
        if (mDt < MIN_DT) {
            Log.w(CLASS_NAME, "Data receiving time less the Min. Reset to default.");
            setDt(DEFAULT_DT);
        }
        return mDt;
    }

    /**
     * Set Data receiving time, unix time, GMT
     *
     * @param dt Data receiving time, unix time, GMT
     */
    public void setDt(final double dt) {
        mDt = dt;
    }

    /**
     * @return City identification.
     */
    public int getCityId() {
        if (mCityId < MIN_CITY_ID) {
            Log.w(CLASS_NAME, "City Id less the Min. Reset to default.");
            setCityId(DEFAULT_CITY_ID);
        }
        return mCityId;
    }

    /**
     * Set City identification.
     *
     * @param value Value of the City identification.
     */
    public void setCityId(final int value) {
        mCityId = value;
    }

    /**
     * @return City name.
     */
    public String getCityName() {
        if (mCityName == null) {
            Log.w(CLASS_NAME, "City Name is null. Reset to default.");
            setCityName(DEFAULT_CITY_NAME);
        }
        return mCityName;
    }

    /**
     * Set City name.
     *
     * @param value Value of the City name.
     */
    public void setCityName(final String value) {
        mCityName = value;
    }

    /**
     * @return Weather condition code.
     */
    public int getCod() {
        if (mCod < DEFAULT_COD) {
            Log.w(CLASS_NAME, "Weather condition code is less then default. Reset to default.");
            setCod(DEFAULT_COD);
        }
        return mCod;
    }

    /**
     * Set Weather condition code.
     *
     * @param value Weather condition code.
     */
    public void setCod(final int value) {
        mCod = value;
    }
}
