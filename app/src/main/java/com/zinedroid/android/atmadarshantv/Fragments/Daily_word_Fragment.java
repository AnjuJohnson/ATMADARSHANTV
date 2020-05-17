package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cecil Paul on 3/9/18.
 */
@SuppressLint("ValidFragment")
public class Daily_word_Fragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    TextView mMessageTextView, mVerseTextView;
    Context context;
    Utility.menuIconChange OnMenuIconChange;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;

    @SuppressLint("ValidFragment")
    public Daily_word_Fragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        View mFragmentView = inflater.inflate(R.layout.fragment_daily_word, container, false);
        mainFunction(mFragmentView);

        return mFragmentView;

    }

    public void mainFunction(View mFragmentView) {
        mMessageTextView = mFragmentView.findViewById(R.id.message);
        mVerseTextView = mFragmentView.findViewById(R.id.verse);
        OnMenuIconChange = (Utility.menuIconChange) getActivity();

        if (isNetworkAvailable()) {
            new WebserviceCall(Daily_word_Fragment.this, AppConstants.Methods.listNotification
            ).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        } else {
            Toast.makeText(getActivity(), "Connect to the internet", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Daily Word");
        OnMenuIconChange.iconchange(Daily_word_Fragment.this);
        HomeActivity.fragment = Daily_word_Fragment.this;
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {


        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.listNotification:

                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {

                        JSONArray mMessagesJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.MESSAGES);
                        for (int a = 0; a < mMessagesJsonArray.length(); a++) {
                            mMessageTextView.setText(mMessagesJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.WORD));
                            mVerseTextView.setText(mMessagesJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.VERSE));

                        }


                    }

            }

        }
        catch (JSONException l) {
            l.printStackTrace();
        }
        catch (Exception e) {

        }


    }
}
