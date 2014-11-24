package com.yuriy.weatherapp.business;

import android.net.Uri;
import android.test.InstrumentationTestCase;

import com.yuriy.weatherapp.ApplicationTest;
import com.yuriy.weatherapp.api.APIServiceProvider;
import com.yuriy.weatherapp.api.APIServiceProviderImpl;
import com.yuriy.weatherapp.net.Downloader;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with Android Studio.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 9:58
 */
public class DataParserTest extends InstrumentationTestCase {

    /**
     * Downloader mock
     */
    private Downloader mDownloader;

    /**
     * Data parser mock
     */
    private DataParser mDataParserMock;

    /**
     * Service Provide mock
     */
    private APIServiceProvider mServiceProvider;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ApplicationTest.setupMocking(this);
        mDownloader = mock(Downloader.class);
        mDataParserMock = mock(JSONDataParserImpl.class);
        mServiceProvider = new APIServiceProviderImpl(mDataParserMock);

        // when downloader asks to download data - return real bytes array
        when(mDownloader.downloadDataFromUri(Uri.parse("")))
                .thenReturn(ApplicationTest.RAW_RESPONSE.getBytes());

        // TODO : In every test it is possible to test exact value of the formal parameter
        //        of the method that is called. For the simplicity "anyString()" check method
        //        is used.
    }

    public void testParseCityCoordinatesCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseCityCoordinates(anyString());
    }

    public void testParseSysInfoCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseSysInfo(anyString());
    }

    public void testParseWeatherCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseWeather(anyString());
    }

    public void testParseMainInfoCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseMainInfo(anyString());
    }

    public void testParseWindCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseWind(anyString());
    }

    public void testParseRainCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseRain(anyString());
    }

    public void testParseCloudsCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseClouds(anyString());
    }

    public void testParseDtCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseDt(anyString());
    }

    public void testParseIdCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseId(anyString());
    }

    public void testParseNameCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseName(anyString());
    }

    public void testParseCodCall() {
        mServiceProvider.getCurrentWeatherReportByCity(mDownloader, Uri.parse(""));

        verify(mDataParserMock, times(1)).parseCod(anyString());
    }
}