package com.yuriy.weatherapp.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.test.ServiceTestCase;

import java.lang.ref.WeakReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/23/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */
public class WeatherServiceTest extends ServiceTestCase<WeatherService> {

    /**
     * Contains an Intent used to start the service
     */
    private Intent mStartServiceIntent;

    /**
     * Contains a handle to the Weather Service
     */
    private WeatherService mService;

    /**
     * Contains a handle to the Handler
     */
    private Handler mHandler;

    /**
     * URI object
     */
    private Uri mUri;

    /**
     * Test URL
     */
    private String mUrl = "http://www.example.com";

    public WeatherServiceTest() {
        super(WeatherService.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mUri = Uri.parse(mUrl);
        mHandler = new DownloadHandler(this);

        // Sets up an intent to start the service under test
        mStartServiceIntent = WeatherService.makeDownloadWeatherDataIntent(getSystemContext(), mUri, mHandler);
    }

    public void testIntentWithCorrectDataCreated() {
        assertThat("Intent should be created", mStartServiceIntent, notNullValue());
        assertThat("URI should be original", mStartServiceIntent.getData(), is(mUri));
        assertThat("URL should be original",
                mStartServiceIntent.getData().toString(), is(mUrl));
        assertThat("Intent should contain Messenger",
                mStartServiceIntent.getExtras().containsKey(WeatherService.BUNDLE_KEY_MESSENGER),
                is(true));
    }

    /**
     * Tests the service's onCreate() method. Starts the service using startService(Intent)
     */
    public void testServiceCreate() {
        // Starts the service under test
        startService(mStartServiceIntent);

        // Gets a handle to the service under test.
        mService = getService();

        // Asserts that the Service is created.
        assertThat("Instance of the Service should be created", mService, notNullValue());

        mService.stopSelf();
    }

    private static class DownloadHandler extends Handler {

        /**
         * Allows Activity to be garbage collected properly.
         */
        private WeakReference<WeatherServiceTest> mActivity;

        /**
         * Class constructor constructs {@link #mActivity} as weak reference
         * to the activity.
         *
         * @param activity The corresponding activity.
         */
        public DownloadHandler(final WeatherServiceTest activity) {
            mActivity = new WeakReference<WeatherServiceTest>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // TODO : Can not get callback from Service here. Need more time to investigate
            //        this test case
        }
    }

    public void testConvertFahrenheitToCelsius() {
        final double kelvin = 259.65;
        final double fahrenheit = 7.7;
        final double celsius = -13.5;

        assertThat("Fahrenheit value should be:" + fahrenheit,
                WeatherService.kelvinToFahrenheit(kelvin), is(fahrenheit));
        assertThat("Celsius value should be:" + celsius,
                WeatherService.kelvinToCelsius(kelvin), is(celsius));
    }
}
