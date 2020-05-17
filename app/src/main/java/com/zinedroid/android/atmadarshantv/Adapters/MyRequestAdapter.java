package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context context;
    int resource;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;

    public MyRequestAdapter(BaseFragment context, ArrayList<PrayerRequest> mPrayerRequestArrayList) {
        super();
        //  this.context = context;
        this.mPrayerRequestArrayList = mPrayerRequestArrayList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.context = context.getContext();
    }

    @Override
    public MyRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_my_request_list, viewGroup, false);
        MyRequestAdapter.ViewHolder viewHolder = new MyRequestAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyRequestAdapter.ViewHolder viewHolder, final int i) {
        final PrayerRequest mPrayerRequest = mPrayerRequestArrayList.get(i);
        viewHolder.title.setText(mPrayerRequest.getRequest_title());
        viewHolder.discription.setText(mPrayerRequest.getRequest_discription());
        viewHolder.mDeleteREquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baseFragment.isNetworkAvailable()) {
                    new WebserviceCall(baseFragment, AppConstants.Methods.removePrayerRequest).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                            mPrayerRequest.getId());
                } else {
                    Toast.makeText(context, "Connect to the internet", Toast.LENGTH_LONG).show();
                }


                mPrayerRequestArrayList.remove(i);
                notifyDataSetChanged();
            }
        });


        viewHolder.mEditRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.discription.setVisibility(View.GONE);
                viewHolder.mEditDiscription.setVisibility(View.VISIBLE);
                viewHolder.mEditDiscription.setText(mPrayerRequest.getRequest_discription());
                viewHolder.title.setVisibility(View.GONE);
                viewHolder.mEditTitle.setVisibility(View.VISIBLE);
                viewHolder.mEditTitle.setText(mPrayerRequest.getRequest_title());
                viewHolder.mSaveEdit.setVisibility(View.VISIBLE);
                viewHolder.mSaveEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (baseFragment.isNetworkAvailable()) {
                            new WebserviceCall(baseFragment, AppConstants.Methods.editPrayerRequest).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                    mPrayerRequest.getId(),viewHolder.mEditTitle.getText().toString().trim(),viewHolder.mEditDiscription.getText().toString().trim());
                        } else {
                            Toast.makeText(context, "Connect to the internet", Toast.LENGTH_LONG).show();
                        }


                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return mPrayerRequestArrayList.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            switch (method) {
                case AppConstants.Methods.removePrayerRequest:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        Log.d("6666","6666");
                        Toast.makeText(context, "Removed Successfully", Toast.LENGTH_LONG).show();

                    }
                case AppConstants.Methods.editPrayerRequest:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        Log.d("6666","6666");
/*
                        Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_LONG).show();

                        onFragmnetSwitch.onFragmentChange(new MyPrayRequestFragment(context,baseFragment),"My Prayer Requests",true);*/

                    }
            }

        } catch (Exception e) {

        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView catogorycard;
        public TextView title, discription, prayer_count;
        public ImageView mEditRequest, mDeleteREquest;
        EditText mEditDiscription, mEditTitle;
        TextView mSaveEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            //prayer_count = (TextView) itemView.findViewById(R.id.prayer_count);
            discription = itemView.findViewById(R.id.discription);
            mEditRequest = itemView.findViewById(R.id.mEditRequest);
            mDeleteREquest = itemView.findViewById(R.id.mDeleteRequest);
            mEditDiscription = itemView.findViewById(R.id.mEditDiscription);
            mEditTitle = itemView.findViewById(R.id.mEditTitle);
            mSaveEdit = itemView.findViewById(R.id.mSaveEdit);

        }
    }

}

