package com.yuriy.weatherapp.net;

import android.net.Uri;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/21/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */

/**
 * {@link com.yuriy.weatherapp.net.Downloader} interface provides method which allows to perform
 * download operations. Different implementations will allows to perform downloading via different
 * protocols: HTTP, FTP, etc ...
 */
public interface Downloader {

    /**
     * Method to download data from provided {@link android.net.Uri}.
     *
     * @param uri Provided {@link android.net.Uri}.
     * @return Downloaded data as bytes array.
     */
    public byte[] downloadDataFromUri(final Uri uri);
}
