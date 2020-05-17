package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.DifferentShowAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.DifferentShowsNewAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cecil Paul on 27/8/18.
 */
@SuppressLint("ValidFragment")
public class DifferentShowsNewFragment extends BaseFragment implements WebserviceCall.WebServiceCall,DifferentShowAdapter.AddtofavouritesShows {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Context context;
    ArrayList<Video> mVideoList;
    RecyclerView.Adapter mDifferentshowVideoAdapter;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    ArrayList<Video> videolinks;
    DifferentShowsNewAdapter mDifferentShowsNewAdapter;
    ArrayList<Differentshows> mDifferentshoww;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public DifferentShowsNewFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mainfunction(view);
        if (isNetworkAvailable()) {
            new WebserviceCall(DifferentShowsNewFragment.this, AppConstants.Methods.differentshows).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        } else {
            Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
        }
        String message="kkkkk";
        Log.d("aaaaaa",message);

        return view;
    }
    public void mainfunction(View v){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mRecyclerView = v.findViewById(R.id.differentshows_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Shows");
        OnMenuIconChange.iconchange(DifferentShowsNewFragment.this);
        HomeActivity.fragment = DifferentShowsNewFragment.this;
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.differentshows:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mDifferentshoww = new ArrayList<>();
                        JSONArray mdifferentshowsJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.DIFFERENTSHOWS);

                        for (int a = 0; a < mdifferentshowsJsonArray.length(); a++) {
                            videolinks = new ArrayList<>();
                            Differentshows mDifferentshow = new Differentshows();
                            mDifferentshow.setShowid(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.SHOW_ID));
                            mDifferentshow.setShowname(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.SHOW_NAME));
                            mDifferentshow.setImage(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.IMAGE));

                            String fav_status = mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.FAV_STATUS);

                            if (fav_status.equalsIgnoreCase("true")) {
                                mDifferentshow.setIsfavourite(true);
                            } else {
                                mDifferentshow.setIsfavourite(false);
                            }



                            JSONObject mVideolinkObjects = mdifferentshowsJsonArray.getJSONObject(a);
                            JSONArray mVideoLinkArray = mVideolinkObjects.getJSONArray(AppConstants.APIKeys.LINK);
                            for (int k = 0; k < mVideoLinkArray.length(); k++) {
                                JSONObject mLinks = mVideoLinkArray.getJSONObject(k);
                                Video video = new Video();
                                video.setCat_id(mLinks.getString(AppConstants.APIKeys.ID));
                                video.setVideo_titile(mLinks.getString(AppConstants.APIKeys.TITLE));
                                video.setVideolink(mLinks.getString(AppConstants.APIKeys.VIDEO_LINKK));
                                video.setDiscription(mLinks.getString(AppConstants.APIKeys.LINK_DISCRIPTION));
                                String videoid= getVideoId(mLinks.getString(AppConstants.APIKeys.VIDEO_LINKK));
                                video.setViews(mLinks.getString(AppConstants.APIKeys.VIEWERS));
                               // String fav_status = mLinks.getString(AppConstants.APIKeys.FAV_STATUS);
                               /* if (fav_status.equalsIgnoreCase("true")) {
                                    video.setIsfavourite(true);
                                } else {
                                    video.setIsfavourite(false);
                                }*/
                                videolinks.add(video);
                            }
                            mDifferentshow.setShowdetaillist(videolinks);
                            mDifferentshoww.add(mDifferentshow);
                        }
                        mDifferentShowsNewAdapter = new DifferentShowsNewAdapter(DifferentShowsNewFragment.this, getActivity(),mDifferentshoww);
                        mRecyclerView.setAdapter(mDifferentShowsNewAdapter);
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getVideoId(@NonNull String videoUrl) {
        String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }


    @Override
    public void addtofavouritesAshows(int position, boolean isfavourite) {
        mDifferentshoww.get(position).setIsfavourite(isfavourite);
        mDifferentShowsNewAdapter.notifyDataSetChanged();
    }
}