package com.example.rahul.bhavsarorg.MainClasses;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rahul on 21/2/18.
 */

public class AppUtils {

    private static final String KEY_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_HUB_ID = "hub_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_HUB_NAME = "hub_name";
    private static final String KEY_IS_LHCO = "is_lhco";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CURRENT_VERSION = "version";
    private static final String KEY_ACCESS_KEY = "access_key";
    private static final String KEY_UUID = "uuid";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public static void setLogin(Context context, boolean isLoggedin) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(KEY_LOGGED_IN, isLoggedin);
        editor.commit();
    }

    /*
        name changed to erase confusion
     */


//    public static void setUserProfile(UserProfileModel profile, Context context) {
//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//        editor.putString(KEY_USER_EMAIL, profile.getEmail());
//        editor.putString(KEY_HUB_ID, profile.getHubId());
//        editor.putString(KEY_HUB_NAME, profile.getHubName());
//        editor.putString(KEY_USER_ID, profile.getUserId());
//        editor.putString(KEY_USER_NAME, profile.getUserName());
//        editor.putBoolean(KEY_IS_LHCO,profile.isLHCO());
//        editor.commit();
//    }

//    public static UserProfileModel getUserProfile(Context context) {
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//        UserProfileModel profile = new UserProfileModel();
//        profile.setEmail(pref.getString(KEY_USER_EMAIL, ""));
//        profile.setHubId(pref.getString(KEY_HUB_ID, ""));
//        profile.setHubName(pref.getString(KEY_HUB_NAME, ""));
//        profile.setUserId(pref.getString(KEY_USER_ID, ""));
//        profile.setUserName(pref.getString(KEY_USER_NAME, ""));
//        profile.setLHCO(pref.getBoolean(KEY_IS_LHCO,false));
//        return profile;
//    }

    public static String getDeviceId(Context context){
        String deviceId = getIMEI(context);
        String imeiNo = getIMEInumber(context);
        String uniqueId = getUuid(context);
        if (deviceId!=null){
            return deviceId;
        }else if (imeiNo!=null){
            return imeiNo;
        }else
            return uniqueId;
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getIMEInumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tMgr.getSimSerialNumber();
        if (simSerialNumber == null) {
            simSerialNumber = "";
        }
        return simSerialNumber;
    }

    public static String getUuid(Context context) {
        String uuID =  PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_UUID, null);
        if (uuID==null) {
            UUID uuid = UUID.randomUUID();
            uuID = uuid.toString();
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_UUID,uuID).commit();
        }
        return uuID;
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if (mPhoneNumber == null) {
            mPhoneNumber = "";
        }
        return mPhoneNumber;
    }


    public static String getDateTime(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    // date format for post data
    public static String getDateForPOST(String date) {
        if (date == null)
            return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    /***
     * only used for the scan tally
     * @param date
     * @return
     */
    public static Date getDate(String date) {
        if (date == null)
            return null;
        if (date.contains("AM") || date.contains("PM")) {
            date = date.replace("AM", "");
            date = date.replace("PM", "");
        }

        if (date.contains("/")) {
            date = date.replace("/", "-");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy hh:mm:ss");
        try {
//            Log.d("date" ,date +  "----date = "+dateFormat.parse(date));
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * only used for the scan tally
     * @param date
     * @return
     */
    public static Date getDateForCreatedDate(String date) {
        if (date == null)
            return null;
        if (date.contains("AM") || date.contains("PM")) {
            date = date.replace("AM", "");
            date = date.replace("PM", "");
        }

        if (date.contains("/")) {
            date = date.replace("/", "-");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy hh:mm:ss");
        try {
//            Log.d("date" ,date +  "----date = "+dateFormat.parse(date));
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /***
     * only used for the scan tally
     * @param date
     * @return
     */
    public static Date getDateForLastModifiedDate(String date) {
        if (date == null)
            return null;
        if (date.contains("AM") || date.contains("PM")) {
            date = date.replace("AM", "");
            date = date.replace("PM", "");
        }

        if (date.contains("/")) {
            date = date.replace("/", "-");
        }

        DateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy hh:mm:ss");
        DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date2 = dateFormat.parse(date);
            Date formattedDate = targetFormat.parse(date2.toString());  // 20120821

//            Log.d("date" ,date +  "----date = "+dateFormat.parse(date));
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getInitials(String typeName) {
        StringBuilder builder = new StringBuilder();
        String[] arr = typeName.split(" ");
        for (String str : arr) {
            builder.append(str.charAt(0));
        }
        return builder.toString();
    }


    public static String getRupees(int cash) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(cash);
    }

    public static String getMonth(int monthOfYear) {
        if (monthOfYear < 10) {
            return 0 + "" + monthOfYear;
        } else {
            return "" + monthOfYear;
        }
    }

    /* public static Date getDate(int year, int monthOfYear, int day) {
        return AppUtils.getDate(year+"-"+getMonth(monthOfYear)+"-"+
                 day);
     }*/
    public static String getDate(int year, int monthOfYear, int day) {
        return getMonth(day) + "-" + getMonth(monthOfYear) + "-" +
                year;
    }

    public static String getDateString(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        return getDate(year, monthOfYear, dayOfMonth) + " To " + getDate(yearEnd, monthOfYearEnd, dayOfMonthEnd);
    }

    public static String getDateString(String from, String to) {
        return from + " To " + to;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            String pattern = "^[789]\\d{9}$";
            Pattern pat = Pattern.compile(pattern);
            Matcher m = pat.matcher(phoneNumber);
            return m.find();
        } else return phoneNumber.length() == 11;
    }

    public static boolean isNonEmpty(String str) {
        return !TextUtils.isEmpty(str) && str.trim().length() > 0;
    }

    public static String[] getDateAsDMY(String stayFrom) {
        if (TextUtils.isEmpty(stayFrom)) {
            return null;
        }
        return stayFrom.split("-");
    }

    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    public static int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    public static String[] getDateAsDMY(Calendar instance) {

        return new String[]{instance.get(Calendar.DAY_OF_MONTH) + "",
                instance.get(Calendar.MONTH) + "",
                instance.get(Calendar.YEAR) + ""};
    }

    public static String getDateAsDMYString(Calendar instance) {
        //    Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(instance.getTime());
 /*       return  instance.get(Calendar.DAY_OF_MONTH)+"-"+
                instance.get(Calendar.MONTH)+"-"+
                instance.get(Calendar.YEAR)+" "+ instance.get(Calendar.HOUR_OF_DAY)+":"
                +instance.get(Calendar.MINUTE)+":"+instance.get(Calendar.SECOND);*/
    }

    //convert date dd-mm-yyyy to mm-yyyy
    public static String convertToMMYYYY(String date) {
        String strDateTime = "";
        if (!TextUtils.isEmpty(date)) {
            DateFormat iFormatter = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat oFormatter = new SimpleDateFormat("MM-yyyy");
            try {
                strDateTime = oFormatter.format(iFormatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return strDateTime;
    }

    //show submitted successfully message
    public static void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isValidName(String name) {
        name = name.trim();
       /* boolean b = name.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
        Log.d("name", String.valueOf(b));*/
//        return name.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
        return name.matches("^[^±!@£$%^&*_+§¡€#¢§¶•ªº«\\\\/<>?:;\\d|=,]{1,500}$");
    }

    public static boolean isValidPhotoId(String photoId) {
        String pattern = "^[a-zA-Z0-9 ]*$";
        Pattern pat = Pattern.compile(pattern);
        Matcher m = pat.matcher(photoId);
        return m.find();
    }


    public static void goToPlayStore(Context context, String packageName) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getUrlMarketPlayStore(packageName)));

        if (!startActivity(context, intent)) {

            intent.setData(Uri.parse(getUrlPlayStore(packageName)));

            if (!startActivity(context, intent)) {

                Toast.makeText(context, "Could not open Android market, please install the market app.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static boolean startActivity(Context context, Intent aIntent) {
        try {
            context.startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private static String getUrlMarketPlayStoreBase() {
        return "market://details?id=".trim();
    }


    private static String getUrlMarketPlayStore(String appPackageName) {
        return getUrlMarketPlayStoreBase() + appPackageName.trim();
    }

    private static String getUrlPlayStore(String appPackageName) {
        return getUrlPlayStoreBase() + appPackageName.trim();
    }

    private static String getUrlPlayStoreBase() {
        return "https://play.google.com/store/apps/details?id=".trim();
    }


//    public static void beepForSuccessful(Context context) {
//        MediaPlayer mp = MediaPlayer.create(context, R.raw.beep);
//        mp.start();
//    }
//
//    public static void beepForError(Context context) {
//        MediaPlayer mp = MediaPlayer.create(context, R.raw.errrortune);
//        mp.start();
//    }

    public static void showAPKDetails(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void setPreviousLoc(Context context, String latLong) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(KEY_LOCATION, latLong);
        editor.commit();
    }

    public static void setPreviousLoc(Context context, double latLong) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(KEY_LOCATION, Double.doubleToRawLongBits(latLong));
        editor.commit();
    }

    public static double[] getPrevLatLong(Context context) {
        String latlong = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LOCATION, "0@0");
        String[] lat = latlong.split("@");
        double latl[] = new double[2];
        latl[0] = parseDouble(lat[0]);
        latl[1] = parseDouble(lat[1]);
        return latl;
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void trackScreen(String screenName, Context context) {
//        if (context instanceof Activity)
//            ((AppController) ((Activity) context).getApplication()).trackScreenView(screenName);
//        else
//            ((AppController) context.getApplicationContext()).trackScreenView(screenName);
    }

    public static void trackEvent(String category, String action, String label, Context context) {
//        if (context instanceof Activity)
//           // ((AppController) ((Activity) context).getApplication()).trackEvent(category, action, label);
//        else
           // ((AppController) context.getApplicationContext()).trackEvent(category, action, label);
    }

    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0).versionName;
    }


    public static boolean isGpsOn(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void turnGPSOn(Context context) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        context.sendBroadcast(intent);


    }

    public static String getCurrentTimeForLog() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm k");
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static void putCurrentVersion(Context context, int version) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(KEY_CURRENT_VERSION, version).commit();
    }

    public static boolean isVersionAllowed(Context context) {
        int firebaseAppVersion = PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_CURRENT_VERSION, -1);
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode >= firebaseAppVersion;

    }

    public static String getErrorMessageFromThrowable(Throwable t) {

        String errorMessage = "";

        errorMessage = getErrorMessageFromStackStrace(t.getStackTrace()).trim();

        if (errorMessage.length() == 0) {

            errorMessage = getErrorMessageFromStackStrace(t.getCause().getStackTrace()).trim();

        }
        return errorMessage;
    }

    private static String getErrorMessageFromStackStrace(StackTraceElement[] stackTraceElement) {
        String description = "";
        if (stackTraceElement != null) {

            for (int i = 0; i < stackTraceElement.length; i++) {

                StackTraceElement ste = stackTraceElement[i];

                if (ste.getClassName().startsWith("com.xpressbees")) {

                    description = ste.getClassName() + ">>"
                            + ste.getMethodName() + ">>" + ste.getLineNumber();
                }
            }
        }
        return description;

    }


//    public static String getUserDetailandTime(Context context) {
//
//        String UserId = PreferenceUtils.getInstance(context).getUserName();
//        String HubId = PreferenceUtils.getInstance(context).getHubId();
//        String time = getCurrentTimeForLog();
//        return UserId + "|" + HubId + "|" + time;
//    }

    public static boolean checkVersionOrShowUpgrade(Context context) {
        if (isVersionAllowed(context))
            return true;
        else {
            showUpgradeDialog(context);
            return false;
        }
    }

    public static void showUpgradeDialog(final Context context) {
//        DialogHelper.showDialogForUpdateVersion(context, context.getString(R.string.update_dialog), context.getString(R.string.update_msg),
//                context.getString(R.string.update_app), null, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        goToPlayStore(context, "com.xpreesbees.pda");
//                    }
//                });
    }
   /* public static Location getLocation(Context mContext, String type, LocationListener listner) {
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(mContext.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                AppConstants.MIN_TIME_BW_LOCATION_UPDATES,
                AppConstants.MIN_DISTANCE_CHANGE_FOR_LOCATION_UPDATES, listner);

        Log.d("location" ,"type passed = "+ type);

        return locationManager.getLastKnownLocation(type);

    }

    public static boolean isGpsEnabled(LocationManager locationManager) {
        // getting GPS status
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isLocationFromNetworkEnabled(LocationManager locationManager) {
        // getting GPS status
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static String getLatitude(Context context, LocationListener listner) {
        if(!isLocationCapturePossible(context))
            return "0.0";
        Location location = getLocation(context ,  getLocationProviderType(context), listner);
        if (location == null)
            return "0.0";
        else
            return String.valueOf(location.getLatitude());
    }

    public static String getLongitude(Context context, LocationListener listner) {
        if(!isLocationCapturePossible(context))
            return "0.0";
        Location location = getLocation(context, getLocationProviderType(context), listner);
        if (location == null)
            return "0.0";
        else
            return String.valueOf(location.getLongitude());
    }

    public static String getLatitudeLongitude(Context context, LocationListener listner) {
        if(!isLocationCapturePossible(context))
            return "0.0@0.0";

        Location location = getLocation(context , getLocationProviderType(context), listner);
        if (location == null)
            return "0.0@0.0";
        else
            return String.valueOf(location.getLatitude() + "@" + location.getLongitude());
    }

    public static boolean isLocationCapturePossible(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(mContext.LOCATION_SERVICE);
        return isGpsEnabled(locationManager) || isLocationFromNetworkEnabled(locationManager);
    }

    public static String getLocationProviderType(Context mContext){
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(mContext.LOCATION_SERVICE);
        if(isGpsEnabled(locationManager))
            return LocationManager.GPS_PROVIDER;
        else if(isLocationFromNetworkEnabled(locationManager))
            return LocationManager.NETWORK_PROVIDER;
        else
            return null;
    }

    public static float getLocationAccuracy(Location location){
        return location.getAccuracy();
    }*/

    public static void setUrls(HashMap<String, String> values, Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//        if (!TextUtils.isEmpty(values.get(BaseUrls.KEY_BASE_URL_STAGE)))
//            editor.putString(BaseUrls.KEY_BASE_URL_STAGE, values.get(BaseUrls.KEY_BASE_URL_STAGE));
//        if (!TextUtils.isEmpty(values.get(BaseUrls.KEY_BASE_URL_LIVE)))
//            editor.putString(BaseUrls.KEY_BASE_URL_LIVE, values.get(BaseUrls.KEY_BASE_URL_LIVE));
//        if (!TextUtils.isEmpty(values.get(BaseUrls.KEY_BASE_URL_LIVE_NEW)))
//            editor.putString(BaseUrls.KEY_BASE_URL_LIVE_NEW, values.get(BaseUrls.KEY_BASE_URL_LIVE_NEW));
//        if (!TextUtils.isEmpty(values.get(BaseUrls.KEY_BASE_URL_STAGE_NEW)))
//            editor.putString(BaseUrls.KEY_BASE_URL_STAGE_NEW, values.get(BaseUrls.KEY_BASE_URL_STAGE_NEW));
        editor.commit();

    }

    public static void storeAccessKey(String returnKey, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_ACCESS_KEY, returnKey).commit();
    }

    public static String getAuthKey(Context context) {
//        return "abc";
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_ACCESS_KEY, "");
    }


}
