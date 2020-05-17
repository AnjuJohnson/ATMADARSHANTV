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

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.ViewAllAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Category;
import com.zinedroid.android.atmadarshantv.models.LiveVideo;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ViewAllFragment  extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    RecyclerView.LayoutManager mLayoutManager;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mLiveVideoRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<Category> mCategoryList;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    TextView mNoVideoTextView;
    ArrayList<LiveVideo> mLiveVideoArrayList;
    RecyclerView mRecyclerView, mRecyclerView2;
    Utility.menuIconChange OnMenuIconChange;
 //   RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
 //   RecyclerView.Adapter mAdapter, mAdapter2;
    @SuppressLint("ValidFragment")
    public ViewAllFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_recycler, container, false);
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();

        mainFunction(mFragmentView);
        return mFragmentView;
    }
public void mainFunction(View mFragmentView){
    ArrayList<Video> videoArrayList = (ArrayList<Video>)getArguments().getSerializable("videolist");
    OnMenuIconChange = (Utility.menuIconChange) getActivity();

    mRecyclerView2 = mFragmentView.findViewById(R.id.differentshows_recycler);
    mRecyclerView2.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView2.setLayoutManager(mLayoutManager);
    mAdapter = new ViewAllAdapter(getActivity(), videoArrayList);
    mRecyclerView2.setAdapter(mAdapter);
}
    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Videos");
        OnMenuIconChange.iconchange(ViewAllFragment.this);
        HomeActivity.fragment = ViewAllFragment.this;
    }
}