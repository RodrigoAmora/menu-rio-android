package br.com.lazerrio.util;

import android.content.Context;
import android.content.Intent;

public class ShareUtil {

    public static Intent directShare(String title, String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        return sharingIntent;
    }

}
