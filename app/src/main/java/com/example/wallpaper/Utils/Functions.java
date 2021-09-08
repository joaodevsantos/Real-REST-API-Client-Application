package com.example.wallpaper.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.wallpaper.R;

public class Functions {
    public static void chainMainFragment(FragmentActivity fragmentActivity,
                                          Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public static void chainMainFragmentWithBack(FragmentActivity fragmentActivity,
                                         Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
