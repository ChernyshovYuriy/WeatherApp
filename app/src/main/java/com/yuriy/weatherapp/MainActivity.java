package com.yuriy.weatherapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yuriy.weatherapp.business.AppPreferencesManager;
import com.yuriy.weatherapp.business.vo.CurrentWeatherVO;
import com.yuriy.weatherapp.business.vo.TemperatureFormat;
import com.yuriy.weatherapp.net.UrlBuilder;
import com.yuriy.weatherapp.net.UrlRequestParameters;
import com.yuriy.weatherapp.service.WeatherService;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    /**
     * Tag to use in the logging message.
     */
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();

    /**
     * Key to store selected City in the {@link android.os.Bundle}
     */
    private static final String KEY_SELECTED_CITY = "SelectedCity";

    /**
     * Key to store current Weather VO in the {@link android.os.Bundle}
     */
    private static final String KEY_CURRENT_WEATHER_VO = "CurrentWeatherVO";

    /**
     * Key to store current Weather Icon in the {@link android.os.Bundle}
     */
    private static final String KEY_CURRENT_WEATHER_ICON = "CurrentWeatherIcon";

    /**
     * Display progress of download
     */
    private ProgressDialog mProgressDialog;

    /**
     * Stores an instance of {@link com.yuriy.weatherapp.MainActivity.DownloadHandler}.
     */
    private Handler mDownloadHandler = null;

    /**
     * Weather VO for the current selected City.
     */
    private CurrentWeatherVO mCurrentWeatherVO;

    /**
     * Current selected City name.
     */
    private String mCurrentCity = "";

    /**
     * Current downloaded icon for the weather condition..
     */
    private Bitmap mCurrentWeatherConditionIcon;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(CLASS_NAME, "onCreate: " + savedInstanceState);

        // Initialize context
        AppPreferencesManager.setAppContext(this);

        // Restore data which are keeping in the Bundle from the previous activity life
        restoreSavedInstance(savedInstanceState);

        // Initialize the downloadHandler.
        mDownloadHandler = new DownloadHandler(this);

        // Spinner with the cities list
        Spinner citiesSpinner = (Spinner) findViewById(R.id.cities_spinner_view);
        // Create an ArrayAdapter using the string array and a default citiesSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the citiesSpinner
        citiesSpinner.setAdapter(adapter);

        // Apply listener to the spinner
        citiesSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(final AdapterView<?> parent,
                                               final View view, final int position,
                                               final long id) {


                        final String selectedCity
                                = String.valueOf(parent.getAdapter().getItem(position));

                        if (mCurrentCity.equals(selectedCity)) {
                            // Skip same city selection
                            return;
                        }

                        mCurrentCity = selectedCity;

                        Log.d(CLASS_NAME, "City selected: " + mCurrentCity);

                        // Download weather for the selected city.
                        downloadWeatherData(mCurrentCity, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                }
        );
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {

        // Save state of the activities fields:
        // Selected City
        outState.putString(KEY_SELECTED_CITY, mCurrentCity);
        // Current Weather VO
        outState.putSerializable(KEY_CURRENT_WEATHER_VO, mCurrentWeatherVO);
        // Current Weather Icon
        outState.putParcelable(KEY_CURRENT_WEATHER_ICON, mCurrentWeatherConditionIcon);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Create an instance of the dialog fragment and show it
            final DialogFragment settingsDialog = new SettingsDialog();
            settingsDialog.show(getFragmentManager(), SettingsDialog.FRAGMENT_TAG);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Restore previous state of the Activity.
     *
     * @param savedInstanceState Saved {@link android.os.Bundle}.
     */
    private void restoreSavedInstance(final Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        // Restore selected City name.
        if (savedInstanceState.containsKey(KEY_SELECTED_CITY)) {
            mCurrentCity = savedInstanceState.getString(KEY_SELECTED_CITY);
        }

        // Restore current Weather Icon. Must be use before Weather VO when restore
        if (savedInstanceState.containsKey(KEY_CURRENT_WEATHER_ICON)) {
            displayWeatherIconImage((Bitmap) savedInstanceState.get(KEY_CURRENT_WEATHER_ICON));
        }

        // Restore current Weather VO.
        if (savedInstanceState.containsKey(KEY_CURRENT_WEATHER_VO)) {
            setCurrentWeatherVO((CurrentWeatherVO) savedInstanceState.get(KEY_CURRENT_WEATHER_VO));

            updateUI();
        }
    }

    /**
     * Helper method to start {@link android.app.IntentService} which will download weather
     * data and return {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO} in case of success.
     *
     * @param cityName     Name of the City.
     * @param cityPosition Position of the City in the cities list.
     */
    private void downloadWeatherData(final String cityName, final int cityPosition) {
        final String[] countries = getResources().getStringArray(R.array.countries_array);
        final String countryAbbreviation = countries[cityPosition];

        // Create URL request parameters holder and pass it to the URL builder.
        final UrlRequestParameters parameters = new UrlRequestParameters();
        // Set city name
        try {
            parameters.setCity(URLEncoder.encode(cityName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w(CLASS_NAME, "Can not encode city:" + e.getMessage());
        }
        // Set country abbreviation
        parameters.setCountryAbbreviation(countryAbbreviation);
        // Set specific request key for the case where city is a value
        parameters.setCityNameKey("q");

        // Build URL
        // TODO : Probably it's better to shift this operation into WeatherService
        final String url = UrlBuilder.getWeatherUrlByCityName(parameters);

        // Inform the user that the download is starting.
        showDialog("Downloading weather data");

        // Invalidate Icon
        mCurrentWeatherConditionIcon = null;

        // Create an Intent to download weather data in the background via a Service.
        // The downloaded data is later displayed in the
        // UI Thread via the downloadHandler() method defined below.
        final Intent intent = WeatherService.makeDownloadWeatherDataIntent(this,
                Uri.parse(url),
                mDownloadHandler);

        // Start the DownloadService.
        startService(intent);
    }

    /**
     * Helper method to start {@link android.app.IntentService} which will download weather
     * icon and return path to the saved image in case of success.
     *
     * @param weatherVO Weather VO.
     */
    private void downloadWeatherIcon(final CurrentWeatherVO weatherVO) {
        if (weatherVO == null) {
            return;
        }
        if (weatherVO.getWeatherItemsSize() == 0) {
            return;
        }
        final String iconId = weatherVO.getWeatherItemAt(0).getIcon();
        // Build URL
        // TODO : Probably it's better to shift this operation into WeatherService
        final String url = UrlBuilder.getWeatherIconByCode(iconId);

        // Inform the user that the download is starting.
        showDialog("Downloading weather icon");

        // Create an Intent to download weather data in the background via a Service.
        // The downloaded data is later displayed in the
        // UI Thread via the downloadHandler() method defined below.
        final Intent intent = WeatherService.makeDownloadWeatherIconIntent(this,
                Uri.parse(url),
                mDownloadHandler);

        // Start the DownloadService.
        startService(intent);
    }

    /**
     * Display the Dialog to the User.
     *
     * @param message The String to display.
     */
    private void showDialog(final String message) {
        mProgressDialog = ProgressDialog.show(this, "Download", message, true);
    }

    /**
     * Dismiss the Dialog
     */
    private void dismissDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }

    /**
     * Set Weather VO received from the {@link com.yuriy.weatherapp.service.WeatherService}.
     * @param value {@link com.yuriy.weatherapp.business.vo.CurrentWeatherVO}.
     */
    private void setCurrentWeatherVO(final CurrentWeatherVO value) {
        Log.d(CLASS_NAME, "Weather VO:" + value);
        mCurrentWeatherVO = value;

        // Update UI
        updateUI();

        if (mCurrentWeatherConditionIcon == null) {
            downloadWeatherIcon(mCurrentWeatherVO);
        }
    }

    /**
     * Update UI with received parameters
     */
    private void updateUI() {
        // Update Temperature
        updateTemperature();
        // Update Humidity
        final TextView humidityView = (TextView) findViewById(R.id.humidity_view);
        if (humidityView != null) {
            String appendix = getString(R.string.percentage_abbreviation);
            humidityView.setText(WeatherService.getHumidityValue(mCurrentWeatherVO, appendix));
        }
    }

    /**
     * Update Temperature value.
     */
    public void updateTemperature() {
        final TextView tempView = (TextView) findViewById(R.id.temp_view);
        if (tempView != null) {
            String tempFormat = getString(R.string.fahrenheit_abbreviation);
            if (AppPreferencesManager.getTemperatureFormat() == TemperatureFormat.CELSIUS) {
                tempFormat = getString(R.string.celsius_abbreviation);
            }
            tempView.setText(WeatherService.getTemperatureValue(mCurrentWeatherVO, tempFormat));
        }
    }

    /**
     * Display a downloaded weather condition icon image.
     *
     * @param image The bitmap image
     */
    private void displayWeatherIconImage(final Bitmap image) {

        mCurrentWeatherConditionIcon = image;

        final ImageView iconView = (ImageView) findViewById(R.id.icon_view);
        if (iconView != null) {
            iconView.setImageBitmap(mCurrentWeatherConditionIcon);
        }
    }

    /**
     * An inner class that inherits from {@link android.os.Handler}
     * and uses its {@link android.os.Handler#handleMessage(android.os.Message)}
     * hook method to process Messages
     * sent to it from the {@link com.yuriy.weatherapp.service.WeatherService}.
     */
    private static class DownloadHandler extends Handler {

        /**
         * Allows Activity to be garbage collected properly.
         */
        private WeakReference<MainActivity> mActivity;

        /**
         * Class constructor constructs {@link #mActivity} as weak reference
         * to the activity.
         *
         * @param activity The corresponding activity.
         */
        public DownloadHandler(final MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        /**
         * This hook method is dispatched in response to receiving the
         * pathname back from the {@link com.yuriy.weatherapp.service.WeatherService}.
         */
        public void handleMessage(final Message message) {

            final MainActivity activity = mActivity.get();

            // Bail out if the MainActivity is gone.
            if (activity == null) {
                return;
            }

            // Stop displaying the progress dialog.
            activity.dismissDialog();

            // Get message Id
            final int what = message.what;

            switch (what) {
                case WeatherService.ServiceHandler.MSG_DOWNLOAD_DATA:
                    // Try to extract the currentWeatherVO from the message.
                    final CurrentWeatherVO currentWeatherVO
                            = WeatherService.getCurrentWeather(message);

                    // See if the download success or not.
                    if (currentWeatherVO == null)
                        activity.showDialog("Failed download weather");

                    // Set Weather VO into Activity
                    activity.setCurrentWeatherVO(currentWeatherVO);
                    break;
                case WeatherService.ServiceHandler.MSG_DOWNLOAD_ICON:
                    // Try to extract the pathname from the message.
                    final String pathname = WeatherService.getWeatherIconPathName(message);

                    // See if the download worked or not.
                    if (pathname == null)
                        return;

                    // Display the image in the UI Thread.
                    activity.displayWeatherIconImage(BitmapFactory.decodeFile(pathname));
                    break;
                default:
                    Log.d(CLASS_NAME, "Unknown message id received:" + what);
            }
        }
    }
}
