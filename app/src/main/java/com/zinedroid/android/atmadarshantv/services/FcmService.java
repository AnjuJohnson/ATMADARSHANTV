package com.zinedroid.android.atmadarshantv.services;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;

import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.utils.Config;
import com.zinedroid.android.atmadarshantv.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * Created by Cecil Paul on 9/1/18.
 */

public class FcmService extends FirebaseMessagingService {
    private static final String TAG = FcmService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    private static final Logger log = Logger.getLogger(FcmService.class
            .getName());

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        String message = remoteMessage.getData().toString();
        Log.d("message", message);

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
     /*   if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            String title = remoteMessage.getNotification().getTitle();
            Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
         //   handleNotification(remoteMessage.getNotification().getBody());

            JsonObject notifiDetails = new JsonObject();
            notifiDetails.addProperty("body", remoteMessage.getNotification().getBody());
            notifiDetails.addProperty("title", title);

           JsonObject jsonObj = new JsonObject();
            *//*  jsonObj.addProperty("topic", "deals");*//*
            jsonObj.add("notification", notifiDetails);

        *//*    JsonObject msgObj = new JsonObject();
            msgObj.add("message", jsonObj);*//*

            log.info("json  message "+jsonObj.toString());
           // String messagebackground=remoteMessage.toString();
        }*/

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            JSONObject json = new JSONObject(remoteMessage.getData());
            handleDataMessage(json);
        }
    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Log.i("message++", message);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {

            Log.i("message++", message);
            // If the app is in background, firebase itself handles the notification


        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            String body = json.getString("body");
            String title = json.getString("title");
            Log.e(TAG, "title: " + title);
            shownotification(body, title);


/*
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", body);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//                NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                notificationManager.createNotificationChannel(channel);
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("PUSHNOTIFICATION_MESSAGE", "MESSAGE");
                intent.putExtra("body", body);
                intent.putExtra("title", title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.athmadarshan_logo_final)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
            } else {
                // app is in background, show the notification in notification tray



                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                resultIntent.putExtra("PUSHNOTIFICATION_MESSAGE", "MESSAGE");
                resultIntent.putExtra("body", body);
                resultIntent.putExtra("title", title);
                *//*PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//**//* Request code *//**//*, resultIntent,
                        PendingIntent.FLAG_ONE_SHOT);*//*


                // check for image attachment
                *//*if (TextUtils.isEmpty(imageUrl)) {
                 if(getSharedPreference("NOTIFICATIONSTATUS").equals("true")){
                     showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                 }

                } else {
                    // image is present, show notification with image
                    if(getSharedPreference("NOTIFICATIONSTATUS").equals("true")){
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                    }

                }*//*
            }*/
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    public void shownotification(String body, String title) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setSmallIcon(R.mipmap.atmadarshan_logo);
            notification.setColor(getResources().getColor(R.color.colorPrimary));
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.atmadarshan_logo));
            notification.setContentTitle("Todays'Quote"); // title for notification
            notification.setContentText(title); // message for notification
            notification.setAutoCancel(true); // clear notification after click

        } else {
            notification.setSmallIcon(R.mipmap.atmadarshan_logo);
            notification.setContentTitle("Todays'Quote"); // title for notification
            notification.setContentText(title); // message for notification
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.atmadarshan_logo));
            notification.setAutoCancel(true);
        }
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
        //  notificationUtils.showNotificationMessage("BookMyRoom", message, timeStamp, intent);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("PUSHNOTIFICATION_MESSAGE", "MESSAGE");
        intent.putExtra("body", body);
        intent.putExtra("title", title);


        Log.e(TAG, "title+++: " + title);

        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        notification.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification.build());

    }


    /* Showing notification with text only*/
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    public void setSharedPreference(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPreference(String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        return prefs.getString(key, "DEFAULT");
    }
}
