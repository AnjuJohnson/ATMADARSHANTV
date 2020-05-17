package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;

/**
 * Created by Anjumol Johnson on 1/11/18.
 */
public class FacebookLikeFragment  extends BaseFragment {
   WebView mWebview;
   TextView mNoInternetTextView;

    @SuppressLint("ValidFragment")
    public FacebookLikeFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_facebook_like, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView){

        mNoInternetTextView= mFragmentView.findViewById(R.id.mNoInternetTextView);
        mWebview= mFragmentView.findViewById(R.id.mWebview);

      if(isNetworkAvailable()) {

            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.loadUrl("https://www.facebook.com/AtmadarshanTv/");
            mWebview.setWebViewClient(new WebViewClient());

        }
        else {
          mWebview.setVisibility(View.GONE);
          mNoInternetTextView.setVisibility(View.VISIBLE);
      }




    }
}