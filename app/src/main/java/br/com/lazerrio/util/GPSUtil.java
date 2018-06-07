package br.com.lazerrio.util;

import android.content.Context;
import android.location.LocationManager;

public class GPSUtil {

    public static boolean gpsIsEnable(Context context) {
        try{
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
