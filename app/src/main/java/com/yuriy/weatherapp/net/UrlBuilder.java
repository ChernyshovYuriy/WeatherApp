package com.yuriy.weatherapp.net;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import android.util.Log;

/**
 * {@link UrlBuilder} is a helper class which can build different types of
 * URLs for the {@link com.yuriy.weatherapp.service.WeatherService}.
 */
public class UrlBuilder {

    private static final String CLASS_NAME = UrlBuilder.class.getSimpleName();

    /**
     * Weather URL.
     */
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    /**
     * Base URL for the Weather Condition Icon.
     */
    private static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/";

    private static final String PARAMS_DELIMITER_SYMBOL = "?";
    private static final String EQUAL_SYMBOL = "=";
    private static final String COMA_SYMBOL = ",";

    /**
     * Method to create a URL for the web weather service for the provided city.
     *
     * @param parameters Instance of the
     *                   {@link UrlRequestParameters}
     * @return URL for the weather service.
     */
    public static String getWeatherUrlByCityName(final UrlRequestParameters parameters) {
        final StringBuilder builder = new StringBuilder(WEATHER_URL);
        if (parameters == null) {
            throw new NullPointerException(CLASS_NAME
                    + " getWeatherUrlByCityName parameters are null");
        }
        if (parameters.getCity() == null) {
            return builder.toString();
        }
        if (parameters.getCity().isEmpty()) {
            return builder.toString();
        }
        builder.append(PARAMS_DELIMITER_SYMBOL);
        builder.append(parameters.getCityNameKey());
        builder.append(EQUAL_SYMBOL);
        builder.append(parameters.getCity());
        builder.append(COMA_SYMBOL);
        builder.append(parameters.getCountryAbbreviation());
        return builder.toString();
    }

    /**
     * Method to create a URL for the web weather service for the provided city.
     *
     * @param iconId Icon Identifier
     * @return URL for the weather icon.
     */
    public static String getWeatherIconByCode(final String iconId) {
        if (iconId == null) {
            Log.w(CLASS_NAME, "Create weather icon url -> iconId is null");
            return WEATHER_ICON_URL;
        }
        return WEATHER_ICON_URL + iconId + ".png";
    }

    // TODO : Could be more factory methods for the URLs of the request API
}
