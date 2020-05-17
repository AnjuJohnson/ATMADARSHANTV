package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

@SuppressLint("ValidFragment")
public class CreateRequestFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
   private TextView mCreateRequestTextView,mPrayerTitle,mPrayerDiscription;
   private EditText mTitleEditeEditText,mDiscriptionEditText;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
   String page;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public CreateRequestFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.create_request, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }
    public void mainFunction(View mFragmentView){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mTitleEditeEditText = mFragmentView. findViewById(R.id.title);
        mDiscriptionEditText = mFragmentView. findViewById(R.id.request);
        mCreateRequestTextView = mFragmentView. findViewById(R.id.createrequest);
        mPrayerTitle = mFragmentView. findViewById(R.id.prayertitle);
        mPrayerDiscription = mFragmentView. findViewById(R.id.prayerrdiscription);

        page = getArguments().getString("PAGE");
        Log.d("PAGE",page);
        if(page.equalsIgnoreCase("1")){
            mTitleEditeEditText.setHint("Enter your Prayer Title");
            mDiscriptionEditText.setHint("Enter your Prayer ");
            mPrayerTitle.setText("Prayer Title");
            mPrayerDiscription.setText("Prayer Description");
        }
        else {
            mTitleEditeEditText.setHint("Enter your Testimony Title");
            mDiscriptionEditText.setHint("Enter your Testimony ");
            mPrayerDiscription.setText("Testimony Description");
            mPrayerTitle.setText("Testimony Title");
        }

        mCreateRequestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page.equalsIgnoreCase("1")) {

                    if(!mTitleEditeEditText.getText().toString().trim().equalsIgnoreCase("")){

                        if(!mDiscriptionEditText.getText().toString().trim().equalsIgnoreCase("")){

                            if (isNetworkAvailable()) {
                                new WebserviceCall(CreateRequestFragment.this, AppConstants.Methods.createprayerrequest).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),mTitleEditeEditText.getText().toString().trim(),
                                        mDiscriptionEditText.getText().toString().trim());
                            } else {
                                Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            mDiscriptionEditText.setError("Description field cannot left blank!");
                        }

                    }
                    else {
                        mTitleEditeEditText.setError("Title field cannot left blank!");

                    }




                }else if(page.equalsIgnoreCase("2")){

                    if(!mTitleEditeEditText.getText().toString().trim().equalsIgnoreCase("")){
                        if(!mDiscriptionEditText.getText().toString().trim().equalsIgnoreCase("")){
                            if (isNetworkAvailable()) {
                                new WebserviceCall(CreateRequestFragment.this, AppConstants.Methods.addTestimony).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),mTitleEditeEditText.getText().toString().trim(),
                                        mDiscriptionEditText.getText().toString().trim());
                            } else {
                                Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            mDiscriptionEditText.setError("Description field cannot left blank!");
                        }



                    }
                    else {
                        mTitleEditeEditText.setError("Title field cannot left blank!");
                    }


                }

            }
        });

    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        switch (method) {
            case AppConstants.Methods.createprayerrequest:
                try {
                    Log.e("JsonResponse", mJsonObject.toString());
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)){


                      /*  BaseFragment baseFragment=null;
                        mOnFragmnetSwitch.onFragmentChange(new PrayerRequestFragment(getActivity(),baseFragment),"Prayer Requests and Testimonies",true);
*/


                      /*  AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());

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
                        dlgAlert.create().show();*/

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception d) {
                    d.printStackTrace();
                }
                case AppConstants.Methods.addTestimony:

                    try {
                        Log.e("JsonResponse", mJsonObject.toString());
                        if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)){
/*
                            BaseFragment baseFragment=null;
                            mOnFragmnetSwitch.onFragmentChange(new PrayerRequestFragment(getActivity(),baseFragment),"Prayer Requests and Testimonies",true);*/

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
                    catch (Exception d) {
                        d.printStackTrace();
                    }

        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if(page.equalsIgnoreCase("1")){
            mOnFragmnetSwitch.loadTitle("Create Request");
        }
        else {
            mOnFragmnetSwitch.loadTitle("Create Testimony");
        }


     //   mOnFragmnetSwitch.loadTitle("Create Request");
        OnMenuIconChange.iconchange(CreateRequestFragment.this);
        HomeActivity.fragment = CreateRequestFragment.this;
    }
}