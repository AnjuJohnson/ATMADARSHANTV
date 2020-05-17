package com.zinedroid.android.atmadarshantv.utils;


import com.zinedroid.android.atmadarshantv.Base.BaseFragment;

/**
 * Created by Cecil Paul on 9/1/18.
 */

public class Config {

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";



    public interface OnFragmnetSwitch {
        void onFragmentChange(BaseFragment fragment, String title, boolean isReplace);
        void loadTitle(String Title);
    }


}
