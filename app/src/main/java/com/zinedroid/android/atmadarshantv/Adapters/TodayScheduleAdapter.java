package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Fragments.DifferentshowItemFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.models.TodaySchedule;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodayScheduleAdapter extends RecyclerView.Adapter<TodayScheduleAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context contextt;
    int resource;
    ArrayList<TodaySchedule> mTodaySchedule;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    ArrayList<Video> videolinks;
    public TodayScheduleAdapter(BaseFragment context, Context contextt, ArrayList<TodaySchedule> mTodaySchedule) {
        super();
        //  this.context = context;
        this.mTodaySchedule = mTodaySchedule;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.contextt = contextt;
    }

    @Override
    public TodayScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_todayshedule, viewGroup, false);
        TodayScheduleAdapter.ViewHolder viewHolder = new TodayScheduleAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodayScheduleAdapter.ViewHolder viewHolder, int i) {
        final TodaySchedule mtodaySchedule=mTodaySchedule.get(i);
        //   viewHolder.imgThumbnail.setImageResource(mvideo.getVideolink());
        final String link=mtodaySchedule.getImage();
        Picasso.with(contextt)
                .load(link)
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.mTodayScheduleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseFragment.isNetworkAvailable()) {
                    new WebserviceCall(TodayScheduleAdapter.this, baseFragment,AppConstants.Methods.getScheduledShow).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),mtodaySchedule.getProfram());
                } else {
                    Toast.makeText(contextt,"No internet",Toast.LENGTH_LONG).show();
                }
            }
        });
        viewHolder.videotitle.setText(mtodaySchedule.getProfram());
        viewHolder.discription.setText(mtodaySchedule.getDiscription());
        viewHolder.start.setText(mtodaySchedule.getFrom());
      //  viewHolder.end.setText(mtodaySchedule.getTo());

    }



    @Override
    public int getItemCount() {
        return mTodaySchedule.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.getScheduledShow:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        JSONArray mdifferentshowsJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.DIFFERENTSHOWS);
                        AppConstants.differentshowsArrayList=new ArrayList<>();

                        for (int a = 0; a < mdifferentshowsJsonArray.length(); a++) {
                            videolinks = new ArrayList<>();
                            Differentshows mDifferentshow = new Differentshows();
                            mDifferentshow.setShowid(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.SHOW_ID));
                            mDifferentshow.setShowname(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.SHOW_NAME));
                            mDifferentshow.setImage(mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.IMAGE));

                            String fav_status = mdifferentshowsJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.FAV_STATUS);



                            if (fav_status.equalsIgnoreCase("true")) {
                                mDifferentshow.setIsfavourite(true);
                            } else {
                                mDifferentshow.setIsfavourite(false);
                            }
                            JSONObject mVideolinkObjects = mdifferentshowsJsonArray.getJSONObject(a);
                            JSONArray mVideoLinkArray = mVideolinkObjects.getJSONArray(AppConstants.APIKeys.LINK);
                            for (int k = 0; k < mVideoLinkArray.length(); k++) {
                                JSONObject mLinks = mVideoLinkArray.getJSONObject(k);
                                Video video = new Video();
                                video.setCat_id(mLinks.getString(AppConstants.APIKeys.ID));
                                video.setVideo_titile(mLinks.getString(AppConstants.APIKeys.TITLE));
                                video.setVideolink(mLinks.getString(AppConstants.APIKeys.VIDEO_LINKK));
                                video.setDiscription(mLinks.getString(AppConstants.APIKeys.LINK_DISCRIPTION));
                            //    String videoid= getVideoId(mLinks.getString(AppConstants.APIKeys.VIDEO_LINKK));
                                video.setViews(mLinks.getString(AppConstants.APIKeys.VIEWERS));
                                videolinks.add(video);
                            }
                            mDifferentshow.setShowdetaillist(videolinks);
                            AppConstants.differentshowsArrayList.add(mDifferentshow);
                            //   mDifferentshoww.add(mDifferentshow);

                        }
                        //  Log.d("mDifferentshowwwwwwww", String.valueOf(mDifferentshoww.size()));
                        Log.d("mDifferentshoww", String.valueOf(AppConstants.differentshowsArrayList.size()));
                        Differentshows mDifferent= AppConstants.differentshowsArrayList.get(0);
                        Bundle bundle = new Bundle();
                        baseFragment=new DifferentshowItemFragment();
                        bundle.putSerializable("VIDEOLIST",mDifferent.getShowdetaillist());
                        bundle.putString("SHOWNAME",mDifferent.getShowname());
                        bundle.putString("SHOWIMAGE",mDifferent.getImage());
                        bundle.putString("SHOWID",mDifferent.getShowid());
                        String fav_status= String.valueOf(mDifferent.isIsfavourite());
                        bundle.putString("FAV_STATUS",fav_status);
                        baseFragment.setArguments(bundle);
                        onFragmnetSwitch.onFragmentChange(baseFragment,mDifferent.getShowname(), true);

                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public TextView videotitle,discription,start,end;
        public CardView mTodayScheduleCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
             videotitle = itemView.findViewById(R.id.title);
            discription = itemView.findViewById(R.id.discription);
            start = itemView.findViewById(R.id.start_time);
            mTodayScheduleCardView = (CardView) itemView.findViewById(R.id.mTodayScheduleCardView);
        }
    }

}


