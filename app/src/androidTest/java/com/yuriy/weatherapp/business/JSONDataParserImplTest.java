package com.yuriy.weatherapp.business;

import com.yuriy.weatherapp.ApplicationTest;
import com.yuriy.weatherapp.business.vo.CloudsVO;
import com.yuriy.weatherapp.business.vo.CoordVO;
import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.business.vo.MainVO;
import com.yuriy.weatherapp.business.vo.RainVO;
import com.yuriy.weatherapp.business.vo.SysVO;
import com.yuriy.weatherapp.business.vo.WeatherItem;
import com.yuriy.weatherapp.business.vo.WindVO;

import junit.framework.TestCase;

import org.json.JSONObject;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */
public class JSONDataParserImplTest extends TestCase {

    private DataParser mDataParser;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mDataParser = new JSONDataParserImpl();
    }

    public void testJSONFromRawDataShouldNotBeEmpty() {
        final JSONDataParserImpl parser = new JSONDataParserImpl();
        final JSONObject jsonObject = parser.getJSONFromRawData(ApplicationTest.RAW_RESPONSE);

        assertThat("JSON Object should not be null", jsonObject, notNullValue());
        assertTrue("JSON Object should not be empty", jsonObject.length() > 0);
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_COORD,
                jsonObject.has(JSONDataParserImpl.KEY_COORD), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_SYS,
                jsonObject.has(JSONDataParserImpl.KEY_SYS), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_WEATHER,
                jsonObject.has(JSONDataParserImpl.KEY_WEATHER), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_MAIN,
                jsonObject.has(JSONDataParserImpl.KEY_MAIN), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_WIND,
                jsonObject.has(JSONDataParserImpl.KEY_WIND), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_RAIN,
                jsonObject.has(JSONDataParserImpl.KEY_RAIN), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_CLOUDS,
                jsonObject.has(JSONDataParserImpl.KEY_CLOUDS), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_DT,
                jsonObject.has(JSONDataParserImpl.KEY_DT), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_ID,
                jsonObject.has(JSONDataParserImpl.KEY_ID), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_NAME,
                jsonObject.has(JSONDataParserImpl.KEY_NAME), is(true));
        assertThat("JSON Object should contains key:" + JSONDataParserImpl.KEY_COD,
                jsonObject.has(JSONDataParserImpl.KEY_COD), is(true));
    }

    public void testJSONFromNullRawDataShouldBeEmpty() {
        final JSONDataParserImpl parser = new JSONDataParserImpl();
        final JSONObject jsonObject = parser.getJSONFromRawData(null);

        assertThat("JSON Object should not be null", jsonObject, notNullValue());
        assertTrue("JSON Object should be empty", jsonObject.length() == 0);
    }

    public void testJSONFromEmptyRawDataShouldBeEmpty() {
        final JSONDataParserImpl parser = new JSONDataParserImpl();
        final JSONObject jsonObject = parser.getJSONFromRawData("");

        assertThat("JSON Object should not be null", jsonObject, notNullValue());
        assertTrue("JSON Object should be empty", jsonObject.length() == 0);
    }

    public void testParseCoordSuccess() {
        final CoordVO coordVO = mDataParser.parseCityCoordinates(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", coordVO, notNullValue());
        assertThat("Latitude should be same as original", coordVO.getLat(), is((double) 35));
        assertThat("Longitude should be same as original", coordVO.getLon(), is((double) 139));
    }

    public void testParseCoordFailButInstanceNotNull() {
        CoordVO coordVO = mDataParser.parseCityCoordinates(null);

        assertThat("Instance should not be null", coordVO, notNullValue());
        assertThat("Default longitude should be: " + CoordVO.DEFAULT_LATITUDE,
                coordVO.getLon(), is(CoordVO.DEFAULT_LATITUDE));
        assertThat("Default latitude should be: " + CoordVO.DEFAULT_LONGITUDE,
                coordVO.getLat(), is(CoordVO.DEFAULT_LATITUDE));

        coordVO = mDataParser.parseCityCoordinates("");

        assertThat("Instance should not be null", coordVO, notNullValue());
        assertThat("Default longitude should be: " + CoordVO.DEFAULT_LATITUDE,
                coordVO.getLon(), is(CoordVO.DEFAULT_LATITUDE));
        assertThat("Default latitude should be: " + CoordVO.DEFAULT_LONGITUDE,
                coordVO.getLat(), is(CoordVO.DEFAULT_LATITUDE));
    }

    public void testParseSysSuccess() {
        final SysVO sysVO = mDataParser.parseSysInfo(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", sysVO, notNullValue());
        assertThat("Country should be same as original", sysVO.getCountry(), is("JP"));
        assertThat("Sunrise time should be same as original",
                sysVO.getSunrise(), is((long)1369769524));
        assertThat("Sunset time should be same as original",
                sysVO.getSunset(), is((long)1369821049));
    }

    public void testParseSysFailButInstanceNotNull() {
        SysVO sysVO = mDataParser.parseSysInfo(null);

        assertThat("Instance should not be null", sysVO, notNullValue());
        assertThat("Default country should be: '" + SysVO.DEFAULT_COUNTRY + "'",
                sysVO.getCountry(), is(SysVO.DEFAULT_COUNTRY));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNRISE,
                sysVO.getSunrise(), is(SysVO.DEFAULT_SUNRISE));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNSET,
                sysVO.getSunset(), is(SysVO.DEFAULT_SUNSET));

        sysVO = mDataParser.parseSysInfo("");

        assertThat("Instance should not be null", sysVO, notNullValue());
        assertThat("Default country should be: '" + SysVO.DEFAULT_COUNTRY + "'",
                sysVO.getCountry(), is(SysVO.DEFAULT_COUNTRY));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNRISE,
                sysVO.getSunrise(), is(SysVO.DEFAULT_SUNRISE));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNSET,
                sysVO.getSunset(), is(SysVO.DEFAULT_SUNSET));
    }

    public void testParsWeatherItemsSuccess() {
        final List<WeatherItem> weatherItems = mDataParser.parseWeather(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", weatherItems, notNullValue());
        assertThat("Collection should be of size 1", weatherItems.size(), is(1));
        assertThat("Weather Id should be as original", weatherItems.get(0).getId(), is(804));
        assertThat("Weather main should be as original",
                weatherItems.get(0).getMain(), is("clouds"));
        assertThat("Weather description should be as original",
                weatherItems.get(0).getDescription(), is("overcast clouds"));
        assertThat("Weather icon should be as original",
                weatherItems.get(0).getIcon(), is("04n"));
    }

    public void testParsWeatherItemsNullRawDataShouldBeEmpty() {
        final List<WeatherItem> weatherItems = mDataParser.parseWeather(null);

        assertThat("Instance should not be null", weatherItems, notNullValue());
        assertThat("Collection should be of size 1", weatherItems.size(), is(0));
    }

    public void testParsWeatherItemsEmptyRawDataShouldBeEmpty() {
        final List<WeatherItem> weatherItems = mDataParser.parseWeather("");

        assertThat("Instance should not be null", weatherItems, notNullValue());
        assertThat("Collection should be of size 1", weatherItems.size(), is(0));
    }

    public void testParseMainSuccess() {
        final MainVO mainVO = mDataParser.parseMainInfo(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", mainVO, notNullValue());
        assertThat("Temperature should be same as original",
                mainVO.getTemperature(), is(289.5));
        assertThat("Humidity should be same as original",
                mainVO.getHumidity(), is((double) 89));
        assertThat("Pressure should be same as original",
                mainVO.getPressure(), is((double) 1013));
        assertThat("Max Temperature should be same as original",
                mainVO.getMaxTemperature(), is(292.04));
        assertThat("Min Temperature should be same as original",
                mainVO.getMinTemperature(), is(287.04));
    }

    public void testParseMainFailButInstanceNotNull() {
        MainVO mainVO = mDataParser.parseMainInfo(null);

        assertThat("Instance should not be null", mainVO, notNullValue());
        assertThat("Default Temperature should be: '" + MainVO.DEFAULT_TEMPERATURE + "'",
                mainVO.getTemperature(), is(MainVO.DEFAULT_TEMPERATURE));
        assertThat("Default Min Temperature should be: '" + MainVO.DEFAULT_MIN_TEMPERATURE + "'",
                mainVO.getMinTemperature(), is(MainVO.DEFAULT_MIN_TEMPERATURE));
        assertThat("Default Max Temperature should be: '" + MainVO.DEFAULT_MAX_TEMPERATURE + "'",
                mainVO.getMaxTemperature(), is(MainVO.DEFAULT_MAX_TEMPERATURE));
        assertThat("Default Humidity should be: '" + MainVO.DEFAULT_HUMIDITY + "'",
                mainVO.getHumidity(), is(MainVO.DEFAULT_HUMIDITY));
        assertThat("Default Pressure should be: '" + MainVO.DEFAULT_PRESSURE + "'",
                mainVO.getPressure(), is(MainVO.DEFAULT_PRESSURE));

        mainVO = mDataParser.parseMainInfo("");

        assertThat("Instance should not be null", mainVO, notNullValue());
        assertThat("Default Temperature should be: '" + MainVO.DEFAULT_TEMPERATURE + "'",
                mainVO.getTemperature(), is(MainVO.DEFAULT_TEMPERATURE));
        assertThat("Default Min Temperature should be: '" + MainVO.DEFAULT_MIN_TEMPERATURE + "'",
                mainVO.getMinTemperature(), is(MainVO.DEFAULT_MIN_TEMPERATURE));
        assertThat("Default Max Temperature should be: '" + MainVO.DEFAULT_MAX_TEMPERATURE + "'",
                mainVO.getMaxTemperature(), is(MainVO.DEFAULT_MAX_TEMPERATURE));
        assertThat("Default Humidity should be: '" + MainVO.DEFAULT_HUMIDITY + "'",
                mainVO.getHumidity(), is(MainVO.DEFAULT_HUMIDITY));
        assertThat("Default Pressure should be: '" + MainVO.DEFAULT_PRESSURE + "'",
                mainVO.getPressure(), is(MainVO.DEFAULT_PRESSURE));
    }

    public void testParseWindSuccess() {
        final WindVO windVO = mDataParser.parseWind(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", windVO, notNullValue());
        assertThat("Wind Speed should be same as original",
                windVO.getSpeed(), is(7.31));
        assertThat("Wind Direction should be same as original",
                windVO.getDeg(), is(187.002));
    }

    public void testParseWindFailButInstanceNotNull() {
        WindVO windVO = mDataParser.parseWind(null);

        assertThat("Instance should not be null", windVO, notNullValue());
        assertThat("Default Wind Speed should be: '" + WindVO.DEFAULT_SPEED + "'",
                windVO.getSpeed(), is(WindVO.DEFAULT_SPEED));
        assertThat("Default Wind Direction should be: '" + WindVO.DEFAULT_DEG + "'",
                windVO.getDeg(), is(WindVO.DEFAULT_DEG));

        windVO = mDataParser.parseWind("");

        assertThat("Instance should not be null", windVO, notNullValue());
        assertThat("Default Wind Speed should be: '" + WindVO.DEFAULT_SPEED + "'",
                windVO.getSpeed(), is(WindVO.DEFAULT_SPEED));
        assertThat("Default Wind Direction should be: '" + WindVO.DEFAULT_DEG + "'",
                windVO.getDeg(), is(WindVO.DEFAULT_DEG));
    }

    public void testParseRainSuccess() {
        final RainVO rainVO = mDataParser.parseRain(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", rainVO, notNullValue());
        assertThat("Precipitation should be same as original", rainVO.get3h(), is(0));
    }

    public void testParseRainFailButInstanceNotNull() {
        RainVO rainVO = mDataParser.parseRain(null);

        assertThat("Instance should not be null", rainVO, notNullValue());
        assertThat("Default Precipitation should be: '" + RainVO.DEFAULT_3H + "'",
                rainVO.get3h(), is(RainVO.DEFAULT_3H));

        rainVO = mDataParser.parseRain("");

        assertThat("Instance should not be null", rainVO, notNullValue());
        assertThat("Default Precipitation should be: '" + RainVO.DEFAULT_3H + "'",
                rainVO.get3h(), is(RainVO.DEFAULT_3H));
    }

    public void testParseCloudsSuccess() {
        final CloudsVO cloudsVO = mDataParser.parseClouds(ApplicationTest.RAW_RESPONSE);

        assertThat("Instance should not be null", cloudsVO, notNullValue());
        assertThat("Cloudiness should be same as original", cloudsVO.getAll(), is((double)92));
    }

    public void testParseCloudsFailButInstanceNotNull() {
        CloudsVO cloudsVO = mDataParser.parseClouds(null);

        assertThat("Instance should not be null", cloudsVO, notNullValue());
        assertThat("Default Cloudiness should be: '" + CloudsVO.DEFAULT_VALUE + "'",
                cloudsVO.getAll(), is(CloudsVO.DEFAULT_VALUE));

        cloudsVO = mDataParser.parseClouds("");

        assertThat("Instance should not be null", cloudsVO, notNullValue());
        assertThat("Default Cloudiness should be: '" + CloudsVO.DEFAULT_VALUE + "'",
                cloudsVO.getAll(), is(CloudsVO.DEFAULT_VALUE));
    }

    public void testParseDTSuccess() {
        final double dt = mDataParser.parseDt(ApplicationTest.RAW_RESPONSE);

        assertThat("Data receive time should be same as original", dt, is((double)1369824698));
    }

    public void testParseDTFailButValueDefault() {
        double dt = mDataParser.parseDt(null);

        assertThat("Default Data receive time should be: '" + CurrentWeatherVO.DEFAULT_DT + "'",
                dt, is(CurrentWeatherVO.DEFAULT_DT));

        dt = mDataParser.parseDt("");

        assertThat("Default Data receive time should be: '" + CurrentWeatherVO.DEFAULT_DT + "'",
                dt, is(CurrentWeatherVO.DEFAULT_DT));
    }

    public void testParseCityIdSuccess() {
        final int cityId = mDataParser.parseId(ApplicationTest.RAW_RESPONSE);

        assertThat("City Id should be same as original", cityId, is(1851632));
    }

    public void testParseCityIdFailButValueDefault() {
        int cityId = mDataParser.parseId(null);

        assertThat("City Id should be: '" + CurrentWeatherVO.DEFAULT_CITY_ID + "'",
                cityId, is(CurrentWeatherVO.DEFAULT_CITY_ID));

        cityId = mDataParser.parseId("");

        assertThat("City Id should be: '" + CurrentWeatherVO.DEFAULT_CITY_ID + "'",
                cityId, is(CurrentWeatherVO.DEFAULT_CITY_ID));
    }

    public void testParseCityNameSuccess() {
        final String cityName = mDataParser.parseName(ApplicationTest.RAW_RESPONSE);

        assertThat("City Name should be same as original", cityName, is("Shuzenji"));
    }

    public void testParseCityNameFailButValueDefault() {
        String cityName = mDataParser.parseName(null);

        assertThat("City Name should be: '" + CurrentWeatherVO.DEFAULT_CITY_NAME + "'",
                cityName, is(CurrentWeatherVO.DEFAULT_CITY_NAME));

        cityName = mDataParser.parseName("");

        assertThat("City Name should be: '" + CurrentWeatherVO.DEFAULT_CITY_NAME + "'",
                cityName, is(CurrentWeatherVO.DEFAULT_CITY_NAME));
    }

    public void testParseCodeSuccess() {
        final int cod = mDataParser.parseCod(ApplicationTest.RAW_RESPONSE);

        assertThat("Weather Condition Code should be same as original", cod, is(200));
    }

    public void testParseCodFailButValueDefault() {
        int cod = mDataParser.parseCod(null);

        assertThat("Weather Condition Code should be: '" + CurrentWeatherVO.DEFAULT_COD + "'",
                cod, is(CurrentWeatherVO.DEFAULT_COD));

        cod = mDataParser.parseCod("");

        assertThat("Weather Condition Code should be: '" + CurrentWeatherVO.DEFAULT_COD + "'",
                cod, is(CurrentWeatherVO.DEFAULT_COD));
    }
}
