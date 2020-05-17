package com.zinedroid.android.atmadarshantv.Webservice;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.zinedroid.android.atmadarshantv.Adapters.DifferentShowAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.FavouriteShowAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.PrayerRequestAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.TodayScheduleAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.VideoAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.ViewAllRequestAdapter;
import com.zinedroid.android.atmadarshantv.Adapters.ViewAllTestimoniesAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;


/**
 * Created by Cecil Paul on 30/8/17.
 */

public class WebserviceCall extends AsyncTask<String, Void, JSONObject> {
    API api;
    WebServiceCall webServiceCall;
    Activity activity;
    int method;
    Dialog mDialog;


    public WebserviceCall(BaseActivity activity, int method) {
        this.activity = activity;
        this.method = method;
        webServiceCall = (WebServiceCall) activity;

    }

    public WebserviceCall(BaseFragment fragment, int method) {
        this.activity = fragment.getActivity();
        this.method = method;
        webServiceCall = (WebServiceCall) fragment;


    }



   /* public WebserviceCall(DifferentShowAdapter differentShowAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = (WebServiceCall) differentShowAdapter;
    }
*/
    public WebserviceCall(PrayerRequestAdapter prayerRequestAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = prayerRequestAdapter;
    }

    public WebserviceCall(DifferentShowAdapter differentShowAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = differentShowAdapter;
    }

    public WebserviceCall(VideoAdapter videoAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = videoAdapter;
    }

    public WebserviceCall(FavouriteShowAdapter favouriteShowAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = favouriteShowAdapter;
    }

    public WebserviceCall(ViewAllRequestAdapter viewAllRequestAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = viewAllRequestAdapter;
    }

    public WebserviceCall(ViewAllTestimoniesAdapter viewAllTestimoniesAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = (WebServiceCall) viewAllTestimoniesAdapter;
    }

    public WebserviceCall(TodayScheduleAdapter todayScheduleAdapter, BaseFragment baseFragment, int method) {
        this.activity = baseFragment.getActivity();
        this.method = method;
        webServiceCall = (WebServiceCall) todayScheduleAdapter;
    }


    @Override
    protected void onPreExecute() {

        mDialog = Utility.showProgressBar(activity);
        mDialog.show();
        super.onPreExecute();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AppConstants.END_POINT)
                .setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).build();

        api = restAdapter.create(API.class);
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject mJsonObject = null;
        try {
            mJsonObject = new JSONObject(callService(strings).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return mJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        webServiceCall.onWebServiceCall(jsonObject, method);
        mDialog.dismiss();
    }

    public JsonObject callService(String... strings) {


        switch (method) {


            case AppConstants.Methods.register:
                return api.register(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5],strings[6]);
            case AppConstants.Methods.register_image:
                TypedFile profileimage;
                profileimage = new TypedFile("multipart/form-data", new File(strings[7]));
                return api.register_image(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6],profileimage);

            case AppConstants.Methods.login:
                return api.login(strings[0], strings[1], strings[2], "1");

            case AppConstants.Methods.loadbanners:
                return api.loadbanners(strings[0], strings[1]);

            case AppConstants.Methods.categorylist:
                return api.listcategory(strings[0], strings[1]);
            case AppConstants.Methods.listvideos:
                return api.listvideos(strings[0], strings[1], strings[2]);
            case AppConstants.Methods.TodaySchedule:
                return api.ListProgramSchedule(strings[0], strings[1], strings[2]);
            case AppConstants.Methods.differentshows:
                return api.differentshows(strings[0], strings[1]);
            case AppConstants.Methods.listOwnPrayRequest:
                return api.listOwnPrayRequest(strings[0], strings[1]);

            case AppConstants.Methods.listOwnTestimonials:
                return api.listOwnTestimonials(strings[0], strings[1]);

            case AppConstants.Methods.listNotification:
                return api.listNotification(strings[0], strings[1]);

            case AppConstants.Methods.listchannel:
                return api.channellist(strings[0], strings[1]);
            case AppConstants.Methods.addFavourites:
                return api.addtofavourites(strings[0], strings[1], strings[2], strings[3]);
            case AppConstants.Methods.removeFavourites:
                return api.removeFavourites(strings[0], strings[1], strings[2], strings[3]);
            case AppConstants.Methods.favouritelist:
                return api.ListFavourites(strings[0], strings[1]);
            case AppConstants.Methods.createprayerrequest:
                return api.createprayerrequest(strings[0], strings[1], strings[2], strings[3]);
            case AppConstants.Methods.addTestimony:
                return api.addTestimony(strings[0], strings[1], strings[2], strings[3]);
            case AppConstants.Methods.listPrayRequest:
                return api.listPrayRequest(strings[0], strings[1]);
            case AppConstants.Methods.listTestimony:
                return api.listTestimony(strings[0], strings[1]);
            case AppConstants.Methods.addcomment:
                return api.addcomment(strings[0], strings[1], strings[2], strings[3]);
            case AppConstants.Methods.selectedShowsDetails:
                return api.selectedShowsDetails(strings[0], strings[1],strings[2]);

            case AppConstants.Methods.getScheduledShow:
                return api.getScheduledShow(strings[0], strings[1],strings[2]);

            case AppConstants.Methods.pushLogin:
                return api.PushLogin(strings[0], strings[1], strings[2], "1");


            case AppConstants.Methods.commentlist:
                return api.listComments(strings[0], strings[1], strings[2]);


            case AppConstants.Methods.fbLOgin:
                return api.fbLogin(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], "1");

            case AppConstants.Methods.pushFbLogin:
                return api.pushFbLogin(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], "1");
            case AppConstants.Methods.resetPassword:
                return api.forgetPassword(strings[0]);
            case AppConstants.Methods.updateProfile:
             //   Log.d("hhhhh",strings[7]);
                return api.updateProfile(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5],strings[6],strings[7],strings[8]);



            case AppConstants.Methods.addprayecount:
                return api.addprayecount(strings[0], strings[1],strings[2]);

            case AppConstants.Methods.removePrayerRequest:
                return api.removePrayerRequest(strings[0], strings[1],strings[2]);

            case AppConstants.Methods.removeTestimony:
                return api.removeTestimony(strings[0], strings[1],strings[2]);

            case AppConstants.Methods.editPrayerRequest:
                return api.editPrayerRequest(strings[0], strings[1],strings[2],strings[3],strings[4]);


            case AppConstants.Methods.updateProfileImage:
                TypedFile image;
                image = new TypedFile("multipart/form-data", new File(strings[9]));
                Log.d("hhhhh",strings[3]);
                return api.updateProfileImage(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5],strings[6],strings[7],strings[8], image);
            case AppConstants.Methods.recentlyadded:
                return api.recentlyadded(strings[0], strings[1]);
            case AppConstants.Methods.getLiveVideo:
                return api.getlivevideo(strings[0], strings[1]);
            case AppConstants.Methods.listIntroVideos:
                return api.listIntroVideos(strings[0], strings[1]);
            case AppConstants.Methods.liveVideo:
                return api.livevideo();

        }

        return null;
    }

    public interface WebServiceCall {
        void onWebServiceCall(JSONObject mJsonObject, int method);



    }

}
