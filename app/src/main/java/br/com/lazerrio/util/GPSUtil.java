package br.com.lazerrio.util;

import android.content.Context;
import android.location.LocationManager;

public class GPSUtil {

    public static boolean gpsIsEnable(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            return false;
        }
    }

}
