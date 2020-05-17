package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Fragments.CategoryVideoListFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Category;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<Category> mCategoryList;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    public CategoryAdapter(BaseFragment context, ArrayList<Category> mCategoryList) {
        super();
      //  this.context = context;
        this.mCategoryList = mCategoryList;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder viewHolder, int i) {
        final Category mCateegory=mCategoryList.get(i);
        viewHolder.videotype.setText(mCateegory.getCat_name());
        viewHolder.no_video.setText(mCateegory.getCount()+" Videos");
        viewHolder.catogorycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("cat_id", mCateegory.getCat_id());
                baseFragment=new CategoryVideoListFragment();
                baseFragment.setArguments(bundle);
                onFragmnetSwitch.onFragmentChange(baseFragment,mCateegory.getCat_name(), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
public CardView catogorycard;
        public TextView videotype,no_video;
        public ViewHolder(View itemView) {
            super(itemView);
            videotype = itemView.findViewById(R.id.video_type);
            no_video = itemView.findViewById(R.id.no_videos);
            catogorycard = itemView.findViewById(R.id.card_view);

        }
    }

}

