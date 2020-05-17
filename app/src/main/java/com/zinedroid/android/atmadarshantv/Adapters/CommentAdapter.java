package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.PrayerRequest;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 29/8/18.
 */
public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<PrayerRequest> mPrayerRequestArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    public CommentAdapter(BaseFragment context, ArrayList<PrayerRequest> mPrayerRequestArrayList) {
        super();
        //  this.context = context;
        this.mPrayerRequestArrayList = mPrayerRequestArrayList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comment, viewGroup, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder viewHolder, int i) {
        final PrayerRequest mPrayerRequest=mPrayerRequestArrayList.get(i);
        //viewHolder.title.setText(mPrayerRequest.getRequest_title());
        viewHolder.discription.setText(mPrayerRequest.getRequest_discription());
        viewHolder.username.setText("by "+mPrayerRequest.getRequuest_user_name());
    }

    @Override
    public int getItemCount() {
        return mPrayerRequestArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView catogorycard;
        public TextView title,discription,username;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            username = itemView.findViewById(R.id.username);
            discription = itemView.findViewById(R.id.discription);

        }
    }

}

