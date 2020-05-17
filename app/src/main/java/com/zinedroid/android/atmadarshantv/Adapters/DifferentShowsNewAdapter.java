package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Fragments.DifferentshowItemFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cecil Paul on 27/8/18.
 */
public class DifferentShowsNewAdapter  extends RecyclerView.Adapter<DifferentShowsNewAdapter.ViewHolder> {
    Context contextt;
    int resource;
    ArrayList<Video> mVideo;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    ArrayList<Differentshows> mDifferentshoww;

    public DifferentShowsNewAdapter(BaseFragment context, Context contextt, ArrayList<Differentshows> mDifferentshoww) {
        super();
        //  this.context = context;
        this.mDifferentshoww = mDifferentshoww;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.contextt = contextt;
    }

    @Override
    public DifferentShowsNewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_differentshow, viewGroup, false);
        DifferentShowsNewAdapter.ViewHolder viewHolder = new DifferentShowsNewAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DifferentShowsNewAdapter.ViewHolder viewHolder, int i) {

        final Differentshows mDifferent=mDifferentshoww.get(i);
        viewHolder.videotitle.setText(mDifferent.getShowname());
      /*  mVideo=new ArrayList();
         mVideo=mDifferent.getShowdetaillist();
*/

      /*
        final Video mvideo = mVideo.get(i);
        final String videoid = getVideoId(mvideo.getVideolink());*/
        //   final String link=mvideo.getVideolink();
        Picasso.with(contextt)
                .load(mDifferent.getImage())
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);
        viewHolder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideo=new ArrayList();
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
        });

      //  viewHolder.videotitle.setText(mvideo.getVideo_titile());
       /* viewHolder.toolbarFavorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        //webservice
                        new WebserviceCall(DifferentShowsNewAdapter.this, baseFragment, AppConstants.Methods.addFavourites).execute(new String[]{baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                mvideo.getCat_id(), "different_shows"
                        });

                    }
                });*/

    }


    @Override
    public int getItemCount() {
        return mDifferentshoww.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView videotitle,viewers;
        public LinearLayout mlayout;
        public MaterialFavoriteButton toolbarFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            videotitle = itemView.findViewById(R.id.title);

            mlayout = itemView.findViewById(R.id.mlayout);

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


