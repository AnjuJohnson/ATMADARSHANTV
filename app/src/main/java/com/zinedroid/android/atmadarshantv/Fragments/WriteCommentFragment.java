package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cecil Paul on 29/8/18.
 */
@SuppressLint("ValidFragment")
public class WriteCommentFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    private TextView mCreateRequestTextView,mPrayerTitle,mPrayerDiscription;
    private EditText mTitleEditeEditText,mDiscriptionEditText;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String testimony_id;
    @SuppressLint("ValidFragment")
    public WriteCommentFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.write_comment, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }
    public void mainFunction(View mFragmentView){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        mDiscriptionEditText = mFragmentView. findViewById(R.id.request);
        mCreateRequestTextView = mFragmentView. findViewById(R.id.createrequest);
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        Bundle extras = getArguments();

        testimony_id = extras.getString("testimony_id");

        mCreateRequestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mDiscriptionEditText.getText().toString().equalsIgnoreCase("")){
                    if (isNetworkAvailable()) {
                        new WebserviceCall(WriteCommentFragment.this, AppConstants.Methods.addcomment).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                testimony_id,mDiscriptionEditText.getText().toString().trim());
                    } else {
                        Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getActivity(),"Please write any comments!",Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
              /*  Snackbar.make(mDiscriptionEditText, "Your Profile Updated Successfully!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());

                dlgAlert.setMessage("Successfully submitted");
                //       dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // getActivity().onBackPressed();
                                BaseFragment baseFragment=null;
                                mOnFragmnetSwitch.onFragmentChange(new PrayerRequestFragment(getActivity(),baseFragment),"Prayer Requests and Testimonies",true);



                            }
                        });
                dlgAlert.create().show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Write Comment");
        OnMenuIconChange.iconchange(WriteCommentFragment.this);
        HomeActivity.fragment = WriteCommentFragment.this;
    }
}
