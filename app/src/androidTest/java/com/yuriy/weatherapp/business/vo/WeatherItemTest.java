package com.yuriy.weatherapp.business.vo;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */
public class WeatherItemTest extends TestCase {

    private static final int ID = 56;
    private static final String MAIN = "Rain";
    private static final String DESCRIPTION = "overcast clouds";
    private static final String ICON = "04n";

    private WeatherItem mWeatherItem;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mWeatherItem = WeatherItem.getInstance(ID, MAIN, DESCRIPTION, ICON);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mWeatherItem, notNullValue());
    }

    public void testCreateInstanceWithOutOfBoundsValuesResetThenToDefault() {
        mWeatherItem = WeatherItem.getInstance(-1, null, null, null);

        assertThat("Instance should not be null", mWeatherItem, notNullValue());
        assertThat("Should be default Id: " + WeatherItem.DEFAULT_CONDITION_ID,
                mWeatherItem.getId(), is(WeatherItem.DEFAULT_CONDITION_ID));
        assertThat("Should be default Main: " + WeatherItem.DEFAULT_MAIN,
                mWeatherItem.getMain(), is(WeatherItem.DEFAULT_MAIN));
        assertThat("Should be default Description: " + WeatherItem.DEFAULT_DESCRIPTION,
                mWeatherItem.getDescription(), is(WeatherItem.DEFAULT_DESCRIPTION));
        assertThat("Should be default Id: " + WeatherItem.DEFAULT_ICON,
                mWeatherItem.getIcon(), is(WeatherItem.DEFAULT_ICON));
    }

    public void testCreateDefaultInstance() {
        mWeatherItem = WeatherItem.getDefaultInstance();

        assertThat("Default instance should not be null", mWeatherItem, notNullValue());
        assertThat("Default condition id should be: " + WeatherItem.DEFAULT_CONDITION_ID,
                mWeatherItem.getId(), is(WeatherItem.DEFAULT_CONDITION_ID));
        assertThat("Default main id should be: " + WeatherItem.DEFAULT_MAIN,
                mWeatherItem.getMain(), is(WeatherItem.DEFAULT_MAIN));
        assertThat("Default description id should be: " + WeatherItem.DEFAULT_DESCRIPTION,
                mWeatherItem.getDescription(), is(WeatherItem.DEFAULT_DESCRIPTION));
        assertThat("Default icon id should be: " + WeatherItem.DEFAULT_ICON,
                mWeatherItem.getIcon(), is(WeatherItem.DEFAULT_ICON));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Id should be same as original", mWeatherItem.getId(), is(ID));
        assertThat("Main should be same as original", mWeatherItem.getMain(), is(MAIN));
        assertThat("Description should be same as original",
                mWeatherItem.getDescription(), is(DESCRIPTION));
        assertThat("Icon should be same as original", mWeatherItem.getIcon(), is(ICON));
    }
}
