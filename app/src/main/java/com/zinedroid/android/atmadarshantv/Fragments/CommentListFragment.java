package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.CommentAdapter;
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

/**
 * Created by Cecil Paul on 29/8/18.
 */
@SuppressLint("ValidFragment")
public class CommentListFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    RecyclerView mMyRequestRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Video> mVideoList;
    RecyclerView.Adapter mPrayerRequestAdapter;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    BaseFragment baseFragment;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    String testimony_id;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")

    public CommentListFragment() {
        // Required empty public constructor

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
        mMyRequestRecyclerView = view.findViewById(R.id.differentshows_recycler);
        mMyRequestRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mMyRequestRecyclerView.setLayoutManager(mLayoutManager);
        Bundle extras = getArguments();

        testimony_id = extras.getString("testimony_id");

        if (isNetworkAvailable()) {
            new WebserviceCall(CommentListFragment.this, AppConstants.Methods.commentlist).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                    testimony_id);
        } else {
            Snackbar.make(view, "Please connect to the Internet and try again!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            switch (method) {
                case AppConstants.Methods.commentlist:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mPrayerRequestArrayList = new ArrayList<>();
                        JSONArray mPrayerrequestJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.COMMENT);
                        for (int a = 0; a < mPrayerrequestJsonArray.length(); a++) {

                            PrayerRequest mPrayerRequest = new PrayerRequest();
                            mPrayerRequest.setId(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ID));
                            mPrayerRequest.setRequuest_user_name(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.USERNAME));
                            mPrayerRequest.setRequest_discription(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.COMMENT));
                            mPrayerRequestArrayList.add(mPrayerRequest);
                        }
                        mPrayerRequestAdapter = new CommentAdapter(CommentListFragment.this,mPrayerRequestArrayList);
                        mMyRequestRecyclerView.setAdapter(mPrayerRequestAdapter);


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
        onFragmnetSwitch.loadTitle("Comments");
        OnMenuIconChange.iconchange(CommentListFragment.this);
        HomeActivity.fragment = CommentListFragment.this;
    }
}