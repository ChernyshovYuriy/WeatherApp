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
public class CurrentWeatherVOTest extends TestCase {

    private CurrentWeatherVO mWeatherVO;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mWeatherVO = CurrentWeatherVO.getInstance();
    }

    public void testCreateInstance() {
        assertThat("Instance should not be null", mWeatherVO, notNullValue());
    }

    /**
     * Coord
     */

    public void testCoordGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getCoordVO(), notNullValue());
    }

    public void testCoordSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setCoordVO(null);

        assertThat("Instance should not be null", mWeatherVO.getCoordVO(), notNullValue());
    }

    public void testCoordSetterShouldReturnCorrectValue() {
        final CoordVO coordVO = CoordVO.getDefaultInstance();
        mWeatherVO.setCoordVO(coordVO);

        assertThat("Instance should be same", mWeatherVO.getCoordVO(), is(coordVO));
        assertThat("Latitude should be same",
                mWeatherVO.getCoordVO().getLat(), is(coordVO.getLat()));
        assertThat("Longitude should be same",
                mWeatherVO.getCoordVO().getLon(), is(coordVO.getLon()));
    }

    /**
     * Sys information
     */

    public void testSysGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getSysVO(), notNullValue());
    }

    public void testSysSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setSysVO(null);

        assertThat("Instance should not be null", mWeatherVO.getSysVO(), notNullValue());
    }

    public void testSysSetterShouldReturnCorrectValue() {
        final SysVO sysVO = SysVO.getDefaultInstance();
        mWeatherVO.setSysVO(sysVO);

        assertThat("Instance should be same", mWeatherVO.getSysVO(), is(sysVO));
        assertThat("Country should be same",
                mWeatherVO.getSysVO().getCountry(), is(sysVO.getCountry()));
        assertThat("Sunrise should be same",
                mWeatherVO.getSysVO().getSunrise(), is(sysVO.getSunrise()));
        assertThat("Sunset should be same",
                mWeatherVO.getSysVO().getSunset(), is(sysVO.getSunset()));
    }

    /**
     * Weather
     */

    public void testWeatherGetSizeShouldReturnZeroDefault() {
        assertThat("Size of the weather items should be zero",
                mWeatherVO.getWeatherItemsSize(), is(0));
    }

    public void testWeatherGetInvalidItemShouldReturnDefault() {
        assertThat("Weather item should have default description",
                mWeatherVO.getWeatherItemAt(0).getDescription(), is(WeatherItem.DEFAULT_DESCRIPTION));
        assertThat("Weather item should have default description",
                mWeatherVO.getWeatherItemAt(0).getIcon(), is(WeatherItem.DEFAULT_ICON));
        assertThat("Weather item should have default description",
                mWeatherVO.getWeatherItemAt(0).getMain(), is(WeatherItem.DEFAULT_MAIN));
        assertThat("Weather item should have default description",
                mWeatherVO.getWeatherItemAt(0).getId(), is(WeatherItem.DEFAULT_CONDITION_ID));
    }

    public void testWeatherSetInvalidItemShouldNotAddItem() {
        mWeatherVO.addWeatherItem(null);

        assertThat("Size of the collection should be same",
                mWeatherVO.getWeatherItemsSize(), is(0));
    }

    public void testWeatherSetValidItemShouldAddItem() {
        final int id = 963;
        final String main = "Main";
        final String description = "Description";
        final String icon = "Icon";
        final WeatherItem item = WeatherItem.getInstance(id, main, description, icon);
        mWeatherVO.addWeatherItem(item);

        assertThat(mWeatherVO.getWeatherItemsSize(), is(1));
        assertThat(mWeatherVO.getWeatherItemAt(0), is(item));
    }

    /**
     * Main
     */

    public void testMainGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getMainVO(), notNullValue());
    }

    public void testMainSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setMainVO(null);

        assertThat("Instance should not be null", mWeatherVO.getMainVO(), notNullValue());
    }

    public void testMainSetterShouldReturnCorrectValue() {
        final MainVO mainVO = MainVO.getDefaultInstance();
        mWeatherVO.setMainVO(mainVO);

        assertThat("Instance should be same", mWeatherVO.getMainVO(), is(mainVO));
        assertThat("Temperature should be same",
                mWeatherVO.getMainVO().getTemperature(), is(mainVO.getTemperature()));
        assertThat("Max Temperature should be same",
                mWeatherVO.getMainVO().getMaxTemperature(), is(mainVO.getMaxTemperature()));
        assertThat("Min Temperature should be same",
                mWeatherVO.getMainVO().getMinTemperature(), is(mainVO.getMinTemperature()));
        assertThat("Humidity should be same",
                mWeatherVO.getMainVO().getHumidity(), is(mainVO.getHumidity()));
        assertThat("Pressure should be same",
                mWeatherVO.getMainVO().getPressure(), is(mainVO.getPressure()));
    }

    /**
     * Wind
     */

    public void testWindGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getWindVO(), notNullValue());
    }

    public void testWindSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setWindVO(null);

        assertThat("Instance should not be null", mWeatherVO.getWindVO(), notNullValue());
    }

    public void testWindSetterShouldReturnCorrectValue() {
        final WindVO windVO = WindVO.getDefaultInstance();
        mWeatherVO.setWindVO(windVO);

        assertThat("Instance should be same", mWeatherVO.getWindVO(), is(windVO));
        assertThat("Wind Speed should be same",
                mWeatherVO.getWindVO().getSpeed(), is(windVO.getSpeed()));
        assertThat("Wind Direction should be same",
                mWeatherVO.getWindVO().getDeg(), is(windVO.getDeg()));
    }

    /**
     * Rain
     */

    public void testRainGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getRainVO(), notNullValue());
    }

    public void testRainSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setRainVO(null);

        assertThat("Instance should not be null", mWeatherVO.getRainVO(), notNullValue());
    }

    public void testRainSetterShouldReturnCorrectValue() {
        final RainVO rainVO = RainVO.getDefaultInstance();
        mWeatherVO.setRainVO(rainVO);

        assertThat("Instance should be same", mWeatherVO.getRainVO(), is(rainVO));
        assertThat("Precipitation should be same",
                mWeatherVO.getRainVO().get3h(), is(rainVO.get3h()));
    }

    /**
     * Cloudiness
     */

    public void testCloudinessGetterShouldNotReturnNullByDefault() {
        assertThat("Instance should not be null", mWeatherVO.getCloudsVO(), notNullValue());
    }

    public void testCloudinessSetterWithNullShouldNotReturnNullByDefault() {
        mWeatherVO.setCloudsVO(null);

        assertThat("Instance should not be null", mWeatherVO.getCloudsVO(), notNullValue());
    }

    public void testCloudsSetterShouldReturnCorrectValue() {
        final CloudsVO cloudsVO = CloudsVO.getDefaultInstance();
        mWeatherVO.setCloudsVO(cloudsVO);

        assertThat("Instance should be same", mWeatherVO.getCloudsVO(), is(cloudsVO));
        assertThat("Cloudiness should be same",
                mWeatherVO.getCloudsVO().getAll(), is(cloudsVO.getAll()));
    }

    /**
     * Data receiving time
     */

    public void testDTGetterShouldReturnDefaultValue() {
        assertThat("Initial value should be default",
                mWeatherVO.getDt(), is(CurrentWeatherVO.DEFAULT_DT));
    }

    public void testDTSetterShouldReturnCorrectValue() {
        final double dt = 159753;
        mWeatherVO.setDt(dt);

        assertThat("Data time should be same", mWeatherVO.getDt(), is(dt));
    }

    public void testDTSetterOfMinBoundsValueShouldReturnDefaultValue() {
        final double dt = -159753;
        mWeatherVO.setDt(dt);

        assertThat("Data time should be default", mWeatherVO.getDt(), is(CurrentWeatherVO.DEFAULT_DT));
    }

    /**
     * City Identification
     */

    public void testCityIdGetterShouldReturnDefaultValue() {
        assertThat("Initial value should be default",
                mWeatherVO.getCityId(), is(CurrentWeatherVO.DEFAULT_CITY_ID));
    }

    public void testCityIdSetterShouldReturnCorrectValue() {
        final int cityId = 789654;
        mWeatherVO.setCityId(cityId);

        assertThat("city Id should be same", mWeatherVO.getCityId(), is(cityId));
    }

    public void testCityIdSetterOfMinBoundsValueShouldReturnDefaultValue() {
        final int cityId = -36987;
        mWeatherVO.setCityId(cityId);

        assertThat("City Id should be default",
                mWeatherVO.getCityId(), is(CurrentWeatherVO.DEFAULT_CITY_ID));
    }

    /**
     * City Name
     */

    public void testCityNameGetterShouldReturnDefaultValue() {
        assertThat("Initial value should be default",
                mWeatherVO.getCityName(), is(CurrentWeatherVO.DEFAULT_CITY_NAME));
    }

    public void testCityNameSetterShouldReturnCorrectValue() {
        final String cityName = "Name";
        mWeatherVO.setCityName(cityName);

        assertThat("City Name should be same", mWeatherVO.getCityName(), is(cityName));
    }

    public void testCityNameSetterOfMinBoundsValueShouldReturnDefaultValue() {
        final String cityName = null;
        mWeatherVO.setCityName(cityName);

        assertThat("City Name should be default",
                mWeatherVO.getCityName(), is(CurrentWeatherVO.DEFAULT_CITY_NAME));
    }

    /**
     * Weather condition cod
     */

    public void testCodGetterShouldReturnDefaultValue() {
        assertThat("Initial value should be default",
                mWeatherVO.getCod(), is(CurrentWeatherVO.DEFAULT_COD));
    }

    public void testCodSetterShouldReturnCorrectValue() {
        final int cod = 500;
        mWeatherVO.setCod(cod);

        assertThat("Cod should be same", mWeatherVO.getCod(), is(cod));
    }

    public void testCodSetterOfMinBoundsValueShouldReturnDefaultValue() {
        final int cod = 0;
        mWeatherVO.setCod(cod);

        assertThat("City Name should be default",
                mWeatherVO.getCod(), is(CurrentWeatherVO.DEFAULT_COD));
    }
}
