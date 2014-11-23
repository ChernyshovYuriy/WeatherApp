package com.yuriy.weatherapp.net;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

/**
 * {@link UrlRequestParameters} is a helper class which holds
 * necessary parameters which are in use when form UR Request.
 */
public class UrlRequestParameters {

    /**
     * This field contains abbreviation of the country where city is located.
     */
    private String mCountryAbbreviation;

    /**
     * Name of the city.
     */
    private String mCity;

    /**
     * Request key for case when data requested by the city name.
     */
    private String mCityNameKey;

    /**
     * @return Country abbreviation where particular city is located.
     */
    public String getCountryAbbreviation() {
        return mCountryAbbreviation;
    }

    /**
     * Set country abbreviation where particular city is located.
     *
     * @param value Country abbreviation.
     */
    public void setCountryAbbreviation(final String value) {
        mCountryAbbreviation = value;
    }

    /**
     * @return Name of the city.
     */
    public String getCity() {
        return mCity;
    }

    /**
     * Name of the city.
     *
     * @param value Name of the city.
     */
    public void setCity(String value) {
        mCity = value;
    }

    /**
     * @return Request key for case when data requested by the city name.
     */
    public String getCityNameKey() {
        return mCityNameKey;
    }

    /**
     * Set request key for case when data requested by the city name.
     *
     * @param value Request key.
     */
    public void setCityNameKey(String value) {
        mCityNameKey = value;
    }
}
