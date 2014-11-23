package com.yuriy.weatherapp.api;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

import android.content.Context;
import android.net.Uri;

import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.net.Downloader;

import java.io.IOException;

/**
 * {@link com.yuriy.weatherapp.api.APIServiceProvider} interface provides various methods
 * to operate with Weather Service API.<br>
 * http://openweathermap.org/api<br>
 * This interface can be extended as much as API allows.
 */
public interface APIServiceProvider {

    /**
     * Access current weather data for any location on Earth.
     *
     * @param downloader Implementation of the {@link com.yuriy.weatherapp.net.Downloader}.
     * @param uri        {@link android.net.Uri} of the request.
     * @return {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}.
     */
    public CurrentWeatherVO getCurrentWeatherReportByCity(final Downloader downloader,
                                                          final Uri uri);

    /**
     * Return a path to the downloaded Icon for the weather condition.
     *
     * @param context    Context of the callee
     * @param downloader Implementation of the {@link com.yuriy.weatherapp.net.Downloader}.
     * @param uri        {@link android.net.Uri} of the request.
     * @return Path to the downloaded Icon.
     */
    public String getCurrentWeatherConditionsIcon(final Context context,
                                                  final Downloader downloader,
                                                  final Uri uri) throws IOException;
}
