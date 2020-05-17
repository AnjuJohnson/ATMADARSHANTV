package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.MyRequestAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class MyPrayRequestFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    RecyclerView mMyRequestRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Video> mVideoList;
    RecyclerView.Adapter mPrayerRequestAdapter;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    BaseFragment baseFragment;
    TextView mNoPrayerREquestsTextView;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    @SuppressLint("ValidFragment")
    Utility.menuIconChange OnMenuIconChange;
    public MyPrayRequestFragment(Context context, BaseFragment baseFragmentt) {
        // Required empty public constructor
        this.baseFragment = baseFragmentt;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mainfunction(view);
        return view;
    }

    public void mainfunction(View view) {
        onFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mNoPrayerREquestsTextView = view.findViewById(R.id.no_videos);
        mMyRequestRecyclerView = view.findViewById(R.id.differentshows_recycler);
        mMyRequestRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mMyRequestRecyclerView.setLayoutManager(mLayoutManager);

        if (isNetworkAvailable()) {
            new WebserviceCall(MyPrayRequestFragment.this, AppConstants.Methods.listOwnPrayRequest).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        } else {
            Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            switch (method) {
                case AppConstants.Methods.listOwnPrayRequest:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mPrayerRequestArrayList = new ArrayList<>();
                        JSONArray mPrayerrequestJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.REQUEST);
                        for (int a = 0; a < mPrayerrequestJsonArray.length(); a++) {

                            PrayerRequest mPrayerRequest = new PrayerRequest();
                            mPrayerRequest.setId(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSET_ID));
                            mPrayerRequest.setRequest_title(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSETED_TITLE));
                            mPrayerRequest.setRequest_discription(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSET_PRAY_DISCRIPTION));
                            mPrayerRequestArrayList.add(mPrayerRequest);
                        }
                        mPrayerRequestAdapter = new MyRequestAdapter(MyPrayRequestFragment.this,mPrayerRequestArrayList);
                        mMyRequestRecyclerView.setAdapter(mPrayerRequestAdapter);


                    }
                    else {
                        mMyRequestRecyclerView.setVisibility(View.GONE);
                        mNoPrayerREquestsTextView.setVisibility(View.VISIBLE);
                        mNoPrayerREquestsTextView.setText("No Requests added");
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception d) {
            d.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        onFragmnetSwitch.loadTitle("My Prayer Requests");
        OnMenuIconChange.iconchange(MyPrayRequestFragment.this);
        HomeActivity.fragment = MyPrayRequestFragment.this;
    }
}