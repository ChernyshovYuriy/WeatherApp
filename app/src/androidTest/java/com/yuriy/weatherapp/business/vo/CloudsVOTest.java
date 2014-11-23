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
public class CloudsVOTest extends TestCase {

    private static final double ALL = 55.6;
    private static final double ALL_OUT_OF_BOUNDS_MIN = -200.56;
    private static final double ALL_OUT_OF_BOUNDS_MAX = 200.56;

    private CloudsVO mCloudsVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mCloudsVO = CloudsVO.getInstance(ALL);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mCloudsVO, notNullValue());
    }

    public void testCreateInstanceWithMaxOutOfBoundsValuesResetThenToDefault() {
        mCloudsVO = CloudsVO.getInstance(ALL_OUT_OF_BOUNDS_MAX);

        assertThat("Instance should not be null", mCloudsVO, notNullValue());
        assertThat("Should be default Cloudiness: " + CloudsVO.DEFAULT_VALUE,
                mCloudsVO.getAll(), is(CloudsVO.DEFAULT_VALUE));
    }

    public void testCreateInstanceWithMinOutOfBoundsValuesResetThenToDefault() {
        mCloudsVO = CloudsVO.getInstance(ALL_OUT_OF_BOUNDS_MIN);

        assertThat("Instance should not be null", mCloudsVO, notNullValue());
        assertThat("Should be default Cloudiness: " + CloudsVO.DEFAULT_VALUE,
                mCloudsVO.getAll(), is(CloudsVO.DEFAULT_VALUE));
    }

    public void testCreateDefaultInstance() {
        mCloudsVO = CloudsVO.getDefaultInstance();

        assertThat("Default instance should not be null", mCloudsVO, notNullValue());
        assertThat("Default Cloudiness should be: " + CloudsVO.DEFAULT_VALUE,
                mCloudsVO.getAll(), is(CloudsVO.DEFAULT_VALUE));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Cloudiness should be same as original", mCloudsVO.getAll(), is(ALL));
    }
}
