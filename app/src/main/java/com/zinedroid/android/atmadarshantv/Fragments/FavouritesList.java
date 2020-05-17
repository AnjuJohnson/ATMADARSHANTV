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

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.FavouriteShowAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.FavouritesAdapter;
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

@SuppressLint("ValidFragment")
public class FavouritesList extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter2;
    ArrayList<Video> mFavouritesList;
    ArrayList<Differentshows> mFavouriteshows;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    RecyclerView mFavouriteRecycler;
    RecyclerView mFavouriteShowRecycler;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    TextView mNoVideosTextView,mShowstitleTextView,mVideoTextView;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public FavouritesList(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.favourites_recycler, container, false);
         mainFunction(mFragmentView);
        if (isNetworkAvailable()) {
            new WebserviceCall(FavouritesList.this, AppConstants.Methods.favouritelist).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }
        else {
            Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
        }
        return mFragmentView;

    }
    public void mainFunction(View view){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mFavouriteRecycler = view.findViewById(R.id.differentshows_recycler);
        mFavouriteShowRecycler = view.findViewById(R.id.fav_showrecycler);
        mNoVideosTextView = view.findViewById(R.id.mNoVideosTextView);
        mFavouriteShowRecycler.setHasFixedSize(true);
        mFavouriteRecycler.setHasFixedSize(true);
        mShowstitleTextView = view.findViewById(R.id.mShowstitleTextView);
        mVideoTextView = view.findViewById(R.id.mVideoTextView);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mFavouriteRecycler.setLayoutManager(mLayoutManager);
        mFavouriteShowRecycler.setLayoutManager(mLayoutManager2);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.favouritelist:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {

                        mFavouritesList = new ArrayList<>();
                        mFavouriteshows = new ArrayList<>();
                        try {
                            JSONArray mCategoryJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.CATEGORY);
                            mVideoTextView.setVisibility(View.VISIBLE);
                            for (int a = 0; a < mCategoryJsonArray.length(); a++) {
                                Video mFavourite = new Video();
                                mFavourite.setCat_id(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ID));
                                mFavourite.setVideo_titile(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TITLE));
                             //   mFavourite.setVideolink(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.LINK));
                                mFavourite.setDiscription(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.DESCRIPTION));
                                String videoid = getVideoId(mCategoryJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.LINK));
                                mFavourite.setVideolink(videoid);
                                mFavouritesList.add(mFavourite);
                            }
                        } catch (JSONException e) {
                            Log.d("error category","error");
                            mVideoTextView.setVisibility(View.GONE);
                            mFavouriteRecycler.setVisibility(View.GONE);

                            e.printStackTrace();
                        }

                        try {
                            JSONArray mShowsJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.SHOWS);
                            mShowstitleTextView.setVisibility(View.VISIBLE);
                            for (int a = 0; a < mShowsJsonArray.length(); a++) {
                                Differentshows differentshows=new Differentshows();
                                differentshows.setShowid(mShowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.VIDEO_ID));
                                differentshows.setImage(mShowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.IMAGE));
                                differentshows.setShowname(mShowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TITLE));
                                differentshows.setDiscription(mShowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.DISCRIPTION));
                                mFavouriteshows.add(differentshows);
                                Log.d("show id",mShowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ID));
                            }
                        }
                        catch (JSONException e) {
                            Log.d("error show","error");
                            mShowstitleTextView.setVisibility(View.GONE);
                            mFavouriteShowRecycler.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        mAdapter2 = new FavouriteShowAdapter(FavouritesList.this,getActivity(), mFavouriteshows);
                        mFavouriteShowRecycler.setAdapter(mAdapter2);

                        mAdapter = new FavouritesAdapter(FavouritesList.this,getActivity(), mFavouritesList);
                        mFavouriteRecycler.setAdapter(mAdapter);

                    }
                    else {

                        mNoVideosTextView.setVisibility(View.VISIBLE);
                        mVideoTextView.setVisibility(View.GONE);
                        mShowstitleTextView.setVisibility(View.GONE);

                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
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
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Favourites");
        OnMenuIconChange.iconchange(FavouritesList.this);
        HomeActivity.fragment = FavouritesList.this;
    }
}
