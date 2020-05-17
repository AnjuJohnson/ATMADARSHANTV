package com.zinedroid.android.atmadarshantv.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.utils.Config;

public class RadioPlayerFragment extends BaseFragment {
    ImageView mimage,startButton,stopButton;
    String icon, link, title;
    TextView mTitle;
    //private Button startButton;
  //  private Button stopButton;

    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    public RadioPlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.radio_player, container, false);
        mainfunction(view);
        return view;
    }


    public void mainfunction(View v) {
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        icon = getArguments().getString("icon");
        link = getArguments().getString("link");
        title = getArguments().getString("title");
        mimage = v.findViewById(R.id.imgLogo);
        mTitle = v.findViewById(R.id.title);
        mTitle.setText(title);
        Picasso.with(getActivity())
                .load(icon)
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(mimage);
//exo player
        startButton = v. findViewById(R.id.startButton);
     //   stopButton = (ImageView)v. findViewById(R.id.stopButton);

        bandwidthMeter = new DefaultBandwidthMeter();
        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "mediaPlayerSample"), defaultBandwidthMeter);


        mediaSource = new ExtractorMediaSource(Uri.parse(link), dataSourceFactory, extractorsFactory, null, null);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        player.prepare(mediaSource);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // player.setPlayWhenReady(true);
               // player.setPlayWhenReady(!player.getPlayWhenReady());


                if (isPlaying()) {
                    startButton.setImageResource(R.mipmap.radioplay);
                    player.setPlayWhenReady(false);
                } else {
                    startButton.setImageResource(R.mipmap.pause);
                    player.setPlayWhenReady(true);
                }




            }
        });

      /*  stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);
            }
        });
*/
    }

    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.setPlayWhenReady(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Radio");
        OnMenuIconChange.iconchange(RadioPlayerFragment.this);
        HomeActivity.fragment = RadioPlayerFragment.this;
    }
    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

}
