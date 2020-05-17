package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;

@SuppressLint("ValidFragment")
public class SettingsFragment  extends BaseFragment {
    Context context;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;

    @SuppressLint("ValidFragment")
    public SettingsFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
         mainFunction(mFragmentView);
        if (isNetworkAvailable()) {
            new WebserviceCall(SettingsFragment.this, AppConstants.Methods.favouritelist).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }
        else {
            Snackbar.make(mFragmentView, "Please connect to the Internet and try again!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }


        return mFragmentView;

    }
    public void mainFunction(View view){



    }
    /*@Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Payment");
        HomeActivity.fragment = LiveRadioFragment.this;
    }*/
}
