package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Fragments.PrayerDiscriptionFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anjumol Johnson on 29/10/18.
 */
public class ViewAllRequestAdapter  extends RecyclerView.Adapter<ViewAllRequestAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context context;
    int resource;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    addPrayCount mAddPrayCount;

    public ViewAllRequestAdapter(BaseFragment context, ArrayList<PrayerRequest> mPrayerRequestArrayList) {
        super();
        //  this.context = context;
        this.mPrayerRequestArrayList = mPrayerRequestArrayList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
       mAddPrayCount = (addPrayCount) context;
    }

    @Override
    public ViewAllRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_viewall_prayer_request, viewGroup, false);
        ViewAllRequestAdapter.ViewHolder viewHolder = new ViewAllRequestAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllRequestAdapter.ViewHolder viewHolder, final int i) {
        final PrayerRequest mPrayerRequest = mPrayerRequestArrayList.get(i);
        viewHolder.title.setText(mPrayerRequest.getRequest_title());

        //      viewHolder.username.setText("by "+mPrayerRequest.getRequuest_user_name()+","+mPrayerRequest.getPlace());
        if (mPrayerRequest.getPlace().equalsIgnoreCase("")) {
            viewHolder.username.setText("by " + mPrayerRequest.getRequuest_user_name());
        } else {
            viewHolder.username.setText("by " + mPrayerRequest.getRequuest_user_name() + "," + mPrayerRequest.getPlace());
        }


        // Log.d("place",mPrayerRequest.getPlace());


        if (!mPrayerRequest.getReq_count().equalsIgnoreCase("0")) {
            viewHolder.prayer_count.setText(mPrayerRequest.getReq_count() + "+");
        } else {
            viewHolder.prayer_count.setText(mPrayerRequest.getReq_count());
        }
        viewHolder.pray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mAddPrayCount.addpraycount(i, mPrayerRequest.getReq_count());
                new WebserviceCall(ViewAllRequestAdapter.this, baseFragment, AppConstants.Methods.addprayecount).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID), mPrayerRequest.getId());
            }
        });
        viewHolder.mRequestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                baseFragment = new PrayerDiscriptionFragment();
                bundle.putSerializable("PRAYERDETAILS", mPrayerRequest);
                bundle.putString("PAGE", "1");
                baseFragment.setArguments(bundle);

                onFragmnetSwitch.onFragmentChange(baseFragment, "Prayer", true);
            }
        });

        // viewHolder.no_video.setText(mPrayerRequest.getRequest_discription());
    }

    @Override
    public int getItemCount() {
        return mPrayerRequestArrayList.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        Log.i("jsondata", mJsonObject.toString());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mRequestCard;
        public TextView title, pray, prayer_count, username;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            prayer_count = itemView.findViewById(R.id.prayer_count);
            pray = itemView.findViewById(R.id.pray);
            mRequestCard = itemView.findViewById(R.id.mRequestCard);
            username = itemView.findViewById(R.id.username);

        }
    }

    public interface addPrayCount {
        void addpraycount(int i, String count);


    }
}