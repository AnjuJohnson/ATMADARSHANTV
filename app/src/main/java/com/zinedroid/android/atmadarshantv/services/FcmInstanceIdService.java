package com.zinedroid.android.atmadarshantv.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.utils.Config;

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = FcmInstanceIdService.class.getSimpleName();
    private static String token;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        token = refreshedToken;





        // Saving reg id to shared preferences
      storeRegIdInPref(refreshedToken);


        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    public String getToken() {
        onTokenRefresh();
        return token;
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.i(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(AppConstants.SharedKey.DEVICE_ID, token);
        editor.commit();
        Log.i(TAG, "TokenToServer: " + token);

      //  Log.i(TAG, "TokenToServer: " + getSharedPreferences(AppConstants.SharedKey.DEVICE_ID));
    }

   /* public void setSharedPreference(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPreference(String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        return prefs.getString(key, "DEFAULT");
    }
*/

}