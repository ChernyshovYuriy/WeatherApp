package com.yuriy.weatherapp.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.yuriy.weatherapp.api.APIServiceProvider;
import com.yuriy.weatherapp.api.APIServiceProviderImpl;
import com.yuriy.weatherapp.business.AppPreferencesManager;
import com.yuriy.weatherapp.business.DataParser;
import com.yuriy.weatherapp.business.JSONDataParserImpl;
import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.business.vo.MainVO;
import com.yuriy.weatherapp.business.vo.TemperatureFormat;
import com.yuriy.weatherapp.net.Downloader;
import com.yuriy.weatherapp.net.HTTPDownloaderImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

/**
 * {@link WeatherService} receives an Intent containing a URL
 * (which is a type of URI) and a {@link android.os.Messenger}.
 * It downloads the weather data at the URL, stores it on the {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO},
 * then returns the reference to this object to the caller
 * using the supplied {@link android.os.Messenger}.
 */
public class WeatherService extends IntentService {

    private static final String CLASS_NAME = WeatherService.class.getSimpleName();

    /**
     * Key for the {@link android.os.Bundle} store to hold a {@link android.os.Messenger}
     */
    protected static final String BUNDLE_KEY_MESSENGER = "MESSENGER";

    /**
     * Key for the {@link android.os.Bundle} store to indicate that it is necessary to
     * download weather data from service.
     */
    protected static final String BUNDLE_KEY_DOWNLOAD_WEATHER_DATA = "DOWNLOAD_WEATHER_DATA";

    /**
     * Key for the {@link android.os.Bundle} store to indicate that it is necessary to
     * download Icon for the Weather condition from service.
     */
    protected static final String BUNDLE_KEY_DOWNLOAD_WEATHER_ICON = "DOWNLOAD_WEATHER_ICON";

    /**
     * Key for the {@link android.os.Bundle} store to indicate that it is necessary to
     * perform some download action.
     */
    protected static final String BUNDLE_KEY_DOWNLOAD_ACTION = "DOWNLOAD_ACTION";

    /**
     * Key for the {@link android.os.Bundle} store to hold a
     * {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}
     */
    private static final String BUNDLE_KEY_WEATHER = "WEATHER";

    /**
     * Key for the {@link android.os.Bundle} store to hold a path to the downloaded icon
     */
    private static final String BUNDLE_KEY_WEATHER_ICON = "WEATHER_ICON";

    private static final double TEMP_CONVERSION_FRACTION = 273.15;

    /**
     * Looper associated with the HandlerThread.
     */
    private volatile Looper mServiceLooper;

    /**
     * Processes Messages sent to it from onStartCommnand() that
     * indicate which images to download from a remote server.
     */
    private volatile ServiceHandler mServiceHandler;

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create and start a background HandlerThread since by
        // default a Service runs in the UI Thread, which we don't
        // want to block.
        final HandlerThread thread = new HandlerThread("WeatherServiceThread");
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler.
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        // This method is call in NON UI Thread so there is no need to perform
        // further operation in separate thread.

        // Create a Message that will be sent to ServiceHandler to
        // retrieve a data based on the URI in the Intent.
        Message message = null;

        // Extract extra which is response for the download action
        if (!intent.hasExtra(BUNDLE_KEY_DOWNLOAD_ACTION)) {
            Log.w(CLASS_NAME, "On handle Intent, no download action found");
            return;
        }

        // TODO : Probably it is better to use single method and in this method extract
        //        information about what exactly it is necessary to download

        // Parse action item: download data, icon, etc ...
        if (intent.getStringExtra(BUNDLE_KEY_DOWNLOAD_ACTION)
                .equals(BUNDLE_KEY_DOWNLOAD_WEATHER_DATA)) {
            message = mServiceHandler.makeDownloadDataMessage(intent);
        } else if (intent.getStringExtra(BUNDLE_KEY_DOWNLOAD_ACTION)
                .equals(BUNDLE_KEY_DOWNLOAD_WEATHER_ICON)) {
            message = mServiceHandler.makeDownloadIconMessage(intent);
        }

        if (message == null) {
            Log.w(CLASS_NAME, "On handle Intent, no message created");
            return;
        }

        // Send the Message to ServiceHandler to retrieve an image
        // based on contents of the Intent.
        mServiceHandler.sendMessage(message);
    }

    /**
     * Factory method to make the desired {@link android.content.Intent}.
     */
    public static Intent makeDownloadWeatherDataIntent(final Context context,
                                                       final Uri uri,
                                                       final Handler downloadHandler) {
        // Create the Intent that's associated to the WeatherService class.
        final Intent intent = new Intent(context, WeatherService.class);

        // Set the URI as data in the Intent.
        intent.setData(uri);

        intent.putExtra(BUNDLE_KEY_DOWNLOAD_ACTION, BUNDLE_KEY_DOWNLOAD_WEATHER_DATA);

        // Create and pass a Messenger as an "extra" so the
        // WeatherService can send back the pathname.
        if (downloadHandler != null) {
            intent.putExtra(BUNDLE_KEY_MESSENGER, new Messenger(downloadHandler));
        }
        return intent;
    }

    /**
     * Factory method to make the desired {@link android.content.Intent}.
     * Intent which describes that it is necessary to download weather conditions icon
     */
    public static Intent makeDownloadWeatherIconIntent(final Context context,
                                                       final Uri uri,
                                                       final Handler downloadHandler) {
        // Create the Intent that's associated to the WeatherService class.
        final Intent intent = new Intent(context, WeatherService.class);

        // Set the URI as data in the Intent.
        intent.setData(uri);

        intent.putExtra(BUNDLE_KEY_DOWNLOAD_ACTION, BUNDLE_KEY_DOWNLOAD_WEATHER_ICON);

        // Create and pass a Messenger as an "extra" so the
        // WeatherService can send back the pathname.
        if (downloadHandler != null) {
            intent.putExtra(BUNDLE_KEY_MESSENGER, new Messenger(downloadHandler));
        }
        return intent;
    }

    /**
     * Helper method that returns {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}
     * if download succeeded.
     */
    public static CurrentWeatherVO getCurrentWeather(final Message message) {
        // Extract the data from Message, which is in the form
        // of a Bundle that can be passed across processes.
        final Bundle data = message.getData();

        // Extract the Weather VO from the Bundle.
        final CurrentWeatherVO currentWeatherVO = (CurrentWeatherVO) data.get(BUNDLE_KEY_WEATHER);

        // Check to see if the download succeeded.
        if (message.arg1 != Activity.RESULT_OK || currentWeatherVO == null)
            return null;
        else
            return currentWeatherVO;
    }

    /**
     * Helper method that returns weather condition icon pathname if download succeeded.
     */
    public static String getWeatherIconPathName(final Message message) {
        // Extract the data from Message, which is in the form
        // of a Bundle that can be passed across processes.
        final Bundle data = message.getData();

        // Extract the pathname from the Bundle.
        final String pathname = data.getString(BUNDLE_KEY_WEATHER_ICON);

        // Check to see if the download succeeded.
        if (message.arg1 != Activity.RESULT_OK || pathname == null)
            return "";
        else
            return pathname;
    }

    /**
     * Helper method that returns value of the Temperature that is necessary to display.
     */
    public static String getTemperatureValue(final CurrentWeatherVO weatherVO,
                                             final String tempFormat) {
        if (weatherVO == null) {
            return "N/A";
        }
        // Extract the data from Weather VO.
        double tempValue = weatherVO.getMainVO().getTemperature();
        if (tempValue == MainVO.DEFAULT_TEMPERATURE) {
            return "N/A";
        }
        // Process the result
        if (AppPreferencesManager.getTemperatureFormat() == TemperatureFormat.CELSIUS) {
            tempValue = kelvinToCelsius(tempValue);
        } else {
            tempValue = kelvinToFahrenheit(tempValue);
        }
        return (String.valueOf(tempValue) + " " + tempFormat);
    }

    /**
     * Helper method that returns value of the Humidity that is necessary to display.
     */
    public static String getHumidityValue(final CurrentWeatherVO weatherVO,
                                          final String appendix) {
        if (weatherVO == null) {
            return "N/A";
        }
        // Extract the data from Weather VO.
        double value = weatherVO.getMainVO().getHumidity();
        if (value == MainVO.DEFAULT_HUMIDITY) {
            return "N/A";
        }
        // Process the result
        return (String.valueOf(value) + " " + appendix);
    }

    /**
     * Utility method to convert Kelvin value to Fahrenheit.
     *
     * @param kelvinValue Kelvin value.
     * @return Fahrenheit value.
     */
    protected static double kelvinToFahrenheit(final double kelvinValue) {
        final double value = (((kelvinValue - TEMP_CONVERSION_FRACTION) * 9.0 / 5.0) + 32);
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(value);
        } catch (NumberFormatException e) {
            Log.e(CLASS_NAME, "KelvinToFahrenheit -> can not parse:" + e.getMessage());
            return Double.NaN;
        }
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Utility method to convert Kelvin to Celsius.
     *
     * @param kelvinValue Kelvin value.
     * @return Celsius value.
     */
    protected static double kelvinToCelsius(final double kelvinValue) {
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(kelvinValue - TEMP_CONVERSION_FRACTION);
        } catch (NumberFormatException e) {
            Log.e(CLASS_NAME, "KelvinToCelsius -> can not parse:" + e.getMessage());
            return Double.NaN;
        }
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * An inner class that inherits from {@link android.os.Handler} and uses its
     * {@link #handleMessage(android.os.Message)} hook method to process Messages sent to
     * it from {@link #onHandleIntent(android.content.Intent)} that indicate which
     * data to download.
     */
    public final class ServiceHandler extends Handler {

        /**
         * Message Id indicates that it is necessary to download Weather Data from the service.
         */
        public static final int MSG_DOWNLOAD_DATA = 1;

        /**
         * Message Id indicates that it is necessary to download Weather Icon from the service.
         */
        public static final int MSG_DOWNLOAD_ICON = 2;

        /**
         * Class constructor initializes the Looper.
         *
         * @param looper The Looper that we borrow from HandlerThread.
         */
        public ServiceHandler(final Looper looper) {
            super(looper);
        }

        /**
         * Hook method that retrieves an image from a remote server.
         */
        public void handleMessage(final Message message) {
            // Download the designated weather data and reply to the
            // MainActivity via the Messenger sent with the Intent.
            downloadWeatherDataAndReply((Intent) message.obj);
        }

        /**
         * A factory method that creates a {@link android.os.Message} that contains
         * information on the weather data to download.
         */
        private Message makeDownloadDataMessage(final Intent intent) {

            final Message message = Message.obtain();
            // Include Intent in Message to indicate which URI to retrieve.
            message.obj = intent;
            message.what = MSG_DOWNLOAD_DATA;
            return message;
        }

        /**
         * A factory method that creates a {@link android.os.Message} that contains
         * information on the weather icon to download.
         */
        private Message makeDownloadIconMessage(final Intent intent) {

            final Message message = Message.obtain();
            // Include Intent in Message to indicate which URI to retrieve.
            message.obj = intent;
            message.what = MSG_DOWNLOAD_ICON;
            return message;
        }

        /**
         * Retrieves the designated weather data and reply to the
         * {@link com.yuriy.weatherapp.MainActivity} via the {@link android.os.Messenger}
         * sent with the {@link android.content.Intent}.
         */
        private void downloadWeatherDataAndReply(final Intent intent) {
            Log.i(CLASS_NAME, "Weather data URL:" + intent.getData().toString());

            if (!intent.hasExtra(BUNDLE_KEY_DOWNLOAD_ACTION)) {
                // TODO : Return appropriate message here
                return;
            }

            // Extract the Messenger.
            final Messenger messenger = (Messenger) intent.getExtras().get(BUNDLE_KEY_MESSENGER);

            if (intent.getStringExtra(BUNDLE_KEY_DOWNLOAD_ACTION)
                    .equals(BUNDLE_KEY_DOWNLOAD_WEATHER_DATA)) {
                // Download the requested weather data.
                final CurrentWeatherVO weatherVO
                        = downloadWeather(intent.getData());

                // Send the weatherVO via the Messenger.
                sendWeather(messenger, weatherVO);
            } else if (intent.getStringExtra(BUNDLE_KEY_DOWNLOAD_ACTION)
                    .equals(BUNDLE_KEY_DOWNLOAD_WEATHER_ICON)) {
                // Download the requested weather condition icon
                String iconPath = "";
                try {
                    iconPath = downloadWeatherConditionIcon(intent.getData());
                } catch (IOException e) {
                    Log.e(CLASS_NAME, "Can not download Icon image:" + e.getMessage());
                }
                // Send the weatherVO via the Messenger.
                sendWeatherConditionIcon(messenger, iconPath);
            }
        }

        /**
         * Send the weatherVO back to the DownloadActivity via the Messenger.
         *
         * @param messenger {@link android.os.Messenger}
         * @param weatherVO {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}
         */
        private void sendWeather(final Messenger messenger, final CurrentWeatherVO weatherVO) {
            // Call factory method to create Message.
            final Message message = makeReplyMessageWithWeatherData(weatherVO);

            try {
                // Send weatherVO to back to the DownloadActivity.
                message.what = MSG_DOWNLOAD_DATA;
                messenger.send(message);
            } catch (RemoteException e) {
                Log.e(CLASS_NAME, "Exception while sending:" + e.getMessage());
            }
        }

        /**
         * Send the weather condition icon back to the DownloadActivity via the Messenger.
         *
         * @param messenger {@link android.os.Messenger}
         * @param iconPath  Path to the downloaded icon.
         */
        private void sendWeatherConditionIcon(final Messenger messenger,
                                              final String iconPath) {
            // Call factory method to create Message.
            final Message message = makeReplyMessageWithWeatherConditionIcon(iconPath);

            try {
                // Send weatherVO to back to the DownloadActivity.
                message.what = MSG_DOWNLOAD_ICON;
                messenger.send(message);
            } catch (RemoteException e) {
                Log.e(CLASS_NAME, "Exception while sending:" + e.getMessage());
            }
        }

        /**
         * A factory method that creates a Message to return to the
         * {@link com.yuriy.weatherapp.MainActivity} with the
         * {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO} of the downloaded data.
         *
         * @param weatherVO Instance of the {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}
         */
        private Message makeReplyMessageWithWeatherData(final CurrentWeatherVO weatherVO) {
            final Message message = Message.obtain();

            // Return the result to indicate whether the download
            // succeeded or failed.
            message.arg1 = weatherVO == null
                    ? Activity.RESULT_CANCELED
                    : Activity.RESULT_OK;

            final Bundle data = new Bundle();

            // Data of the downloaded weather.
            data.putSerializable(BUNDLE_KEY_WEATHER, weatherVO);
            message.setData(data);
            return message;
        }

        /**
         * A factory method that creates a Message to return to the
         * {@link com.yuriy.weatherapp.MainActivity} with the path of the downloaded icon.
         *
         * @param iconPath Path to the downloaded icon.
         */
        private Message makeReplyMessageWithWeatherConditionIcon(final String iconPath) {
            final Message message = Message.obtain();

            // Return the result to indicate whether the download
            // succeeded or failed.
            message.arg1 = iconPath == null
                    ? Activity.RESULT_CANCELED
                    : Activity.RESULT_OK;

            final Bundle data = new Bundle();

            // Data of the downloaded weather.
            data.putSerializable(BUNDLE_KEY_WEATHER_ICON, iconPath);
            message.setData(data);
            return message;
        }

        /**
         * Download the requested weather data and return the instance of the
         * {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}.
         *
         * @param uri URI of the weather data.
         * @return Instance of the {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}
         */
        public CurrentWeatherVO downloadWeather(final Uri uri) {
            // Instantiate appropriate downloader (HTTP one)
            final Downloader downloader = new HTTPDownloaderImpl();
            // Instantiate appropriate parse (JSON one)
            final DataParser dataParser = new JSONDataParserImpl();
            // Instantiate appropriate API service provider
            final APIServiceProvider serviceProvider = new APIServiceProviderImpl(dataParser);
            // Get and return Weather
            return serviceProvider.getCurrentWeatherReportByCity(downloader, uri);
        }

        /**
         * Download the requested weather condition icon and return the bytes array}.
         *
         * @param uri URI of the weather condition icon.
         * @return Path to the downloaded image.
         */
        public String downloadWeatherConditionIcon(final Uri uri)
                throws IOException {
            // Instantiate appropriate downloader (HTTP one)
            final Downloader downloader = new HTTPDownloaderImpl();
            // Instantiate appropriate parse (JSON one)
            final DataParser dataParser = new JSONDataParserImpl();
            // Instantiate appropriate API service provider
            final APIServiceProvider serviceProvider = new APIServiceProviderImpl(dataParser);
            // Get and return Weather
            return serviceProvider.getCurrentWeatherConditionsIcon(WeatherService.this,
                    downloader, uri);
        }
    }
}
