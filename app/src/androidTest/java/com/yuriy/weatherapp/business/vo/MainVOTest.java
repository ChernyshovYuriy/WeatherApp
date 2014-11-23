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
public class MainVOTest extends TestCase {

    private static final double TEMPERATURE = 289.5;
    private static final double HUMIDITY = 88.6;
    private static final double PRESSURE = 1013;
    private static final double MAX_TEMPERATURE = 292.04;
    private static final double MIN_TEMPERATURE = 287.04;

    private MainVO mMainVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mMainVO = MainVO.getInstance(
                TEMPERATURE, HUMIDITY, PRESSURE, MIN_TEMPERATURE, MAX_TEMPERATURE
        );
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mMainVO, notNullValue());
    }

    public void testCreateInstanceWithOutOfBoundsValuesResetThenToDefault() {
        mMainVO = MainVO.getInstance(TEMPERATURE, -1, -1, MIN_TEMPERATURE, MAX_TEMPERATURE);

        assertThat("Instance should not be null", mMainVO, notNullValue());
        assertThat("Should be default Humidity: " + MainVO.DEFAULT_HUMIDITY,
                mMainVO.getHumidity(), is(MainVO.DEFAULT_HUMIDITY));
        assertThat("Should be default Pressure: " + MainVO.DEFAULT_PRESSURE,
                mMainVO.getPressure(), is(MainVO.DEFAULT_PRESSURE));
    }

    public void testCreateDefaultInstance() {
        mMainVO = MainVO.getDefaultInstance();

        assertThat("Default instance should not be null", mMainVO, notNullValue());
        assertThat("Default Temperature should be: '" + MainVO.DEFAULT_TEMPERATURE + "'",
                mMainVO.getTemperature(), is(MainVO.DEFAULT_TEMPERATURE));
        assertThat("Default Humidity should be: " + MainVO.DEFAULT_HUMIDITY,
                mMainVO.getHumidity(), is(MainVO.DEFAULT_HUMIDITY));
        assertThat("Default Pressure should be: " + MainVO.DEFAULT_PRESSURE,
                mMainVO.getPressure(), is(MainVO.DEFAULT_PRESSURE));
        assertThat("Default Max Temperature should be: " + MainVO.DEFAULT_MAX_TEMPERATURE,
                mMainVO.getMaxTemperature(), is(MainVO.DEFAULT_MAX_TEMPERATURE));
        assertThat("Default Min Temperature should be: " + MainVO.DEFAULT_MIN_TEMPERATURE,
                mMainVO.getMinTemperature(), is(MainVO.DEFAULT_MIN_TEMPERATURE));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Temperature should be same as original",
                mMainVO.getTemperature(), is(TEMPERATURE));
        assertThat("Humidity should be same as original", mMainVO.getHumidity(), is(HUMIDITY));
        assertThat("Pressure should be same as original", mMainVO.getPressure(), is(PRESSURE));
        assertThat("Max Temperature should be same as original",
                mMainVO.getMaxTemperature(), is(MAX_TEMPERATURE));
        assertThat("Min Temperature should be same as original",
                mMainVO.getMinTemperature(), is(MIN_TEMPERATURE));
    }
}
