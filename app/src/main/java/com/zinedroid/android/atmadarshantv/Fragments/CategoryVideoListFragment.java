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

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.VideoAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
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
public class CategoryVideoListFragment extends BaseFragment implements WebserviceCall.WebServiceCall, VideoAdapter.AddToFavourite {
    Context context;
    RecyclerView.LayoutManager mLayoutManager;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mCategoryVideorecycler;
    RecyclerView.Adapter mCategoryVideoAdapter;
    String cat_id;
    ArrayList<Video> mVideoList;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;

    @SuppressLint("ValidFragment")
    public CategoryVideoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_category_videolist, container, false);
        mainFunction(mFragmentView);
        cat_id = getArguments().getString("cat_id");
        new WebserviceCall(CategoryVideoListFragment.this, AppConstants.Methods.listvideos).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                cat_id);
        return mFragmentView;
    }

    public void mainFunction(View mFragmentView) {
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mCategoryVideorecycler = mFragmentView.findViewById(R.id.category_recycler);
        mCategoryVideorecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCategoryVideorecycler.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.listvideos:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mVideoList = new ArrayList<>();
                        JSONArray mCategoryVideoJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CAT_VIDEO);

                        for (int k = 0; k < mCategoryVideoJsonArray.length(); k++) {
                            JSONObject mVideolonk = mCategoryVideoJsonArray.getJSONObject(k);
                            Video video = new Video();
                            video.setCat_id(mVideolonk.getString(AppConstants.APIKeys.CATEGORY));
                            video.setDiscription(mVideolonk.getString(AppConstants.APIKeys.ID));
                            video.setCat_name(mVideolonk.getString(AppConstants.APIKeys.CATGORY_NAME));
                            video.setViews(mVideolonk.getString(AppConstants.APIKeys.VIEWERS));

                            video.setLink(mVideolonk.getString(AppConstants.APIKeys.VIDEO_LINK).replace( '\'', ' ' ));
                            String videoid = getVideoId(mVideolonk.getString(AppConstants.APIKeys.VIDEO_LINK));
                            video.setVideolink(videoid);
                            String fav_status = mVideolonk.getString(AppConstants.APIKeys.FAV_STATUS);
                            if (fav_status.equalsIgnoreCase("true")) {
                                video.setIsfavourite(true);
                            } else {
                                video.setIsfavourite(false);
                            }
                               /* String videotitle = getTitleQuietly(videoid);
                                Log.d("videtitle",videotitle);*/
                            //video.setVideo_titile(videotitle);
                            mVideoList.add(video);

                        }
                        mCategoryVideoAdapter = new VideoAdapter(CategoryVideoListFragment.this, getActivity(), mVideoList);
                        mCategoryVideorecycler.setAdapter(mCategoryVideoAdapter);
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
    public void addToFavourite(int position, boolean isfavourite) {
        mVideoList.get(position).setIsfavourite(isfavourite);
        mCategoryVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Video List");
        OnMenuIconChange.iconchange(CategoryVideoListFragment.this);
        HomeActivity.fragment = CategoryVideoListFragment.this;
    }
}
