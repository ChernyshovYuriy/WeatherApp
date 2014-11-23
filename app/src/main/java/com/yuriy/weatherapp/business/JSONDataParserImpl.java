package com.yuriy.weatherapp.business;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import android.util.Log;

import com.yuriy.weatherapp.business.vo.CloudsVO;
import com.yuriy.weatherapp.business.vo.CoordVO;
import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.business.vo.MainVO;
import com.yuriy.weatherapp.business.vo.RainVO;
import com.yuriy.weatherapp.business.vo.SysVO;
import com.yuriy.weatherapp.business.vo.WeatherItem;
import com.yuriy.weatherapp.business.vo.WindVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.yuriy.weatherapp.business.JSONDataParserImpl} is implementation of the
 * {@link com.yuriy.weatherapp.business.DataParser} interface and allows to parse raw
 * data of the weather which are represents by the JSON data format.
 */
public class JSONDataParserImpl implements DataParser {

    /**
     * JSON keys
     */
    protected static final String KEY_COORD = "coord";
    protected static final String KEY_LON = "lon";
    protected static final String KEY_LAT = "lat";
    protected static final String KEY_SYS = "sys";
    protected static final String KEY_COUNTRY = "country";
    protected static final String KEY_SUNRISE = "sunrise";
    protected static final String KEY_SUNSET = "sunset";
    protected static final String KEY_WEATHER = "weather";
    protected static final String KEY_MAIN = "main";
    protected static final String KEY_TEMP = "temp";
    protected static final String KEY_HUMIDITY = "humidity";
    protected static final String KEY_PRESSURE = "pressure";
    protected static final String KEY_TEMP_MIN = "temp_min";
    protected static final String KEY_TEMP_MAX = "temp_max";
    protected static final String KEY_DESCRIPTION = "description";
    protected static final String KEY_ICON = "icon";
    protected static final String KEY_WIND = "wind";
    protected static final String KEY_SPEED = "speed";
    protected static final String KEY_DEG = "deg";
    protected static final String KEY_RAIN = "rain";
    protected static final String KEY_3H = "3h";
    protected static final String KEY_CLOUDS = "clouds";
    protected static final String KEY_ALL = "all";
    protected static final String KEY_DT = "dt";
    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_COD = "cod";

    /**
     * Tag to use in the logging.
     */
    private static final String CLASS_NAME = JSONDataParserImpl.class.getSimpleName();

    @Override
    public CoordVO parseCityCoordinates(final String inputData) {
        CoordVO coordVO = CoordVO.getDefaultInstance();
        final JSONObject coordJSON = getJSONObjectFromMainJSONObject(inputData, KEY_COORD);
        if (coordJSON.length() == 0) {
            return coordVO;
        }
        if (!coordJSON.has(KEY_LAT) && !coordJSON.has(KEY_LON)) {
            return coordVO;
        }
        try {
            coordVO = CoordVO.getInstance(
                    coordJSON.getDouble(KEY_LAT), coordJSON.getDouble(KEY_LON)
            );
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse City coordinates exception:" + e.getMessage());
        }
        return coordVO;
    }

    @Override
    public SysVO parseSysInfo(final String inputData) {
        SysVO sysVO = SysVO.getDefaultInstance();
        final JSONObject sysJSON = getJSONObjectFromMainJSONObject(inputData, KEY_SYS);
        if (sysJSON.length() == 0) {
            return sysVO;
        }
        String country = SysVO.DEFAULT_COUNTRY;
        long sunrise = SysVO.DEFAULT_SUNRISE;
        long sunset = SysVO.DEFAULT_SUNSET;
        try {
            if (sysJSON.has(KEY_COUNTRY)) {
                country = sysJSON.getString(KEY_COUNTRY);
            }
            if (sysJSON.has(KEY_SUNRISE)) {
                sunrise = sysJSON.getLong(KEY_SUNRISE);
            }
            if (sysJSON.has(KEY_SUNSET)) {
                sunset = sysJSON.getLong(KEY_SUNSET);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Sys information exception:" + e.getMessage());
        }
        sysVO = SysVO.getInstance(country, sunrise, sunset);
        return sysVO;
    }

    @Override
    public List<WeatherItem> parseWeather(final String inputData) {
        final List<WeatherItem> weatherItems = new ArrayList<WeatherItem>();
        final JSONObject jsonObject = getJSONFromRawData(inputData);
        if (!jsonObject.has(KEY_WEATHER)) {
            return weatherItems;
        }
        JSONArray weatherJSON = null;
        try {
            weatherJSON = jsonObject.getJSONArray(KEY_WEATHER);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse weather conditions exception:" + e.getMessage());
        }
        if (weatherJSON == null) {
            return weatherItems;
        }
        JSONObject item;
        int id;
        String main;
        String description;
        String icon;
        WeatherItem weatherItem;
        try {
            for (int i = 0; i < weatherJSON.length(); i++) {
                item = weatherJSON.getJSONObject(0);
                if (item == null) {
                    continue;
                }
                id = WeatherItem.DEFAULT_CONDITION_ID;
                main = WeatherItem.DEFAULT_MAIN;
                description = WeatherItem.DEFAULT_DESCRIPTION;
                icon = WeatherItem.DEFAULT_ICON;
                if (item.has(KEY_ID)) {
                    id = item.getInt(KEY_ID);
                }
                if (item.has(KEY_MAIN)) {
                    main = item.getString(KEY_MAIN);
                }
                if (item.has(KEY_DESCRIPTION)) {
                    description = item.getString(KEY_DESCRIPTION);
                }
                if (item.has(KEY_ICON) ) {
                    icon = item.getString(KEY_ICON);
                }
                weatherItem = WeatherItem.getInstance(id, main, description, icon);
                weatherItems.add(weatherItem);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse weather conditions exception:" + e.getMessage());
        }
        return weatherItems;
    }

    @Override
    public MainVO parseMainInfo(final String inputData) {
        MainVO mainVO = MainVO.getDefaultInstance();
        final JSONObject mainJSON = getJSONObjectFromMainJSONObject(inputData, KEY_MAIN);
        if (mainJSON.length() == 0) {
            return mainVO;
        }
        double temperature = MainVO.DEFAULT_TEMPERATURE;
        double humidity = MainVO.DEFAULT_HUMIDITY;
        double pressure = MainVO.DEFAULT_PRESSURE;
        double minTemperature = MainVO.DEFAULT_MIN_TEMPERATURE;
        double maxTemperature = MainVO.DEFAULT_MAX_TEMPERATURE;
        try {
            if (mainJSON.has(KEY_TEMP)) {
                temperature = mainJSON.getDouble(KEY_TEMP);
            }
            if (mainJSON.has(KEY_TEMP_MAX)) {
                maxTemperature = mainJSON.getDouble(KEY_TEMP_MAX);
            }
            if (mainJSON.has(KEY_TEMP_MIN)) {
                minTemperature = mainJSON.getDouble(KEY_TEMP_MIN);
            }
            if (mainJSON.has(KEY_HUMIDITY)) {
                humidity = mainJSON.getLong(KEY_HUMIDITY);
            }
            if (mainJSON.has(KEY_PRESSURE)) {
                pressure = mainJSON.getLong(KEY_PRESSURE);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Main information exception:" + e.getMessage());
        }
        mainVO = MainVO.getInstance(
                temperature, humidity, pressure, minTemperature, maxTemperature
        );
        return mainVO;
    }

    @Override
    public WindVO parseWind(final String inputData) {
        WindVO windVO = WindVO.getDefaultInstance();
        final JSONObject windJSON = getJSONObjectFromMainJSONObject(inputData, KEY_WIND);
        if (windJSON.length() == 0) {
            return windVO;
        }
        double windSpeed = WindVO.DEFAULT_SPEED;
        double windDirection = WindVO.DEFAULT_DEG;
        try {
            if (windJSON.has(KEY_SPEED)) {
                windSpeed = windJSON.getDouble(KEY_SPEED);
            }
            if (windJSON.has(KEY_DEG)) {
                windDirection = windJSON.getDouble(KEY_DEG);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Wind information exception:" + e.getMessage());
        }
        windVO = WindVO.getInstance(windSpeed, windDirection);
        return windVO;
    }

    @Override
    public RainVO parseRain(final String inputData) {
        RainVO rainVO = RainVO.getDefaultInstance();
        final JSONObject rainJSON = getJSONObjectFromMainJSONObject(inputData, KEY_RAIN);
        if (rainJSON.length() == 0) {
            return rainVO;
        }
        int _3h = RainVO.DEFAULT_3H;
        try {
            if (rainJSON.has(KEY_SPEED)) {
                _3h = rainJSON.getInt(KEY_3H);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Rain information exception:" + e.getMessage());
        }
        rainVO = RainVO.getInstance(_3h);
        return rainVO;
    }

    @Override
    public CloudsVO parseClouds(final String inputData) {
        CloudsVO cloudsVO = CloudsVO.getDefaultInstance();
        final JSONObject cloudsJSON = getJSONObjectFromMainJSONObject(inputData, KEY_CLOUDS);
        if (cloudsJSON.length() == 0) {
            return cloudsVO;
        }
        double cloudiness = CloudsVO.DEFAULT_VALUE;
        try {
            if (cloudsJSON.has(KEY_ALL)) {
                cloudiness = cloudsJSON.getInt(KEY_ALL);
            }
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Clouds information exception:" + e.getMessage());
        }
        cloudsVO = CloudsVO.getInstance(cloudiness);
        return cloudsVO;
    }

    @Override
    public double parseDt(final String inputData) {
        final JSONObject mainJSON = getJSONFromRawData(inputData);
        if (mainJSON.length() == 0) {
            return CurrentWeatherVO.DEFAULT_DT;
        }
        try {
            return mainJSON.getDouble(KEY_DT);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse Data receiving time exception:" + e.getMessage());
            return CurrentWeatherVO.DEFAULT_DT;
        }
    }

    @Override
    public int parseId(final String inputData) {
        final JSONObject mainJSON = getJSONFromRawData(inputData);
        if (mainJSON.length() == 0) {
            return CurrentWeatherVO.DEFAULT_CITY_ID;
        }
        try {
            return mainJSON.getInt(KEY_ID);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse City Id exception:" + e.getMessage());
            return CurrentWeatherVO.DEFAULT_CITY_ID;
        }
    }

    @Override
    public String parseName(final String inputData) {
        final JSONObject mainJSON = getJSONFromRawData(inputData);
        if (mainJSON.length() == 0) {
            return CurrentWeatherVO.DEFAULT_CITY_NAME;
        }
        try {
            return mainJSON.getString(KEY_NAME);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse City Name exception:" + e.getMessage());
            return CurrentWeatherVO.DEFAULT_CITY_NAME;
        }
    }

    @Override
    public int parseCod(final String inputData) {
        final JSONObject mainJSON = getJSONFromRawData(inputData);
        if (mainJSON.length() == 0) {
            return CurrentWeatherVO.DEFAULT_COD;
        }
        try {
            return mainJSON.getInt(KEY_COD);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse weather Condition Code exception:" + e.getMessage());
            return CurrentWeatherVO.DEFAULT_COD;
        }
    }

    /**
     * This method converts raw data which comes from the server into the JSON object.
     *
     * @param rawData Raw data from the weather server.
     * @return Instance of the {@link org.json.JSONObject}.
     */
    protected JSONObject getJSONFromRawData(final String rawData) {
        if (rawData == null) {
            Log.w(CLASS_NAME, "Can not convert raw data to JSON, raw data is null");
            return new JSONObject();
        }
        if (rawData.isEmpty()) {
            Log.w(CLASS_NAME, "Can not convert raw data to JSON, raw data is empty");
            return new JSONObject();
        }
        try {
            return new JSONObject(rawData);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Can not convert raw data to JSON:" + e.getMessage());
            return new JSONObject();
        }
    }

    /**
     * This method allows extract JSON Object from the main JSON Object.
     *
     * @param inputData Raw input data.
     * @param key       Key which is used to find a JSON Object.
     * @return Found JSON Object or empty one.
     */
    private JSONObject getJSONObjectFromMainJSONObject(final String inputData, final String key) {
        final JSONObject mainJSON = getJSONFromRawData(inputData);
        JSONObject returnJSON = new JSONObject();
        if (key == null) {
            return returnJSON;
        }
        if (mainJSON.length() == 0) {
            return returnJSON;
        }
        if (!mainJSON.has(key)) {
            return returnJSON;
        }
        try {
            returnJSON = mainJSON.getJSONObject(key);
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "Parse " + key + " from main JSON exception:" + e.getMessage());
        }
        return returnJSON;
    }
}
