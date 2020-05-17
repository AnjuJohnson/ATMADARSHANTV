package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.widget.ShareDialog;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.VideoPlayerActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context contextt;
    private boolean onBind;
    int resource;
    ArrayList<Video> mVideo;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    AddToFavourite mAddToFavourite;
    BaseFragment baseFragment;
    ShareDialog shareDialog;

    public VideoAdapter(BaseFragment baseFragment, Context contextt, ArrayList<Video> mVideo) {
        super();
        //  this.context = context;
        this.mVideo = mVideo;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) baseFragment.getActivity();
        this.baseFragment = baseFragment;
        this.contextt = contextt;
        mAddToFavourite = (AddToFavourite) baseFragment;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video_list, viewGroup, false);
        VideoAdapter.ViewHolder viewHolder = new VideoAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder viewHolder, final int i) {
        onBind = true;
        final Video mvideo = mVideo.get(i);
        //   viewHolder.imgThumbnail.setImageResource(mvideo.getVideolink());
        final String link = mvideo.getVideolink();
        Log.d("video id", mvideo.getLink());

        Picasso.with(contextt)
                .load("http://img.youtube.com/vi/" + link + "/hqdefault.jpg")
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contextt, VideoPlayerActivity.class);
                i.putExtra("clickedvideoTitle", mvideo.getCat_name());
                i.putExtra("clickedvideo", link);
                i.putExtra("clickedvideoviews", mvideo.getViews());
                contextt.startActivity(i);
            }
        });

        viewHolder.mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*

                Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(mvideo.getLink()));
                Intent chooser = Intent.createChooser(yt_play , "share With");

                if (yt_play .resolveActivity(contextt.getPackageManager()) != null) {
                   contextt. startActivity(chooser);
                }
*/



                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT, mvideo.getLink());

                intent.putExtra(Intent.EXTRA_SUBJECT, "Share via");

                contextt. startActivity(Intent.createChooser(intent, "Share"));




               /*
                shareDialog = new ShareDialog(baseFragment);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Androidlift")
                            .setContentDescription("Androidlift blog")
                            .setContentUrl(Uri.parse(mvideo.getLink()))
                            .build();
                    shareDialog.show(linkContent);
                }
*/





            }
        });


        viewHolder.videotitle.setText(mvideo.getCat_name());
        //  viewHolder.toolbarFavorite.setColor(R.color.red);
        viewHolder.toolbarFavorite.setFavorite(mVideo.get(i).isIsfavourite());
        viewHolder.toolbarFavorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (!onBind) {
                            mAddToFavourite.addToFavourite(i, favorite);
                            if (favorite) {
                                buttonView.setFavorite(true);
                                Log.d("favourite", "clickedinside");
                                new WebserviceCall(VideoAdapter.this, baseFragment, AppConstants.Methods.addFavourites).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                        mvideo.getDiscription(), "category");
                            } else {
                                buttonView.setFavorite(false);
                                Log.d("favourite", "notclickedinside");

                                new WebserviceCall(VideoAdapter.this, baseFragment, AppConstants.Methods.removeFavourites).execute(baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                        mvideo.getDiscription(), "category");
                            }

                        }

                        //webservice

                    }
                });
        onBind = false;

    }

    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            Log.e("JsonResponse", mJsonObject.toString());
        } catch (Exception e) {
            Log.d("error", "error");
            e.printStackTrace();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView videotitle;
        public MaterialFavoriteButton toolbarFavorite;
        public ImageView mShareButton;


        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            videotitle = itemView.findViewById(R.id.video_title);
            toolbarFavorite = itemView.findViewById(R.id.favourite);
            mShareButton = itemView.findViewById(R.id.share);
        }
    }

    public interface AddToFavourite {
        void addToFavourite(int position, boolean isfavourite);
    }
}

