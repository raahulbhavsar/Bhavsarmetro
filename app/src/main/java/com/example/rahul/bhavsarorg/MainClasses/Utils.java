package com.example.rahul.bhavsarorg.MainClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by rahul on 21/2/18.
 */

public class Utils {

    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public static boolean hasHoneycomb()
    {
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB;
    }
}
