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

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.CategoryAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Category;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class CategoriesFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    RecyclerView.LayoutManager mLayoutManager;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mCategoryrecycler;
    RecyclerView.Adapter mAdapter;
    ArrayList<Category> mCategoryList;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public CategoriesFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_categories, container, false);
        mainFunction(mFragmentView);
        if (isNetworkAvailable()) {
            new WebserviceCall(CategoriesFragment.this, AppConstants.Methods.categorylist).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }
       else {
            Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
        }
        return mFragmentView;
    }

    public void mainFunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        mCategoryrecycler = mFragmentView.findViewById(R.id.category_recycler);
        mCategoryrecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCategoryrecycler.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.categorylist:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mCategoryList = new ArrayList<>();
                        JSONArray mCategoryJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CATGORY_LIST);
                        for (int a = 0; a < mCategoryJsonArray.length(); a++) {
                            Category mCatgory = new Category();
                            mCatgory.setCat_id(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.CATGORY_ID));
                            mCatgory.setCat_name(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.CATGORY_NAME));
                            mCatgory.setCount(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.COUNT));
                            mCategoryList.add(mCatgory);
                        }
                        mAdapter = new CategoryAdapter(CategoriesFragment.this, mCategoryList);
                        mCategoryrecycler.setAdapter(mAdapter);

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
        mOnFragmnetSwitch.loadTitle("Categories");
        OnMenuIconChange.iconchange(CategoriesFragment.this);
        HomeActivity.fragment = CategoriesFragment.this;
    }
}
