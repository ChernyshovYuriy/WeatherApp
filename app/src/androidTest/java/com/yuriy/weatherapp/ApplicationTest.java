package com.yuriy.weatherapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public static final String RAW_RESPONSE
            = "{\"coord\":{\"lon\":139,\"lat\":35},\n" +
            "\"sys\":{\"country\":\"JP\",\"sunrise\":1369769524,\"sunset\":1369821049},\n" +
            "\"weather\":[{\"id\":804,\"main\":\"clouds\",\"description\":\"overcast clouds\"," +
            "\"icon\":\"04n\"}],\n" +
            "\"main\":{\"temp\":289.5,\"humidity\":89,\"pressure\":1013,\"temp_min\":287.04," +
            "\"temp_max\":292.04},\n" +
            "\"wind\":{\"speed\":7.31,\"deg\":187.002},\n" +
            "\"rain\":{\"3h\":0},\n" +
            "\"clouds\":{\"all\":92},\n" +
            "\"dt\":1369824698,\n" +
            "\"id\":1851632,\n" +
            "\"name\":\"Shuzenji\",\n" +
            "\"cod\":200}";

    public ApplicationTest() {
        super(Application.class);
    }

    public static void setupMocking(final InstrumentationTestCase testCase) {
        System.setProperty("dexmaker.dexcache", testCase.getInstrumentation()
                .getTargetContext()
                .getCacheDir()
                .getPath());
    }
}