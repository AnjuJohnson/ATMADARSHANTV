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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.AboutUsAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.LiveWebserviceCall;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("ValidFragment")
public class AboutUsFragment extends BaseFragment implements LiveWebserviceCall.Livewebservicecall,WebserviceCall.WebServiceCall {
    Context context;
    RecyclerView.LayoutManager mLayoutManager;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mLiveVideoRecyclerView;
    RecyclerView.Adapter mAdapter;

    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    TextView mNoVideoTextView;
    ArrayList<Video> mLiveVideoArrayList;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public AboutUsFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_recycler, container, false);
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mainFunction(mFragmentView);
      /*  if (isNetworkAvailable()) {
            new LiveWebserviceCall(AboutUsFragment.this, AppConstants.Methods.liveVideo).execute();
        } else {
            Snackbar.make(mFragmentView, "Please connect to the Internet and try again!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }*/



      //loading intro for now

        if (isNetworkAvailable()) {
            new WebserviceCall(AboutUsFragment.this, AppConstants.Methods.listIntroVideos).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }else {
            Toast.makeText(getActivity(), "Connect to the internet", Toast.LENGTH_LONG).show();
        }



       /* LiveVideo liveVideo = new LiveVideo();
        liveVideo.setTitle("About Atmadarshan TV | Atmadarshan TV New Intro");
        liveVideo.setDescription("About Atmadarshan TV | Atmadarshan TV New Intro");
        liveVideo.setVideoId("cLteem9A9fE");
        liveVideo.setImageurl("https://img.youtube.com/vi/cLteem9A9fE/0.jpg");
        mLiveVideoArrayList.add(liveVideo);
        mAdapter = new AboutUsAdapter(AboutUsFragment.this,getActivity(), mLiveVideoArrayList);
        mLiveVideoRecyclerView.setAdapter(mAdapter);*/
///

        return mFragmentView;
    }

    public void mainFunction(View view) {
        mLiveVideoRecyclerView = view.findViewById(R.id.differentshows_recycler);
        mNoVideoTextView = view.findViewById(R.id.no_videos);
        mLiveVideoRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLiveVideoRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("About Us");
        OnMenuIconChange.iconchange(AboutUsFragment.this);
        HomeActivity.fragment = AboutUsFragment.this;
    }

    @Override
    public void onLivewebservicecall(JSONObject mJsonObject, int method) {
    /*    Log.i("jsondata", mJsonObject.toString());
        switch (method) {
            case AppConstants.Methods.liveVideo:
                try {
                    if (mJsonObject.getJSONObject(AppConstants.APIKeys.PAGE_INFO).getString(AppConstants.APIKeys.TOTAL_RESULT).equalsIgnoreCase("0")) {
                        mNoVideoTextView.setVisibility(View.VISIBLE);
                        mNoVideoTextView.setText("No Live Video");
                        mLiveVideoRecyclerView.setVisibility(View.GONE);
                    } else {
                        mNoVideoTextView.setVisibility(View.GONE);
                        mLiveVideoRecyclerView.setVisibility(View.VISIBLE);
                        JSONArray mLiveVideoJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.ITEMS);
                        mLiveVideoArrayList = new ArrayList<>();
                        for (int a = 0; a < mLiveVideoJsonArray.length(); a++) {
                            LiveVideo liveVideo = new LiveVideo();
                            JSONObject itemJsonObject = mLiveVideoJsonArray.getJSONObject(a);
                            liveVideo.setVideoId(itemJsonObject.getJSONObject(AppConstants.APIKeys.ID).getString(AppConstants.APIKeys.VIDEOID));
                            liveVideo.setImageurl(itemJsonObject.getJSONObject(AppConstants.APIKeys.SNIPPET).
                                    getJSONObject(AppConstants.APIKeys.THUMBNAILS).getJSONObject(AppConstants.APIKeys.MEDIUM).
                                    getString(AppConstants.APIKeys.URL));
                            liveVideo.setTitle(itemJsonObject.getJSONObject(AppConstants.APIKeys.SNIPPET).getString(AppConstants.APIKeys.TITLE));
                            liveVideo.setDescription(itemJsonObject.getJSONObject(AppConstants.APIKeys.SNIPPET).getString(AppConstants.APIKeys.DESCRIPTION));
                            mLiveVideoArrayList.add(liveVideo);
                        }
                        mAdapter = new AboutUsAdapter(AboutUsFragment.this,getActivity(), mLiveVideoArrayList);
                        mLiveVideoRecyclerView.setAdapter(mAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
*/
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.listIntroVideos:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mLiveVideoArrayList = new ArrayList<>();
                        JSONArray mCategoryJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.INTROVIDEO);
                        for (int a = 0; a < mCategoryJsonArray.length(); a++) {
                            JSONObject mVideolonk = mCategoryJsonArray.getJSONObject(a);
                            Video videoo = new Video();
                            videoo.setDiscription(mVideolonk.getString(AppConstants.APIKeys.DISCRIPTION));
                            videoo.setViews(mVideolonk.getString(AppConstants.APIKeys.VIEWS));
                            videoo.setVideo_titile(mVideolonk.getString(AppConstants.APIKeys.TITLE));
                            videoo.setLink(mVideolonk.getString(AppConstants.APIKeys.LINK).replace( '\'', ' ' ));
                            String videoid = getVideoId(mVideolonk.getString(AppConstants.APIKeys.LINK));
                            videoo.setVideolink(videoid);
                            mLiveVideoArrayList.add(videoo);

                        }

                        mAdapter = new AboutUsAdapter(AboutUsFragment.this,getActivity(), mLiveVideoArrayList);
                        mLiveVideoRecyclerView.setAdapter(mAdapter);


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
}