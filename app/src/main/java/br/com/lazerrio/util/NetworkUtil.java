package br.com.lazerrio.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetworkUtil {

    public static boolean checkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Network network = cm.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities activeNetwork = cm.getNetworkCapabilities(network);

            if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true;
            }
            if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }

        return false;
    }

}
