package com.yuriy.weatherapp.net;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

/**
 * Implementation of the {@link com.yuriy.weatherapp.net.Downloader} interface.
 * {@link HTTPDownloaderImpl} allows to download weather data from the
 * wer resource over HTTP protocol.
 */
public class HTTPDownloaderImpl implements Downloader {

    /**
     * Tag to use in logging message.
     */
    private static final String CLASS_NAME = HTTPDownloaderImpl.class.getSimpleName();

    @Override
    public byte[] downloadDataFromUri(final Uri uri) {
        HttpGet request = null;
        try {
            request = new HttpGet(uri.toString());
        } catch (IllegalArgumentException e) {
            Log.e(CLASS_NAME, "IllegalArgumentException error: " + e.getMessage());
        }

        if (request == null) {
            return new byte[0];
        }

        final HttpClient httpClient = new DefaultHttpClient();
        try {
            final HttpResponse httpResponse = httpClient.execute(request);
            Log.d(CLASS_NAME, "Response code: " + httpResponse.getStatusLine().getStatusCode());
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    try {
                        return EntityUtils.toByteArray(entity);
                    } catch (IOException e) {
                        Log.e(CLASS_NAME, "EntityUtils error: " + e.getMessage());
                    }
                }
            }
        } catch (ClientProtocolException e) {
            Log.e(CLASS_NAME, "ClientProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(CLASS_NAME, "IOException: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(CLASS_NAME, "SecurityException error: " + e.getMessage());
        }
        return new byte[0];
    }
}
