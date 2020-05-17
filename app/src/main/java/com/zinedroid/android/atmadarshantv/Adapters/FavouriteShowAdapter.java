package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Fragments.DifferentshowItemFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cecil Paul on 28/9/18.
 */
public class FavouriteShowAdapter extends RecyclerView.Adapter<FavouriteShowAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context contextt;

    int resource;
    ArrayList<Differentshows> mFavouriteList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;

    ArrayList<Video> videolinks;

    public FavouriteShowAdapter(BaseFragment context, Context contextt, ArrayList<Differentshows> mFavouriteList) {
        super();
        //  this.context = context;
        this.mFavouriteList = mFavouriteList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.contextt = contextt;
    }

    @Override
    public FavouriteShowAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_fav_show, viewGroup, false);
        FavouriteShowAdapter.ViewHolder viewHolder = new FavouriteShowAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavouriteShowAdapter.ViewHolder viewHolder, int i) {
        final Differentshows mvideo = mFavouriteList.get(i);
        Picasso.with(contextt)
                .load(mvideo.getImage())
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseFragment.isNetworkAvailable()) {
                    new WebserviceCall(FavouriteShowAdapter.this, baseFragment,AppConstants.Methods.selectedShowsDetails).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),mvideo.getShowid());


                } else {
                    Toast.makeText(contextt,"No internet",Toast.LENGTH_LONG).show();
                }
            }
        });


        Log.d("titile fav",mvideo.getShowname());
        viewHolder.discription.setText(mvideo.getDiscription());
        viewHolder.videotitle.setText(mvideo.getShowname());
    }

    @Override
    public int getItemCount() {
        return mFavouriteList.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.selectedShowsDetails:
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
                                String videoid= getVideoId(mLinks.getString(AppConstants.APIKeys.VIDEO_LINKK));
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
    public static String getVideoId(@NonNull String videoUrl) {
        String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView videotitle, discription;
        public MaterialFavoriteButton toolbarFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            videotitle = itemView.findViewById(R.id.title);
            discription = itemView.findViewById(R.id.discription);


        }
    }

}

