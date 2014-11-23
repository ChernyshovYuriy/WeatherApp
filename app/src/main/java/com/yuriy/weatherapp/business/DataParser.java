package com.yuriy.weatherapp.business;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import com.yuriy.weatherapp.business.vo.CloudsVO;
import com.yuriy.weatherapp.business.vo.CoordVO;
import com.yuriy.weatherapp.business.vo.MainVO;
import com.yuriy.weatherapp.business.vo.RainVO;
import com.yuriy.weatherapp.business.vo.SysVO;
import com.yuriy.weatherapp.business.vo.WeatherItem;
import com.yuriy.weatherapp.business.vo.WindVO;

import java.util.List;

/**
 * {@link com.yuriy.weatherapp.business.DataParser} interface provides common methods
 * for the weather data parsing. Different implementation can parse raw string data into JSON,
 * XML or any other format.
 */
public interface DataParser {

    /**
     * This method allows to parse city coordinates.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return City coordinates as {@link com.yuriy.weatherapp.business.vo.CoordVO}.
     */
    public CoordVO parseCityCoordinates(final String inputData);

    /**
     * This method allows to parse sys information such as Country, Sunrise and Sunset time..
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Sys information.
     */
    public SysVO parseSysInfo(final String inputData);

    /**
     * This method allows to parse weather data for the concrete city.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Collection of the weather data.
     */
    public List<WeatherItem> parseWeather(final String inputData);

    /**
     * This method allows to parse main information such as Temperature, Pressure, etc ...
     * about weather in the city.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Main information about the weather.
     */
    public MainVO parseMainInfo(final String inputData);

    /**
     * This method allows to parse information about Wind.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Wind information.
     */
    public WindVO parseWind(final String inputData);

    /**
     * This method allows to parse Rain information.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Rain information.
     */
    public RainVO parseRain(final String inputData);

    /**
     * This method allows to parse Clouds information.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Clouds information.
     */
    public CloudsVO parseClouds(final String inputData);

    /**
     * This method allows to parse Data receiving time, unix time, GMT.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Data receiving time.
     */
    public double parseDt(final String inputData);

    /**
     * This method allows to parse City identification.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return City identification.
     */
    public int parseId(final String inputData);

    /**
     * This method allows to parse City name.
     *
     * @param inputData Raw data which is received from the weather service.
     * @return City name.
     */
    public String parseName(final String inputData);

    /**
     * This method allows to parse Code for the weather condition.<br>
     * http://openweathermap.org/weather-conditions
     *
     * @param inputData Raw data which is received from the weather service.
     * @return Weather condition code.
     */
    public int parseCod(final String inputData);
}
