package com.zinedroid.android.atmadarshantv.Webservice;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Anjumol Johnson on 22/10/18.
 */
public class SplashWebserviceCall extends AsyncTask<String, Void, JSONObject> {
    API api;
    SplashWebserviceCall.Splashwebservicecall livewebServiceCall;
    Activity activity;
    int method;
    Dialog mDialog;



    public SplashWebserviceCall(BaseActivity activity, int method) {
        this.activity = activity;
        this.method = method;
        livewebServiceCall = (SplashWebserviceCall.Splashwebservicecall) activity;

    }



    public SplashWebserviceCall(BaseFragment fragment, int method) {
        this.activity = fragment.getActivity();
        this.method = method;
        livewebServiceCall = (SplashWebserviceCall.Splashwebservicecall) fragment;
    }

    @Override
    protected void onPreExecute() {

     /*   mDialog = Utility.showProgressBar(activity);
        mDialog.show();*/
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        livewebServiceCall.onLivewebservicecall(jsonObject, method);
       // mDialog.dismiss();
    }


    public JsonObject callService(String... strings) {
        switch (method) {
            case AppConstants.Methods.deviceRegister:
                return api.deviceRegister(strings[0]);
        }
        return null;
    }


    public interface Splashwebservicecall {
        void onLivewebservicecall(JSONObject mJsonObject, int method);
    }

}
