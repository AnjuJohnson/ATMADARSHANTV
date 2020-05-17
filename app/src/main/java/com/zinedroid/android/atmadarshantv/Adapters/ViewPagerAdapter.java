package com.zinedroid.android.atmadarshantv.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.zinedroid.android.atmadarshantv.Fragments.DifferentshowItemFragment;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.models.Video;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Differentshows> mDifferentshowList;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<Differentshows> mDifferentshowList) {
        super(fm);
        this.mDifferentshowList = mDifferentshowList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position<mDifferentshowList.size()){
            Differentshows mDifferent=mDifferentshowList.get(position);
           // Video video=new Video();
            ArrayList<Video> mVideo=mDifferent.getShowdetaillist();
            fragment = new DifferentshowItemFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mDifferentshowList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if(position<mDifferentshowList.size()){
            Log.d("position", String.valueOf(position));
            Differentshows mDifferent=mDifferentshowList.get(position);
            title=mDifferent.getShowname();
        }


        return title;
    }
}
