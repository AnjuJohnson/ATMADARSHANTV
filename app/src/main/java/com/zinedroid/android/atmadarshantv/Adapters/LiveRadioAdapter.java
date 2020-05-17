package com.zinedroid.android.atmadarshantv.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zinedroid.android.atmadarshantv.Fragments.ChannelListFragment;

public class LiveRadioAdapter extends FragmentPagerAdapter {

    public LiveRadioAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new ChannelListFragment();
        }
        else if (position == 1)
        {
            fragment = new ChannelListFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Channels";
        }
        else if (position == 1)
        {
            title = "Recommended";
        }

        return title;
    }
}
