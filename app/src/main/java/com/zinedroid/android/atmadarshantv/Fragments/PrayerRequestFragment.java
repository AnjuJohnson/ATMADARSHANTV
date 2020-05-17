package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Activity.LoginActivity;
import com.zinedroid.android.atmadarshantv.Adapters.PrayerRequestAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.TestimonyAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.models.Testimony;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class PrayerRequestFragment extends BaseFragment implements WebserviceCall.WebServiceCall, PrayerRequestAdapter.addPrayCount {
    Context context;
    private RecyclerView mPrayerRequestRecyclerView, mTestimoniesRecyclerView;
    private TextView mCreateRequestEditeEditText, mInvisiblelayout, mMyPrayerRequest, mInvisibleTestimonyTextView;
    RecyclerView.Adapter mPrayerRequestAdapter, mTestimoniesAdapter;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    ArrayList<Testimony> mTestimonyArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    private ImageView mAddTestimony;
    BaseFragment baseFragment;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView mViewAllRequestTextView,mViewAllTestimoniesTextView,mCreateTestimonyTextView,mMyTestimonyTextView;

    @SuppressLint("ValidFragment")
    public PrayerRequestFragment(Context context, BaseFragment baseFragmentt) {
        // Required empty public constructor
        this.baseFragment = baseFragmentt;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.prayer_request_fragment, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }

    public void mainFunction(View mFragmentView) {
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mCreateRequestEditeEditText = mFragmentView.findViewById(R.id.createrequest);
        mCreateTestimonyTextView = mFragmentView.findViewById(R.id.mCreateTestimonyTextView);
        mViewAllRequestTextView = mFragmentView.findViewById(R.id.viewall_request);
        mViewAllTestimoniesTextView = mFragmentView.findViewById(R.id.viewall_testimony);
        mMyTestimonyTextView = mFragmentView.findViewById(R.id.mMyTestimonyTextView);
        mMyPrayerRequest = mFragmentView.findViewById(R.id.myprayyerrequest);
        mInvisiblelayout = mFragmentView.findViewById(R.id.invisiblelayout);
        mInvisibleTestimonyTextView = mFragmentView.findViewById(R.id.invisibletestimonylayout);
     //   mAddTestimony = (ImageView) mFragmentView.findViewById(R.id.add);
        mPrayerRequestRecyclerView = mFragmentView.findViewById(R.id.prayerrequest_recycler_view);
        mPrayerRequestRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mPrayerRequestRecyclerView.setLayoutManager(mLayoutManager);

        if (isNetworkAvailable()) {
            new WebserviceCall(PrayerRequestFragment.this, AppConstants.Methods.listPrayRequest).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));

            new WebserviceCall(PrayerRequestFragment.this, AppConstants.Methods.listTestimony).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));


        } else {
            Toast.makeText(getActivity(), "Connect to the internet", Toast.LENGTH_LONG).show();
        }


        mViewAllRequestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("prayer_request", mPrayerRequestArrayList);
                BaseFragment baseFragment = new ViewAllRequest(getActivity());
                baseFragment.setArguments(bundle);
                mOnFragmnetSwitch.onFragmentChange(baseFragment, "Prayer Requsts", true);

            }
        });


        mViewAllTestimoniesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("testimonies", mTestimonyArrayList);
                BaseFragment baseFragment = new ViewAllTestimoniesFragment(getActivity());
                baseFragment.setArguments(bundle);
                mOnFragmnetSwitch.onFragmentChange(baseFragment, "Testimonies", true);

            }
        });


        mCreateRequestEditeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("PAGE", "1");
                    baseFragment = new CreateRequestFragment(getActivity());
                    baseFragment.setArguments(bundle1);
                    onFragmnetSwitch.onFragmentChange(baseFragment, "Create Prayer Request", true);

                }


            }
        });

        mCreateTestimonyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                }

else {
                    Bundle bundle = new Bundle();
                    bundle.putString("PAGE", "2");
                    baseFragment = new CreateRequestFragment(getActivity());
                    baseFragment.setArguments(bundle);
                    onFragmnetSwitch.onFragmentChange(baseFragment, "Create Testimony", true);


                }





            }
        });



        mMyPrayerRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                }
                else {


                    baseFragment = new MyPrayRequestFragment(getActivity(), PrayerRequestFragment.this);
                    onFragmnetSwitch.onFragmentChange(baseFragment, "My Prayer request", true);

                }


            }
        });


        mMyTestimonyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                }
                else {


                    baseFragment = new MyTestimonyFragment(getActivity(), PrayerRequestFragment.this);
                    onFragmnetSwitch.onFragmentChange(baseFragment, "My Testimony", true);

                }


            }
        });





        mTestimoniesRecyclerView = mFragmentView.findViewById(R.id.testmonies_recycler_view);
        mTestimoniesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTestimoniesRecyclerView.setLayoutManager(mLayoutManager2);
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.listPrayRequest:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mViewAllRequestTextView.setVisibility(View.VISIBLE);
                        mInvisiblelayout.setVisibility(View.GONE);
                        mPrayerRequestRecyclerView.setVisibility(View.VISIBLE);
                        mPrayerRequestArrayList = new ArrayList<>();
                        JSONArray mPrayerrequestJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.REQUEST);
                        for (int a = 0; a < mPrayerrequestJsonArray.length(); a++) {

                            PrayerRequest mPrayerRequest = new PrayerRequest();
                            mPrayerRequest.setId(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSET_ID));
                            mPrayerRequest.setRequest_user_id(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSET_USER_ID));
                            mPrayerRequest.setRequest_title(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSETED_TITLE));
                            mPrayerRequest.setRequest_discription(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSET_PRAY_DISCRIPTION));
                            mPrayerRequest.setRequuest_user_name(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUSETED_USER_NAME));

                            mPrayerRequest.setReq_count(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.REQUEST_COUNT));
                            mPrayerRequest.setPlace(mPrayerrequestJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.PLACE));

                            mPrayerRequestArrayList.add(mPrayerRequest);
                        }
                        mPrayerRequestAdapter = new PrayerRequestAdapter(PrayerRequestFragment.this, mPrayerRequestArrayList);
                        mPrayerRequestRecyclerView.setAdapter(mPrayerRequestAdapter);
                    } else {
                        mViewAllRequestTextView.setVisibility(View.GONE);
                        mInvisiblelayout.setVisibility(View.VISIBLE);
                        mPrayerRequestRecyclerView.setVisibility(View.GONE);
                    }

                case AppConstants.Methods.listTestimony:

                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mInvisibleTestimonyTextView.setVisibility(View.GONE);
                        mTestimoniesRecyclerView.setVisibility(View.VISIBLE);
                        mViewAllTestimoniesTextView.setVisibility(View.VISIBLE);
                        mTestimonyArrayList = new ArrayList();
                        JSONArray mTestimonyJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.TESTIMONY);
                        for (int a = 0; a < mTestimonyJsonArray.length(); a++) {
                            Testimony mTestimony = new Testimony();
                            mTestimony.setId(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.ID));
                            mTestimony.setUser_id(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.USER_ID));
                            mTestimony.setTitle(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TITLE));
                            mTestimony.setDiscription(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.DESCRIPTION));
                            mTestimony.setUser_name(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.USERNAME));
                            mTestimony.setComment(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.COMMENTS));
                            mTestimony.setPlace(mTestimonyJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.PLACE));
                            mTestimonyArrayList.add(mTestimony);
                        }

                        mTestimoniesAdapter = new TestimonyAdapter(PrayerRequestFragment.this, mTestimonyArrayList);
                        mTestimoniesRecyclerView.setAdapter(mTestimoniesAdapter);


                    } else {
                        mViewAllTestimoniesTextView.setVisibility(View.GONE);
                        mInvisibleTestimonyTextView.setVisibility(View.VISIBLE);
                        mInvisibleTestimonyTextView.setText("No Testimonies found");
                        mTestimoniesRecyclerView.setVisibility(View.GONE);
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception d) {
            d.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Prayer Requests and Testimonies");
        OnMenuIconChange.iconchange(PrayerRequestFragment.this);
        HomeActivity.fragment = PrayerRequestFragment.this;
    }

    @Override
    public void addpraycount(int i, String count) {
        Integer mCount = Integer.valueOf(count);
        Integer added_praycount = mCount + 1;
        mPrayerRequestArrayList.get(i).setReq_count(String.valueOf(added_praycount));
        mPrayerRequestAdapter.notifyDataSetChanged();
    }
}
