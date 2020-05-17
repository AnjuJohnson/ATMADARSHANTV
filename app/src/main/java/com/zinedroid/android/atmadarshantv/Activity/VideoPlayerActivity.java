package com.zinedroid.android.atmadarshantv.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Video;

import java.util.ArrayList;

public class VideoPlayerActivity extends YouTubeBaseActivity {
    String clickedvideo;
    TextView mTtile;
    String videotitle,viewers;
    ArrayList<Video> mVideoListVideoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        mTtile = findViewById(R.id.mTitleTextView);
        mTtile.setText("Video 1");
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            clickedvideo = extras.getString("clickedvideo");
            videotitle = extras.getString("clickedvideoTitle");
            viewers = extras.getString("clickedvideoviews");
            mTtile.setText(videotitle);

            Video video=new Video();
            video.setIdd(clickedvideo);
            video.setVideo_titile(videotitle);
            video.setViews(viewers);
            Log.d("clicked videooo",clickedvideo);

            mVideoListVideoArrayList = (ArrayList<Video>) Video.findWithQuery(Video.class,
                    "Select * from Video");

            if(mVideoListVideoArrayList.size()!=0){
                for(Video video1:mVideoListVideoArrayList){
                    if(video1.getIdd().equalsIgnoreCase(clickedvideo)){
                        video1.delete();

                    }
                }
            }
            video.save();
        }

        YouTubePlayerView youTubePlayerView =
                findViewById(R.id.player);
        youTubePlayerView.initialize("AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                     //   youTubePlayer.loadVideo(clickedvideo);

                        //use this in next update


                        /*if (youTubePlayer != null) {
                            try {
                                youTubePlayer.loadVideo(clickedvideo);
                            } catch (IllegalStateException e) {
                                initialize("AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c", this);
                            }
                        }*/

//or the following method



                        try{
                            youTubePlayer.loadVideo(clickedvideo);

                        }
                        catch(IllegalStateException ise){
                            //do nothing probably device go rotated
                            return;
                        }

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }
}
