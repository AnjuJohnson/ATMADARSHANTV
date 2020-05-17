package com.zinedroid.android.atmadarshantv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.zinedroid.android.atmadarshantv.Activity.VideoPlayerActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DifferentShowAdapter extends RecyclerView.Adapter<DifferentShowAdapter.ViewHolder> implements WebserviceCall.WebServiceCall {
    Context contextt;
    int resource;
    ArrayList<Video> mVideo;
    public Config.OnFragmnetSwitch onFragmnetSwitch;
    BaseFragment baseFragment;
    private boolean onBind;
    AddtofavouritesShows mAddToFavourite;
    public DifferentShowAdapter(BaseFragment context, Context contextt, ArrayList<Video> mVideo) {
        super();
        //  this.context = context;
        this.mVideo = mVideo;
        onFragmnetSwitch = (Config.OnFragmnetSwitch) context.getActivity();
        this.baseFragment = context;
        this.contextt = contextt;
     //   mAddToFavourite= (AddtofavouritesShows) context;
    }

    @Override
    public DifferentShowAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_fragment_diffferentshow, viewGroup, false);
        DifferentShowAdapter.ViewHolder viewHolder = new DifferentShowAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DifferentShowAdapter.ViewHolder viewHolder, final int i) {
        onBind = true;
        final Video mvideo = mVideo.get(i);
        final String videoid = getVideoId(mvideo.getVideolink());
        //   final String link=mvideo.getVideolink();
       /* Picasso.with(contextt)
                .load("http://img.youtube.com/vi/" + videoid + "/hqdefault.jpg")
                .placeholder(R.drawable.youtubee)
                .error(R.drawable.youtubee)
                .into(viewHolder.imgThumbnail);*/
        viewHolder.show_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(contextt, VideoPlayerActivity.class);
                i.putExtra("clickedvideo", videoid);
                i.putExtra("clickedvideoTitle", mvideo.getVideo_titile());
                i.putExtra("clickedvideoviews", mvideo.getViews());
                contextt.startActivity(i);
            }
        });
       // viewHolder.viewers.setText(mvideo.getViews());
    //    viewHolder.discription.setText(mvideo.getDiscription());


        viewHolder.mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*

                Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(mvideo.getLink()));
                Intent chooser = Intent.createChooser(yt_play , "share With");

                if (yt_play .resolveActivity(contextt.getPackageManager()) != null) {
                   contextt. startActivity(chooser);
                }
*/



                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT, mvideo.getVideolink());

                intent.putExtra(Intent.EXTRA_SUBJECT, "Share via");

                contextt. startActivity(Intent.createChooser(intent, "Share"));




               /*
                shareDialog = new ShareDialog(baseFragment);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Androidlift")
                            .setContentDescription("Androidlift blog")
                            .setContentUrl(Uri.parse(mvideo.getLink()))
                            .build();
                    shareDialog.show(linkContent);
                }
*/





            }
        });



        viewHolder.videotitle.setText(mvideo.getVideo_titile());

       // viewHolder.toolbarFavorite.setFavorite(mVideo.get(i).isIsfavourite());
/*        viewHolder.toolbarFavorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (!onBind) {
                            if (favorite) {
                                buttonView.setFavorite(true);
                            //   mAddToFavourite.addtofavouritesAshows(i, favorite);
                                //webservice


                                new WebserviceCall(DifferentShowAdapter.this, baseFragment, AppConstants.Methods.addFavourites).execute(new String[]{baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                        mvideo.getCat_id(), "different_shows"
                                });




                            }
                            else {
                                buttonView.setFavorite(false);
                                Log.d("favourite", "notclickedinside");

                                new WebserviceCall(DifferentShowAdapter.this, baseFragment, AppConstants.Methods.removeFavourites).execute(new String[]{baseFragment.getSharedPreference(AppConstants.SharedKey.USER_ID), baseFragment.getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                                        mvideo.getCat_id(), "different_shows"
                                });
                            }



                        }



                    }
                });*/
        onBind = false;

    }


    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            Log.e("JsonResponse", mJsonObject.toString());
        } catch (Exception e) {
            Log.d("error", "error");
            e.printStackTrace();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView videotitle, discription,viewers;
        public MaterialFavoriteButton toolbarFavorite;
        LinearLayout show_card;
        public ImageView mShareButton;
        public ViewHolder(View itemView) {
            super(itemView);
           // imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            videotitle = itemView.findViewById(R.id.title);
       //     discription = (TextView) itemView.findViewById(R.id.discription);
       //    toolbarFavorite = (MaterialFavoriteButton) itemView.findViewById(R.id.favourite);
            show_card = itemView.findViewById(R.id.show_card);
            mShareButton = itemView.findViewById(R.id.share);
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
    public interface AddtofavouritesShows{
        void addtofavouritesAshows(int position, boolean isfavourite);
    }

}


