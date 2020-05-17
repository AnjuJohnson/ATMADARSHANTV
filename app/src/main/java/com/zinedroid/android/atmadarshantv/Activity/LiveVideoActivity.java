package com.zinedroid.android.atmadarshantv.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.Fragments.LiveVideoFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LiveVideoActivity extends BaseActivity implements WebserviceCall.WebServiceCall {

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    PlayerView playerView;
    ProgressBar loading;
    ExoPlayer player;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Context context;
    TextView mNoDataLinearLayout;
    Utility.menuIconChange OnMenuIconChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_live);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        playerView = findViewById(R.id.video_view);
        mNoDataLinearLayout = findViewById(R.id.mNoDataLinearLayout);
        loading = findViewById(R.id.loading);

        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }


    @Override
    public void onStart() {
        super.onStart();


        if (isNetworkAvailable()) {
            new WebserviceCall(LiveVideoActivity.this, AppConstants.Methods.getLiveVideo).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
        }

    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.getLiveVideo:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {

                        JSONArray mCategoryJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.VIDEO);
                        for (int a = 0; a < mCategoryJsonArray.length(); a++) {
                            JSONObject mVideolonk = mCategoryJsonArray.getJSONObject(a);

                            String VIDEOTITLE = mVideolonk.getString(AppConstants.APIKeys.TITLE);
                            String VIDEOLINK = mVideolonk.getString(AppConstants.APIKeys.LINK);
                            String STATUS = mVideolonk.getString(AppConstants.APIKeys.STATUS);
                            if (STATUS.equalsIgnoreCase("1")) {
                                startlive(VIDEOLINK);
                            } else {
                                playerView.setVisibility(View.GONE);
                                mNoDataLinearLayout.setVisibility(View.VISIBLE);
                            }


                        }


                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startlive(String videolink) {

        //--------------------------------------
        //Creating default track selector
        //and init the player
        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(adaptiveTrackSelection),
                new DefaultLoadControl());

        //init the player
        playerView.setPlayer(player);
        playerView.hideController();
        //-------------------------------------------------
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory((LiveVideoActivity.this),
                Util.getUserAgent(this, "Exo2"), defaultBandwidthMeter);

        //-----------------------------------------------
        //Create media source

        //  String hls_url = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
     //   String hls_url = "http://my2.videodb.in:8081/ishvani/golive/playlist.m3u8";
        Uri uri = Uri.parse(videolink);
        Handler mainHandler = new Handler();
        MediaSource mediaSource = new HlsMediaSource(uri,
                dataSourceFactory, mainHandler, null);
        player.prepare(mediaSource);


        player.setPlayWhenReady(playWhenReady);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        loading.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        loading.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                playerView.setVisibility(View.GONE);
                mNoDataLinearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, true, false);

    }
}
