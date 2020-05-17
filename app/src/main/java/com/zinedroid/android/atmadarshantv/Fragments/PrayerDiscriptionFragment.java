package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.models.Testimony;
import com.zinedroid.android.atmadarshantv.utils.Config;

/**
 * Created by Cecil Paul on 29/8/18.
 */
@SuppressLint("ValidFragment")
public class PrayerDiscriptionFragment extends BaseFragment {
    Context context;
    private TextView mCreateRequestTextView, mPrayerTitle, mPrayerDiscription;
    private TextView mTitleEditeEditText, mDiscriptionEditText;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    String page;
    PrayerRequest mPrayerDetails;
    Testimony mTestimonyDetails;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public PrayerDiscriptionFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.item_prayer_details, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }

    public void mainFunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();

        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        Bundle extras = getArguments();

        page = extras.getString("PAGE");
        mTitleEditeEditText = mFragmentView.findViewById(R.id.title);
        mDiscriptionEditText = mFragmentView.findViewById(R.id.request);
        mCreateRequestTextView = mFragmentView.findViewById(R.id.createrequest);
        mPrayerTitle = mFragmentView.findViewById(R.id.prayertitle);
        mPrayerDiscription = mFragmentView.findViewById(R.id.prayerrdiscription);

        if (page.equalsIgnoreCase("1")) {
            mPrayerDetails = (PrayerRequest) extras.getSerializable("PRAYERDETAILS");
            mTitleEditeEditText.setText(mPrayerDetails.getRequest_title());
            mDiscriptionEditText.setText(mPrayerDetails.getRequest_discription());
            mCreateRequestTextView.setText("Pray " + mPrayerDetails.getReq_count());
        } else {

            mTestimonyDetails = (Testimony) extras.getSerializable("PRAYERDETAILS");
            mTitleEditeEditText.setText(mTestimonyDetails.getTitle());
            mPrayerTitle.setText("Testimony Title");
            mPrayerDiscription.setText("Testimony Discription");
            mDiscriptionEditText.setText(mTestimonyDetails.getDiscription());
            mCreateRequestTextView.setVisibility(View.GONE);

        }



    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Prayer");
        OnMenuIconChange.iconchange(PrayerDiscriptionFragment.this);
        HomeActivity.fragment = PrayerDiscriptionFragment.this;
    }



}