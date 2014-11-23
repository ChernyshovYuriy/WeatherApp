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
public class RainVOTest extends TestCase {

    private static final int _3H = 56;
    private static final int _3H_BOUND = -56;

    private RainVO mRainVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mRainVO = RainVO.getInstance(_3H);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mRainVO, notNullValue());
    }

    public void testCreateInstanceWithOutOfBoundsValuesResetThenToDefault() {
        mRainVO = RainVO.getInstance(_3H_BOUND);

        assertThat("Instance should not be null", mRainVO, notNullValue());
        assertThat("Should be default Precipitation: " + RainVO.DEFAULT_3H,
                mRainVO.get3h(), is(RainVO.DEFAULT_3H));
    }

    public void testCreateDefaultInstance() {
        mRainVO = RainVO.getDefaultInstance();

        assertThat("Default instance should not be null", mRainVO, notNullValue());
        assertThat("Default Precipitation should be: " + RainVO.DEFAULT_3H,
                mRainVO.get3h(), is(RainVO.DEFAULT_3H));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Precipitation should be same as original", mRainVO.get3h(), is(_3H));
    }
}
