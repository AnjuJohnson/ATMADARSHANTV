package com.zinedroid.android.atmadarshantv.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;

import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.SplashWebserviceCall;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;


public class SplashActivity extends BaseActivity implements SplashWebserviceCall.Splashwebservicecall{
    private static int SPLASH_TIME_OUT = 5000;
    ImageView mLogo,mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);




       /* if (getSharedPreference(AppConstants.SharedKey.DEVICE_ID).equalsIgnoreCase("DEFAULT")) {
           String token = FirebaseInstanceId.getInstance().getToken();

            try {
                setSharedPreference(AppConstants.SharedKey.DEVICE_ID, token);
                Log.e("RefreshedToken++", getSharedPreference(AppConstants.SharedKey.DEVICE_ID));


                FirebaseMessaging.getInstance().subscribeToTopic("Daily_Word");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("RefreshedToken+++", getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
        }

        if (getSharedPreference(AppConstants.SharedKey.DEVICE_ID).equalsIgnoreCase("DEFAULT")) {
            startService(new Intent(getApplicationContext(), OfflineService.class));
        }*/



      //  Log.e("Token+++", getSharedPreference(AppConstants.SharedKey.DEVICE_ID));


    /*

*/


        mLogo  = findViewById(R.id.imgLogo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink);
        mLogo.startAnimation(myFadeInAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    setSharedPreference(AppConstants.SharedKey.DEVICE_ID, token);
                    Log.e("Token+++", getSharedPreference(AppConstants.SharedKey.DEVICE_ID));

                    if (!getSharedPreference(AppConstants.SharedKey.DEVICE_ID).equalsIgnoreCase("DEFAULT")) {

                        if (isNetworkAvailable()) {
                            new SplashWebserviceCall(SplashActivity.this, AppConstants.Methods.deviceRegister).execute(getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
                        } else {
                       //     Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(SplashActivity.this, HomeActivity.class);
                            i.putExtra("PUSHNOTIFICATION_MESSAGE", "HOMENOTIFICATION");
                            i.putExtra("body", "body");
                            i.putExtra("title", "title");
                            startActivity(i);
                            finish();

                        }

                    }
                    else {

                        String tokens = FirebaseInstanceId.getInstance().getToken();
                        setSharedPreference(AppConstants.SharedKey.DEVICE_ID, tokens);
                        if (isNetworkAvailable()) {
                            new SplashWebserviceCall(SplashActivity.this, AppConstants.Methods.deviceRegister).execute(getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
                        } else {
                            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();

                        }

                    }

                }
                else {
                    Intent i=new Intent(SplashActivity.this, HomeActivity.class);
                    i.putExtra("PUSHNOTIFICATION_MESSAGE", "HOMENOTIFICATION");
                    i.putExtra("body", "body");
                    i.putExtra("title", "title");
                    startActivity(i);
                    finish();

                }
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onLivewebservicecall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.deviceRegister:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        setSharedPreference(AppConstants.SharedKey.USER_ID, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.ID));
                        /*setSharedPreference(AppConstants.SharedKey.USER_NAME, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.USERNAME));*/



                        Intent i=new Intent(SplashActivity.this, HomeActivity.class);
                        i.putExtra("PUSHNOTIFICATION_MESSAGE", "HOMENOTIFICATION");
                        startActivity(i);
                        finish();

                    }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
        }
    }
}