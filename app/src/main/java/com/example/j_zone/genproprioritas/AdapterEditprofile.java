package com.example.j_zone.genproprioritas;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterEditprofile extends FragmentPagerAdapter {

    String[] title = new String[]{
            "UMUM",
            "DOMISILI",
            "KTP"
    };

    public AdapterEditprofile(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ProfileumumFragment();
                break;
            case 1:
                fragment = new DomisiliFragment();
                break;
            case 2:
                fragment = new KtpFragment();
                break;
                default:
                    fragment = null;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
