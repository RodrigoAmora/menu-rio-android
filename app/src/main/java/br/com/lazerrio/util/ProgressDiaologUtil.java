package br.com.lazerrio.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDiaologUtil {

    private static ProgressDialog progressDialog;

    public static void showProgressDiaolg(Context ctx, String title, String msg, boolean cancelable) {
        progressDialog = ProgressDialog.show(ctx, title, msg, false, cancelable);
    }

    public static void dimissProgressDialog() {
        progressDialog.dismiss();
    }

}
