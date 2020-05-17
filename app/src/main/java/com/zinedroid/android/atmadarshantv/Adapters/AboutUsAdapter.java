package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.VideoPlayerActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
    Context contextt;
    int resource;
//    ArrayList<LiveVideo> mLiveVideoArrayList;
    ArrayList<Video> mVideoArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;

    public AboutUsAdapter(BaseFragment context, Context contextt, ArrayList<Video> mVideoArrayList) {
        super();
         this.contextt = contextt;
        this.mVideoArrayList = mVideoArrayList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
    }

    @Override
    public AboutUsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_live_video, viewGroup, false);
        AboutUsAdapter.ViewHolder viewHolder = new AboutUsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AboutUsAdapter.ViewHolder viewHolder, int i) {
        final Video mvideo=mVideoArrayList.get(i);
        final String link = mvideo.getVideolink();
        viewHolder.discription.setText(mvideo.getDiscription());
        Picasso.with(contextt)
                .load("http://img.youtube.com/vi/" + link + "/hqdefault.jpg")
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);

        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contextt, VideoPlayerActivity.class);
                i.putExtra("clickedvideoTitle", mvideo.getVideo_titile());
                i.putExtra("clickedvideo", link);
                i.putExtra("clickedvideoviews", mvideo.getViews());
                contextt.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public TextView videotitle, discription;
        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
          //  videotitle = (TextView) itemView.findViewById(R.id.title);
            discription = (TextView) itemView.findViewById(R.id.mDiscriptionTextView);
        }
    }

}

