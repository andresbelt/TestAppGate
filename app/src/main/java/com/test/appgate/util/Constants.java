package com.test.appgate.util;

import com.test.appgate.ui.MainActivity;

public class Constants {

    public static final String API_BASE_URL = "http://api.geonames.org/";
    public static final String TAG = MainActivity.class.getName();

    public static final String PACKAGE_NAME = "com.example.android.whileinuselocation";

    public static final String ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST =
            "$PACKAGE_NAME.action.FOREGROUND_ONLY_LOCATION_BROADCAST";

    public static final String EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION";

    public static final String EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION =
            "$PACKAGE_NAME.extra.CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION";
}
