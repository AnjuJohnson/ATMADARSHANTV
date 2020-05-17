package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.utils.CircleTransform;
import com.zinedroid.android.atmadarshantv.utils.Config;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class MyProfileFragment  extends BaseFragment {
    Context context;
   TextView mUsernameTextView,mEmailTextView,mPhoneNumberTextView,mAddressTextView,mPlaceTextView;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    CircleImageView mProrfileCircleImageView;
    String urlProfileImg;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public MyProfileFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_myprofile, container, false);
         mainFunction(mFragmentView);
        return mFragmentView;

    }
    public void mainFunction(View mView){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mUsernameTextView= mView.findViewById(R.id.username);
        mEmailTextView= mView.findViewById(R.id.email);
        mPhoneNumberTextView= mView.findViewById(R.id.Phonenumber);
        mAddressTextView= mView.findViewById(R.id.address);
        mPlaceTextView= mView.findViewById(R.id.place);
        mProrfileCircleImageView= mView.findViewById(R.id.mProrfileCircleImageView);
        if (getSharedPreference(getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE)) != "DEFAULT") {
            urlProfileImg = "http://graph.facebook.com/"
                    + getSharedPreference(AppConstants.SharedKey.FB_ID) + "/picture?type=large";

        } else if (getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE) != ("DEFAULT")) {
            urlProfileImg = getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE);
            Log.d("kkkk", urlProfileImg);
        }
        loadimage(urlProfileImg);


        if((!getSharedPreference(AppConstants.SharedKey.PLACE).equalsIgnoreCase("DEFAULT"))&&
                (!getSharedPreference(AppConstants.SharedKey.PLACE).equalsIgnoreCase("null"))){
            mPlaceTextView.setText( getSharedPreference(AppConstants.SharedKey.PLACE));
        }
        else {
            mPlaceTextView.setText("None");
        }


        if((!getSharedPreference(AppConstants.SharedKey.ADDRESS).equalsIgnoreCase("DEFAULT"))&&
                (!getSharedPreference(AppConstants.SharedKey.ADDRESS).equalsIgnoreCase("null"))){
            mAddressTextView.setText( getSharedPreference(AppConstants.SharedKey.ADDRESS));
        }
        else {
            mAddressTextView.setText("None");
        }
        if((!getSharedPreference(AppConstants.SharedKey.PHONE_NUMBER).equalsIgnoreCase("DEFAULT"))&&
                (!getSharedPreference(AppConstants.SharedKey.PHONE_NUMBER).equalsIgnoreCase("null"))){
            mPhoneNumberTextView.setText(getSharedPreference(AppConstants.SharedKey.PHONE_NUMBER));
        }
        else {
            mPhoneNumberTextView.setText("None");
        }

        mUsernameTextView.setText( getSharedPreference(AppConstants.SharedKey.USER_NAME));

        if((!getSharedPreference(AppConstants.SharedKey.USER_EMAIL).equalsIgnoreCase("DEFAULT"))&&
                (!getSharedPreference(AppConstants.SharedKey.USER_EMAIL).equalsIgnoreCase("null"))){
            mEmailTextView.setText(getSharedPreference(AppConstants.SharedKey.USER_EMAIL));
        }
        else {
            mEmailTextView.setText("None");
        }


       // mEmailTextView.setText( getSharedPreference(AppConstants.SharedKey.USER_EMAIL));


     //   Log.d("adddress",getSharedPreference(AppConstants.SharedKey.ADDRESS));

    }
    // Loading profile image
    public void loadimage(String urlProfileImg) {
        Glide.with(this).load(urlProfileImg)
                .placeholder(R.drawable.noimage)
                .crossFade()
                .thumbnail(0.5f)
                .dontAnimate()
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mProrfileCircleImageView);

    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("My Profile");
        OnMenuIconChange.iconchange(MyProfileFragment.this);
        HomeActivity.fragment = MyProfileFragment.this;
    }
}
