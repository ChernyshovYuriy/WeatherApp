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
public class WindVOTest extends TestCase {

    private static final double WIND = 9.56;
    private static final double WIND_OUT_OF_BOUNDS = -200.56;
    private static final double DEG = 89.43;

    private WindVO mWindVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mWindVO = WindVO.getInstance(WIND, DEG);
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mWindVO, notNullValue());
    }

    public void testCreateInstanceWithOutOfBoundsValuesResetThenToDefault() {
        mWindVO = WindVO.getInstance(WIND_OUT_OF_BOUNDS, DEG);

        assertThat("Instance should not be null", mWindVO, notNullValue());
        assertThat("Should be default wind speed: " + WindVO.DEFAULT_SPEED,
                mWindVO.getSpeed(), is(WindVO.DEFAULT_SPEED));
    }

    public void testCreateDefaultInstance() {
        mWindVO = WindVO.getDefaultInstance();

        assertThat("Default instance should not be null", mWindVO, notNullValue());
        assertThat("Default wind speed should be: " + WindVO.DEFAULT_SPEED,
                mWindVO.getSpeed(), is(WindVO.DEFAULT_SPEED));
        assertThat("Default wind direction should be: " + WindVO.DEFAULT_DEG,
                mWindVO.getDeg(), is(WindVO.DEFAULT_DEG));
    }

    public void testGettersShouldReturnCorrectValues() {
        assertThat("Wind speed should be same as original", mWindVO.getSpeed(), is(WIND));
        assertThat("Longitude should be same as original", mWindVO.getDeg(), is(DEG));
    }
}
