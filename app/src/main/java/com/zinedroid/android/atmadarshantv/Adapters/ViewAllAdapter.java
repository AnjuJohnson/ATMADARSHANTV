package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.VideoPlayerActivity;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    ArrayList<Video> mVideoArrayList;
    ArrayList<String> alImage;
    Context context;

    public ViewAllAdapter(Context context, ArrayList<Video> mVideoArrayList) {
        super();
        this.context = context;
        this.mVideoArrayList = mVideoArrayList;
    }

    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view_all, viewGroup, false);
        ViewAllAdapter.ViewHolder viewHolder = new ViewAllAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllAdapter.ViewHolder viewHolder, int i) {
        //viewHolder.tvSpecies.setText(alName.get(i));

        final Video video=mVideoArrayList.get(i);
        viewHolder.title.setText(video.getVideo_titile());
        viewHolder.viewers.setText(video.getViews());
        Picasso.with(context)
                .load("http://img.youtube.com/vi/"+video.getIdd()+"/hqdefault.jpg")
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, VideoPlayerActivity.class);
                i.putExtra("clickedvideoTitle", video.getVideo_titile());
                i.putExtra("clickedvideo", video.getIdd());
                i.putExtra("clickedvideoviews", video.getViews());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVideoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgThumbnail;
        public TextView title,viewers;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            title = itemView.findViewById(R.id.title);
            viewers = itemView.findViewById(R.id.viewers);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            //   this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            //  clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            //    clickListener.onClick(view, getPosition(), true);
            return true;
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

}