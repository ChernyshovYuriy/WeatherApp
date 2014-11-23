package com.yuriy.weatherapp.api;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.yuriy.weatherapp.business.DataParser;
import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.business.vo.WeatherItem;
import com.yuriy.weatherapp.net.Downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/22/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

/**
 * {@link com.yuriy.weatherapp.api.APIServiceProviderImpl} implements
 * {@link com.yuriy.weatherapp.api.APIServiceProvider} interface.
 */
public class APIServiceProviderImpl implements APIServiceProvider {

    private static final String CLASS_NAME = APIServiceProviderImpl.class.getSimpleName();

    /**
     * Implementation of the {@link com.yuriy.weatherapp.business.DataParser} allows to
     * parse raw response of the weather data into different formats.
     */
    private DataParser mDataParser;

    /**
     * Constructor.
     *
     * @param dataParser Implementation of the {@link com.yuriy.weatherapp.business.DataParser}.
     */
    public APIServiceProviderImpl(final DataParser dataParser) {
        if (dataParser == null) {
            Log.w(CLASS_NAME, "Constructor -> data parser is null");
        }
        mDataParser = dataParser;
    }

    @Override
    public String getCurrentWeatherConditionsIcon(final Context context,
                                                  final Downloader downloader, final Uri uri)
            throws IOException {

        // Download response from the server
        final byte[] responseBytes = getResponseBytes(downloader, uri);

        // Ignore null response
        if (responseBytes == null) {
            Log.w(CLASS_NAME, "Can not parse weather data, response byes are null");
            return "";
        }

        // Save icons bytes into the file
        final File icon = getTemporaryFile(context);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(icon.getPath());
            fileOutputStream.write(responseBytes);
        } catch (IOException e) {
            Log.e(CLASS_NAME, "Save weather condition icon exception:" + e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        return icon.getAbsolutePath();
    }

    @Override
    public CurrentWeatherVO getCurrentWeatherReportByCity(final Downloader downloader,
                                                          final Uri uri) {
        // Initialize return object.
        final CurrentWeatherVO weatherVO = CurrentWeatherVO.getInstance();

        // Download response from the server
        final byte[] responseBytes = getResponseBytes(downloader, uri);

        // Ignore null response
        if (responseBytes == null) {
            Log.w(CLASS_NAME, "Can not parse weather data, response byes are null");
            return weatherVO;
        }

        final String response = new String(responseBytes);
        Log.i(CLASS_NAME, "Weather Response:\n" + response);

        // Ignore empty response
        if (response.isEmpty()) {
            Log.w(CLASS_NAME, "Can not parse weather data, response is empty");
            return weatherVO;
        }

        if (mDataParser == null) {
            Log.w(CLASS_NAME, "Can not parse weather data, parser is null");
            return weatherVO;
        }

        // Extract City coordinates
        weatherVO.setCoordVO(mDataParser.parseCityCoordinates(response));

        // Extract Sys Information
        weatherVO.setSysVO(mDataParser.parseSysInfo(response));

        // Extract weather conditions
        List<WeatherItem> weatherItems = mDataParser.parseWeather(response);
        if (weatherItems != null) {
            for (WeatherItem weatherItem : weatherItems) {
                weatherVO.addWeatherItem(weatherItem);
            }
        }

        // Extract main information about weather
        weatherVO.setMainVO(mDataParser.parseMainInfo(response));

        // Extract Wind information
        weatherVO.setWindVO(mDataParser.parseWind(response));

        // Extract Rain information
        weatherVO.setRainVO(mDataParser.parseRain(response));

        // Extract Clouds information
        weatherVO.setCloudsVO(mDataParser.parseClouds(response));

        // Extract Data receiving time
        weatherVO.setDt(mDataParser.parseDt(response));

        // Extract City Id
        weatherVO.setCityId(mDataParser.parseId(response));

        // Extract City Name
        weatherVO.setCityName(mDataParser.parseName(response));

        // Extract Weather condition code
        weatherVO.setCod(mDataParser.parseCod(response));

        return weatherVO;
    }

    /**
     * Get response from the server.
     *
     * @param downloader Implementation of the {@link com.yuriy.weatherapp.net.Downloader}
     * @param uri        Provided Uri
     * @return Downloaded data as bytes array.
     */
    private byte[] getResponseBytes(final Downloader downloader, final Uri uri) {
        if (downloader == null) {
            Log.w(CLASS_NAME, "getResponseBytes -> downloader is null");
            return null;
        }
        if (uri == null) {
            Log.w(CLASS_NAME, "getResponseBytes -> uri is null");
            return null;
        }

        // Download response from the server
        return downloader.downloadDataFromUri(uri);
    }

    /**
     * Create a file to store the result of a download.
     *
     * @param context   Context of the callee
     * @return Temporary File.
     * @throws java.io.IOException
     */
    private File getTemporaryFile(final Context context) throws IOException {
        return context.getFileStreamPath(String.valueOf(System.currentTimeMillis()));
    }
}
