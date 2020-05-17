package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.RecenlyViewedNewAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.RecentlyViewedAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.SliderPagerAdapter;
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
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mRecyclerView, mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    RecyclerView.Adapter mAdapter, mAdapter2;
    ArrayList<String> alName;
    ArrayList<String> alImage;
    ImageView mVideoThumbnail;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    ArrayList<Video> mDifferentshoww;
    TextView mViewAllRecentlyAdded ;
    LinearLayout mRecentlyviewd;
    public ArrayList<Video> mVideoListVideoArrayList,mdUMMYLIST;
    private YouTubePlayer YPlayer;
    Utility.menuIconChange OnMenuIconChange;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    ArrayList<Differentshows> mBannerArrayList;
    TextView mNoInternetTextView;
LinearLayout mVisibleHomeLayout;
    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        mainFunction(mFragmentView);
        //    addBottomDots(0);


        if (isNetworkAvailable()) {
            mVisibleHomeLayout.setVisibility(View.VISIBLE);
            new WebserviceCall(HomeFragment.this, AppConstants.Methods.recentlyadded).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        } else {

         //   mOnFragmnetSwitch.onFragmentChange(new NoInternetFragment(),"Atmadarshan TV",true);
          //  mNoInternetTextView.setVisibility(View.VISIBLE);
            mVisibleHomeLayout.setVisibility(View.GONE);

         //   mRecentlyviewd.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
           /* Snackbar.make(mRecyclerView2, "Please connect to the Internet and try again!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
        }

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == mBannerArrayList.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 3000);

        mVideoListVideoArrayList = (ArrayList<Video>) Video.findWithQuery(Video.class,
                "Select * from Video");

        if (mVideoListVideoArrayList.size() != 0) {
            mdUMMYLIST=new ArrayList();
            if (mVideoListVideoArrayList.size() > 5) {
                mVideoListVideoArrayList.subList(0, (mVideoListVideoArrayList.size() - 5)).clear();
                for(int i=mVideoListVideoArrayList.size();i>0;i--){
                    Video mVideo=mVideoListVideoArrayList.get(i-1);
                    mdUMMYLIST.add(mVideo);

                }
                mRecentlyviewd.setVisibility(View.VISIBLE);
                mAdapter = new RecenlyViewedNewAdapter(getActivity(), mdUMMYLIST);
                mRecyclerView.setAdapter(mAdapter);



            } else {


                for(int i=mVideoListVideoArrayList.size();i>0;i--){

                    Video mVideo=mVideoListVideoArrayList.get(i-1);

                    mdUMMYLIST.add(mVideo);

                }


                mRecentlyviewd.setVisibility(View.VISIBLE);
                //   mVideoListVideoArrayList.subList(0,3);
                mAdapter = new RecenlyViewedNewAdapter(getActivity(), mdUMMYLIST);
                mRecyclerView.setAdapter(mAdapter);


            }

        }

        return mFragmentView;

    }

    public void mainFunction(View mFragmentView) {
        //mVideoThumbnail = (ImageView) mFragmentView.findViewById(R.id.youtube_layout);
        mRecentlyviewd = mFragmentView.findViewById(R.id.recently_viewd);
        mViewAllRecentlyAdded = mFragmentView.findViewById(R.id.viewallrecentlyadded);
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
     //   mNoInternetTextView=(TextView) mFragmentView.findViewById(R.id.mNoInternetTextView);
        mVisibleHomeLayout=(LinearLayout) mFragmentView.findViewById(R.id.mVisibleHomeLayout);

//for image slider

        vp_slider = mFragmentView. findViewById(R.id.vp_slider);
        ll_dots = mFragmentView. findViewById(R.id.ll_dots);
        slider_image_list = new ArrayList<>();

        slider_image_list.add("http:\\/\\/zinedroid.com\\/athmadarshan\\/uploads\\/shows\\/topic_image1535455849.png");
        slider_image_list.add("http:\\/\\/zinedroid.com\\/athmadarshan\\/uploads\\/shows\\/topic_image1535455868.png");
        slider_image_list.add("http:\\/\\/zinedroid.com\\/athmadarshan\\/uploads\\/shows\\/topic_image1535455849.png");
        slider_image_list.add("http:\\/\\/zinedroid.com\\/athmadarshan\\/uploads\\/shows\\/topic_image1535455868.png");

        mBannerArrayList=new ArrayList<>();
       /* YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize("AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {

                    YPlayer=youTubePlayer;

                    // youTubePlayer.cueVideo("EjvfD61PXAw");
                    YPlayer.loadVideo("EjvfD61PXAw");
                    YPlayer.play();
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

*/
        mViewAllRecentlyAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist", mDifferentshoww);
                BaseFragment baseFragment = new ViewAllFragment(getActivity());
                baseFragment.setArguments(bundle);
                mOnFragmnetSwitch.onFragmentChange(baseFragment, "Recently Added", true);
            }
        });


        alImage = new ArrayList<>(Arrays.asList("dwtiM4KlxEc", "MhIssCOMyeM", "dwtiM4KlxEc"));

        // Calling the RecyclerView
        mRecyclerView = mFragmentView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView2 = mFragmentView.findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);


        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
/*
        mAdapter = new RecentlyViewedAdapter(getActivity(), alImage);
        mRecyclerView.setAdapter(mAdapter);*/


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
        mOnFragmnetSwitch.loadTitle("Atmadarshan TV");
        OnMenuIconChange.iconchange(HomeFragment.this);


      /*  YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize("AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    // youTubePlayer.cueVideo("EjvfD61PXAw");
                    YPlayer=youTubePlayer;
                    YPlayer.loadVideo("EjvfD61PXAw");
                    YPlayer.play();
                }
              *//*  else {
                    youTubePlayer.play();
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                }
*//*
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
*/
        mVideoListVideoArrayList = (ArrayList<Video>) Video.findWithQuery(Video.class,
                "Select * from Video");
        if (mVideoListVideoArrayList.size() != 0) {
            mdUMMYLIST=new ArrayList();
            if (mVideoListVideoArrayList.size() > 5) {
                mVideoListVideoArrayList.subList(0, (mVideoListVideoArrayList.size() - 5)).clear();
                for(int i=mVideoListVideoArrayList.size();i>0;i--){
                    Video mVideo=mVideoListVideoArrayList.get(i-1);
                    mdUMMYLIST.add(mVideo);

                }
                mRecentlyviewd.setVisibility(View.VISIBLE);
                mAdapter = new RecenlyViewedNewAdapter(getActivity(), mdUMMYLIST);
                mRecyclerView.setAdapter(mAdapter);



            } else {

                for(int i=mVideoListVideoArrayList.size();i>0;i--){

                    Video mVideo=mVideoListVideoArrayList.get(i-1);

                    mdUMMYLIST.add(mVideo);

                }
                mRecentlyviewd.setVisibility(View.VISIBLE);
                //   mVideoListVideoArrayList.subList(0,3);
                mAdapter = new RecenlyViewedNewAdapter(getActivity(), mdUMMYLIST);
                mRecyclerView.setAdapter(mAdapter);


            }
            mAdapter.notifyDataSetChanged();

        }
        else {
            mRecentlyviewd.setVisibility(View.GONE);
        }
        // mAdapter.notifyDataSetChanged();
        HomeActivity.fragment = HomeFragment.this;
    }
    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        switch (method) {
            case AppConstants.Methods.recentlyadded:
                try {
                    Log.i("jsonresponse", mJsonObject.toString());
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        //  mRecentlyviewd.setVisibility(View.VISIBLE);
                        mDifferentshoww = new ArrayList<>();
                        JSONObject jsonObject = mJsonObject.getJSONObject(AppConstants.APIKeys.DIFFERENTSHOWS);
                        JSONArray mdifferentshowsJsonArray = jsonObject.getJSONArray(AppConstants.APIKeys.LINK);
                        for (int a = 0; a < mdifferentshowsJsonArray.length(); a++) {
                            Video video = new Video();
                            video.setVideo_titile(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TITLE));
                            video.setViews(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.VIEWERS));
                            String videoid = getVideoId(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.VIDEO_LINKK));
                            video.setIdd(videoid);
                            video.setVideolink(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.VIDEO_LINKK));
                            mDifferentshoww.add(video);
                        }
                        JSONObject jsonObject1 = mJsonObject.getJSONObject(AppConstants.APIKeys.CATEGORY);
                        JSONArray mCatogoryJsonArray = jsonObject1.getJSONArray(AppConstants.APIKeys.LINK);
                        for (int b = 0; b < mCatogoryJsonArray.length(); b++) {
                            Video video = new Video();
                            video.setVideo_titile(mCatogoryJsonArray.getJSONObject(b).getString(AppConstants.APIKeys.VID_NAME));
                            video.setViews(mCatogoryJsonArray.getJSONObject(b).getString(AppConstants.APIKeys.VIEWERS));
                            String videoid = getVideoId(mCatogoryJsonArray.getJSONObject(b).getString(AppConstants.APIKeys.VIDEO_LINK));
                            video.setIdd(videoid);
                            video.setVideolink(mCatogoryJsonArray.getJSONObject(b).getString(AppConstants.APIKeys.VIDEO_LINK));
                            mDifferentshoww.add(video);
                        }
                        mAdapter2 = new RecentlyViewedAdapter(getActivity(), mDifferentshoww);
                        mRecyclerView2.setAdapter(mAdapter2);
                        if (isNetworkAvailable()) {
                            new WebserviceCall(HomeFragment.this, AppConstants.Methods.loadbanners).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
                        } else {
                            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case AppConstants.Methods.loadbanners:
                try {
                    Log.i("jsonresponse", mJsonObject.toString());
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        JSONArray mbannerJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.BANNER);
                        for (int a = 0; a < mbannerJsonArray.length(); a++) {
                            Differentshows differentshows = new Differentshows();
                            differentshows.setImage(mbannerJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.IMAGE));
                            mBannerArrayList.add(differentshows);
                        }
                        Log.d("66666", String.valueOf(mBannerArrayList.size()));

                        sliderPagerAdapter = new SliderPagerAdapter(HomeFragment.this,getActivity(), mBannerArrayList);
                        vp_slider.setAdapter(sliderPagerAdapter);

                        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                addBottomDots(position);
                            }
                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                        addBottomDots(0);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }
    }
    private void addBottomDots(int currentPage) {
        dots = new TextView[mBannerArrayList.size()];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            try {
                dots[i] = new TextView(getActivity());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(Color.parseColor("#000000"));
                ll_dots.addView(dots[i]);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        try {
            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
