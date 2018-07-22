package br.com.lazerrio.factory;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;

import java.util.ArrayList;
import java.util.List;

import br.com.lazerrio.R;
import br.com.lazerrio.ui.activity.MainActivity;

public class ShortcutFactory {

    @TargetApi(25)
    public static List<ShortcutInfo> createRetrofit(Context context, String[] shortLabels, String[] disabledMessage, String[] options, Integer[] icons) {
        List<ShortcutInfo> shortcutInfos = new ArrayList();
        for (int i = 0; i < shortLabels.length; i++) {
            Intent intentRestaurant = new Intent(context, MainActivity.class);
            intentRestaurant.putExtra("option", options[i]);
            intentRestaurant.setAction(Intent.ACTION_VIEW);

            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context.getApplicationContext(), "shortcut"+shortLabels[i])
                    .setIntent(intentRestaurant)
                    .setShortLabel(shortLabels[i])
                    .setLongLabel(shortLabels[i])
                    .setDisabledMessage(disabledMessage[i])
                    .setIcon(Icon.createWithResource(context, icons[i]))
                    .build();

            shortcutInfos.add(shortcutInfo);
        }
        return shortcutInfos;
    }

}
