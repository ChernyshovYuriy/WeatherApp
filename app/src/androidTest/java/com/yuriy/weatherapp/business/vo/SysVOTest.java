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
public class SysVOTest extends TestCase {

    private static final long SUNRISE = 1369769524;
    private static final long SUNSET = 1369821049;
    private static final String COUNTRY_ABBREVIATION = "JP";

    private SysVO mSysVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mSysVO = SysVO.getInstance(COUNTRY_ABBREVIATION, SUNRISE, SUNSET);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mSysVO, notNullValue());
    }

    public void testCreateInstanceWithOutOfBoundsValuesResetThenToDefault() {
        mSysVO = SysVO.getInstance(null, 0, 0);

        assertThat("Instance should not be null", mSysVO, notNullValue());
        assertThat("Should be default country: " + SysVO.DEFAULT_COUNTRY,
                mSysVO.getCountry(), is(SysVO.DEFAULT_COUNTRY));
        assertThat("Should be default sunrise: " + SysVO.DEFAULT_SUNRISE,
                mSysVO.getSunrise(), is(SysVO.DEFAULT_SUNRISE));
        assertThat("Should be default sunset: " + SysVO.DEFAULT_SUNSET,
                mSysVO.getSunset(), is(SysVO.DEFAULT_SUNSET));
    }

    public void testCreateDefaultInstance() {
        mSysVO = SysVO.getDefaultInstance();

        assertThat("Default instance should not be null", mSysVO, notNullValue());
        assertThat("Default country should be: '" + SysVO.DEFAULT_COUNTRY + "'",
                mSysVO.getCountry(), is(SysVO.DEFAULT_COUNTRY));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNRISE,
                mSysVO.getSunrise(), is(SysVO.DEFAULT_SUNRISE));
        assertThat("Default sunrise should be: " + SysVO.DEFAULT_SUNSET,
                mSysVO.getSunset(), is(SysVO.DEFAULT_SUNSET));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Country abbreviation should be same as original",
                mSysVO.getCountry(), is(COUNTRY_ABBREVIATION));
        assertThat("Sunrise time should be same as original", mSysVO.getSunrise(), is(SUNRISE));
        assertThat("sunset should be same as original", mSysVO.getSunset(), is(SUNSET));
    }
}
