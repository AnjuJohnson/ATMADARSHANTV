package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.utils.Config;

/**
 * Created by Anjumol Johnson on 1/11/18.
 */
public class NoInternetFragment  extends BaseFragment {
    WebView mWebview;
    TextView mNoInternetTextView;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;

    @SuppressLint("ValidFragment")
    public NoInternetFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_no_internet, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mNoInternetTextView= mFragmentView.findViewById(R.id.mNoInternetTextView);

    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Atmadarshan TV");
        OnMenuIconChange.iconchange(NoInternetFragment.this);
        HomeActivity.fragment = NoInternetFragment.this;
    }
}