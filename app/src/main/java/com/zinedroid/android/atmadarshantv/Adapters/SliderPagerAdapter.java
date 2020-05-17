package com.zinedroid.android.atmadarshantv.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Fragments.DifferentShowsNewFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Differentshows;
import com.zinedroid.android.atmadarshantv.utils.Config;

import java.util.ArrayList;

/**
 * Created by Cecil Paul on 21/9/18.
 */
public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Differentshows> image_arraylist;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;

    public SliderPagerAdapter(BaseFragment baseFragment, Activity activity, ArrayList<Differentshows> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.baseFragment=baseFragment;
        onFragmnetSwitch= (Config.OnFragmnetSwitch) activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("444444", String.valueOf(image_arraylist.size()));
        Differentshows differentshows=image_arraylist.get(position);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = view.findViewById(R.id.im_slider);


        Picasso.with(activity.getApplicationContext())
                .load(differentshows.getImage())
                // optional
                .into(im_slider);

        container.addView(view);


        im_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseFragment=new DifferentShowsNewFragment(activity);
                onFragmnetSwitch.onFragmentChange(baseFragment,"Shows",true);
            }
        });


        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}