package br.com.lazerrio.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    public static void changeFragment(int id, Fragment fragment, FragmentManager fragmentManager, boolean backstack, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment);

        if (backstack) {
            transaction.addToBackStack(null);
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        transaction.commit();
    }

}
