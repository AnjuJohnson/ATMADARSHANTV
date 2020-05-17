package com.zinedroid.android.atmadarshantv.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Activity.LoginActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Fragments.CommentListFragment;
import com.zinedroid.android.atmadarshantv.Fragments.PrayerDiscriptionFragment;
import com.zinedroid.android.atmadarshantv.Fragments.WriteCommentFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Testimony;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

/**
 * Created by Anjumol Johnson on 29/10/18.
 */
public class ViewAllTestimoniesAdapter extends RecyclerView.Adapter<ViewAllTestimoniesAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<Testimony> mTestimonyArrayList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    public ViewAllTestimoniesAdapter(BaseFragment context, ArrayList<Testimony> mTestimonyArrayList) {
        super();
        //  this.context = context;
        this.mTestimonyArrayList = mTestimonyArrayList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.context=context.getContext();
    }

    @Override
    public ViewAllTestimoniesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view_all_testimony, viewGroup, false);
        ViewAllTestimoniesAdapter.ViewHolder viewHolder = new ViewAllTestimoniesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllTestimoniesAdapter.ViewHolder viewHolder, int i) {
        final Testimony mTestimony=mTestimonyArrayList.get(i);
        viewHolder.title.setText(mTestimony.getTitle());


        if(mTestimony.getPlace().equalsIgnoreCase("")){
            viewHolder.username.setText("by "+mTestimony.getUser_name());
        }
        else {
            viewHolder.username.setText("by "+mTestimony.getUser_name()+","+mTestimony.getPlace());
        }



        viewHolder.mRequestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                baseFragment=new PrayerDiscriptionFragment();
                bundle.putSerializable("PRAYERDETAILS",mTestimony);
                bundle.putString("PAGE","2");
                baseFragment.setArguments(bundle);

                onFragmnetSwitch.onFragmentChange(baseFragment,"Testimony",true);
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!baseFragment.getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    baseFragment.startActivity(new Intent(context, LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                }
                else { Bundle bundle = new Bundle();
                    baseFragment=new WriteCommentFragment();
                    bundle.putString("testimony_id",mTestimony.getId());
                    baseFragment.setArguments(bundle);

                    onFragmnetSwitch.onFragmentChange(baseFragment,"Your Comments",true);

                }


            }
        });
        viewHolder.prayer_count.setText(mTestimony.getComment());
        viewHolder.commentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                baseFragment=new CommentListFragment();
                bundle.putString("testimony_id",mTestimony.getId());
                baseFragment.setArguments(bundle);
                onFragmnetSwitch.onFragmentChange(baseFragment,"Comments",true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTestimonyArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView mRequestCard;
        public TextView title,prayer_count,username;
        ImageView comment;
        LinearLayout commentlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            prayer_count = itemView.findViewById(R.id.prayer_count);
            mRequestCard = itemView.findViewById(R.id.mRequestCard);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            commentlayout = itemView.findViewById(R.id.commentlayout);

        }
    }

}
