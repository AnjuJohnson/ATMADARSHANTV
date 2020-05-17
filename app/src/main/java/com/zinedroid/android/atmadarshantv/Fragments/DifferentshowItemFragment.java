package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.DifferentShowAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class DifferentshowItemFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Video> mVideoList;
    RecyclerView.Adapter mDifferentshowVideoAdapter;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    String mShowName,mShowImage,mShowId,mFavStatus;
    Utility.menuIconChange OnMenuIconChange;
    ImageView ShowimageView;
     MaterialFavoriteButton toolbarFavorite;
    @SuppressLint("ValidFragment")
    public DifferentshowItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_different_episodes, container, false);
        mainfunction(view);
        return view;
    }
    public void mainfunction(View v){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mRecyclerView = v.findViewById(R.id.differentshows_recycler);
        toolbarFavorite = v.findViewById(R.id.favourite);
        ShowimageView= v.findViewById(R.id.img_thumbnail);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Bundle extras = getArguments();
        mVideoList  = (ArrayList<Video>) extras.getSerializable("VIDEOLIST");
        mShowName=extras.getString("SHOWNAME");
        mShowImage=extras.getString("SHOWIMAGE");
        mShowId=extras.getString("SHOWID");
        mFavStatus=extras.getString("FAV_STATUS");
        setSharedPreference("SHOWID",mShowId);
        mDifferentshowVideoAdapter = new DifferentShowAdapter(DifferentshowItemFragment.this, getActivity(), mVideoList);
        mRecyclerView.setAdapter(mDifferentshowVideoAdapter);
        Picasso.with(getActivity())
                .load(mShowImage)
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(ShowimageView);

if(mFavStatus.equalsIgnoreCase("true")){
    toolbarFavorite.setFavorite(true);
}
else {
    toolbarFavorite.setFavorite(false);
}
        toolbarFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                if (favorite) {
                    if(isNetworkAvailable()){
                        buttonView.setFavorite(true);
                        new WebserviceCall(DifferentshowItemFragment.this, AppConstants.Methods.addFavourites).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID), mShowId, "different_shows");
                    }
                    else {
                        Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                    }

                    }
                else {
                    if(isNetworkAvailable()){

                        buttonView.setFavorite(false);
                        Log.d("favourite", "notclickedinside");

                        new WebserviceCall(DifferentshowItemFragment.this,  AppConstants.Methods.removeFavourites).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                mShowId, "different_shows");
                    }
                    else {
                        Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                    }


                }




            }
        });
    }





    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle(mShowName);
        OnMenuIconChange.iconchange(DifferentshowItemFragment.this);
        HomeActivity.fragment = DifferentshowItemFragment.this;
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
     //   Log.e("JsonResponse", mJsonObject.toString());

    }
}