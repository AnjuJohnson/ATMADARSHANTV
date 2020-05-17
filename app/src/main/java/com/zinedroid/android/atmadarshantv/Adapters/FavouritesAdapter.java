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

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.VideoPlayerActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    Context contextt;

    int resource;
    ArrayList<Video> mFavouriteList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;

    public FavouritesAdapter(BaseFragment context, Context contextt, ArrayList<Video> mFavouriteList) {
        super();
        //  this.context = context;
        this.mFavouriteList = mFavouriteList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.contextt = contextt;
    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_favourites, viewGroup, false);
        FavouritesAdapter.ViewHolder viewHolder = new FavouritesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavouritesAdapter.ViewHolder viewHolder, int i) {
        final Video mvideo = mFavouriteList.get(i);
        String videoid = getVideoId(mvideo.getVideolink());
        final String link = mvideo.getVideolink();
       viewHolder.toolbarFavorite.setVisibility(View.GONE);
        Picasso.with(contextt)
                .load("http://img.youtube.com/vi/" + link + "/hqdefault.jpg")
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contextt, VideoPlayerActivity.class);
                i.putExtra("clickedvideo", link);
                contextt.startActivity(i);
            }
        });
           viewHolder.discription.setText(mvideo.getDiscription());
        viewHolder.videotitle.setText(mvideo.getVideo_titile());
    }

    @Override
    public int getItemCount() {
        return mFavouriteList.size();
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
            toolbarFavorite = itemView.findViewById(R.id.favourite);

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

