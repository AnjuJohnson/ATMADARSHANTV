package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Fragments.RadioPlayerFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Category;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

public class ChanneListAdapter extends RecyclerView.Adapter<ChanneListAdapter.ViewHolder> {
    Context contextt;
    int resource;
    ArrayList<Category> mChannellist;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    public ChanneListAdapter(BaseFragment context, ArrayList<Category> mChannellist) {
        super();
        //  this.context = context;
        this.mChannellist = mChannellist;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;

    }

    @Override
    public ChanneListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_channellist, viewGroup, false);
        ChanneListAdapter.ViewHolder viewHolder = new ChanneListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChanneListAdapter.ViewHolder viewHolder, int i) {
       final Category mchannellist=mChannellist.get(i);
        viewHolder.videotitle.setText(mchannellist.getCat_name());
    //    final String link=mvideo.getVideolink();
        Picasso.with(contextt)
                .load(mchannellist.getCount())
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);

        viewHolder.mChannellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("radio_id", mchannellist.getCat_id());
                bundle.putString("icon", mchannellist.getCount());
                bundle.putString("link", mchannellist.getLink());
                bundle.putString("title", mchannellist.getCat_name());
                baseFragment=new RadioPlayerFragment();
                baseFragment.setArguments(bundle);
                onFragmnetSwitch.onFragmentChange(baseFragment,mchannellist.getCat_name(), true);
            }
        });




    }



    @Override
    public int getItemCount() {
        return mChannellist.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public TextView videotitle;
        public LinearLayout mChannellayout;
        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.icon);
            videotitle = itemView.findViewById(R.id.title);
            mChannellayout = itemView.findViewById(R.id.channellayout);
        }
    }

}


