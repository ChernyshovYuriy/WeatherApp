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
public class CoordVOTest extends TestCase {

    private static final double LAT = -0.56;
    private static final double LAT_OUT_OF_BOUNDS_MIN = -200.56;
    private static final double LAT_OUT_OF_BOUNDS_MAX = 200.56;
    private static final double LON = 89.43;
    private static final double LON_OUT_OF_BOUNDS_MIN = -300.43;
    private static final double LON_OUT_OF_BOUNDS_MAX = 300.43;

    private CoordVO mCoordVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mCoordVO = CoordVO.getInstance(LAT, LON);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mCoordVO, notNullValue());
    }

    public void testCreateInstanceWithMaxOutOfBoundsValuesResetThenToDefault() {
        mCoordVO = CoordVO.getInstance(LAT_OUT_OF_BOUNDS_MAX, LON_OUT_OF_BOUNDS_MAX);

        assertThat("Instance should not be null", mCoordVO, notNullValue());
        assertThat("Should be default longitude: " + CoordVO.DEFAULT_LONGITUDE,
                mCoordVO.getLon(), is(CoordVO.DEFAULT_LONGITUDE));
        assertThat("Should be default latitude: " + CoordVO.DEFAULT_LATITUDE,
                mCoordVO.getLat(), is(CoordVO.DEFAULT_LATITUDE));
    }

    public void testCreateInstanceWithMinOutOfBoundsValuesResetThenToDefault() {
        mCoordVO = CoordVO.getInstance(LAT_OUT_OF_BOUNDS_MIN, LON_OUT_OF_BOUNDS_MIN);

        assertThat("Instance should not be null", mCoordVO, notNullValue());
        assertThat("Should be default longitude: " + CoordVO.DEFAULT_LONGITUDE,
                mCoordVO.getLon(), is(CoordVO.DEFAULT_LONGITUDE));
        assertThat("Should be default latitude: " + CoordVO.DEFAULT_LATITUDE,
                mCoordVO.getLat(), is(CoordVO.DEFAULT_LATITUDE));
    }

    public void testCreateDefaultInstance() {
        mCoordVO = CoordVO.getDefaultInstance();

        assertThat("Default instance should not be null", mCoordVO, notNullValue());
        assertThat("Default longitude should be: " + CoordVO.DEFAULT_LONGITUDE,
                mCoordVO.getLon(), is(CoordVO.DEFAULT_LONGITUDE));
        assertThat("Default latitude should be: " + CoordVO.DEFAULT_LATITUDE,
                mCoordVO.getLat(), is(CoordVO.DEFAULT_LATITUDE));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Latitude should be same as original", mCoordVO.getLat(), is(LAT));
        assertThat("Longitude should be same as original", mCoordVO.getLon(), is(LON));
    }
}
