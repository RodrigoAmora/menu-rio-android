package br.com.lazerrio.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PermissionUtil {

    private static final int PERMISSION_REQUEST_CODE = 200;

    public static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    public static boolean checkPermissions(Context context, String[] permissions) {
        int resultAccessFineLocation = ContextCompat.checkSelfPermission(context, permissions[0]);
        int resultAccessCoarseLocation = ContextCompat.checkSelfPermission(context, permissions[1]);
        return resultAccessFineLocation == PackageManager.PERMISSION_GRANTED && resultAccessCoarseLocation == PackageManager.PERMISSION_GRANTED;
    }

}
