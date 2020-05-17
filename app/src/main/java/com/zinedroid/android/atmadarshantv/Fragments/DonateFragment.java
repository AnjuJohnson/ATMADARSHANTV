package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;

@SuppressLint("ValidFragment")
public class DonateFragment extends BaseFragment {
    Context context;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;

    @SuppressLint("ValidFragment")
    public DonateFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_donate, container, false);
        // mainFunction(mFragmentView);
        return mFragmentView;

    }
   /* @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Payment");
        HomeActivity.fragment = CategoriesFragment.this;
    }*/
}
