package br.com.lazerrio.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import br.com.lazerrio.R;

public class FragmentUtil {

    public static void changeFragment(int id, Fragment fragment, FragmentManager fragmentManager, boolean backstack, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
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
