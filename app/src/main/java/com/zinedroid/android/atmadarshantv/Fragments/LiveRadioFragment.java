package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.ChanneListAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Category;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class LiveRadioFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;
        ArrayList<Video> mVideoList;
        RecyclerView.Adapter mChannelListAdapter;
        ArrayList<Category> mChannelList;
        Config.OnFragmnetSwitch mOnFragmnetSwitch;
        Context context;
        Utility.menuIconChange OnMenuIconChange;
public LiveRadioFragment(Context context) {
        this.context = context;
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mainfunction(view);
        if (isNetworkAvailable()) {
        new WebserviceCall(LiveRadioFragment.this, AppConstants.Methods.listchannel).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }
        else {
                Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
        }
        return view;
        }
public void mainfunction(View v){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mRecyclerView = v.findViewById(R.id.differentshows_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        }

@Override
public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
                Log.e("JsonResponse", mJsonObject.toString());
        switch (method) {
        case AppConstants.Methods.listchannel:
        if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
        mChannelList = new ArrayList<>();
        JSONArray mCategoryJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CHANNEL);
        for (int a = 0; a < mCategoryJsonArray.length(); a++) {
        Category mCatgory = new Category();
        mCatgory.setCat_id(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ID));
        mCatgory.setCat_name(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TITLE));
        mCatgory.setLink(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.LINK));
        mCatgory.setCount(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ICON));
        mChannelList.add(mCatgory);
        }
        mChannelListAdapter = new ChanneListAdapter(LiveRadioFragment.this, mChannelList);
        mRecyclerView.setAdapter(mChannelListAdapter);
        }
        }
        } catch (JSONException e) {
        e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();
        }

        }
@Override
public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Channel List");
        OnMenuIconChange.iconchange(LiveRadioFragment.this);
        HomeActivity.fragment = LiveRadioFragment.this;
        }


}
