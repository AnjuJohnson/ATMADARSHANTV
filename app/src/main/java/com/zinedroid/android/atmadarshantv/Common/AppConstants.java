package com.zinedroid.android.atmadarshantv.Common;

import android.os.Environment;

import com.zinedroid.android.atmadarshantv.models.Differentshows;

import java.io.File;
import java.util.ArrayList;

public class AppConstants {

    public static final String END_POINT = "http://atmadarshantv.in/mobileapps/api/list_api/";
    public static final String SHARED_KEY = "AtmadarshanTv";
    public static  String DEVICEID = "deviceid";
    public static final String LIVE_END_POINT ="https://www.googleapis.com/youtube/v3/";
    public static final String LIVE_VIDEO_END_POINT ="http://my2.videodb.in:8081/ishvani/golive/playlist.m3u8";
    public static String UserEmail;
    public static File[] aDirArray;
    public static String ExtDir;
    public static ArrayList<Differentshows> differentshowsArrayList=new ArrayList<>();
    public static String[] directorylist = new String[]{Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Video/Sent", Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Images", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)), String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))};
    public static boolean isServiceCalled = false;

    public final class APIKeys {
        public static final String DEVICE_ID = "device_id";
        public static final String EMAIL = "email";
        public static final String PLACE = "place";
        public static final String PASSWORD = "password";
        public static final String USERNAME = "username";
        public static final String FULLNAME = "full_name";
        public static final String ADDRESS = "address";
        public static final String PROFILE_COMPLETE = "profile_complete";
        public static final String PROFILE_ADDRESS = "address";
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String IMAGE = "image";
        public static final String FB_ID = "fb_id";
        public static final String PHONE_NO = "phone_number";
        public static final String IMAGE_URL = "image_url";
        public static final String STATUS_CODE = "status_code";
        public static final String USER_DETAILS = "user_details";
        public static final String USER_NAME = "user_name";
        public static final String LOG_STATUS = "log_status";
        public static final String DESCRIPTION = "description";
        public static final String USER_ID = "user_id";
        public static final String CATGORY_ID = "cat_id";
        public static final String CATGORYY_ID = "category_id";
        public static final String CATGORY_NAME = "cat_name";
        public static final String CATGORY_LIST = "cat_list";
        public static final String CAT_VIDEO = "cat_video";
        public static final String LINK = "link";
        public static final String CATEGORY = "category";
        public static final String VIDEO_LINK = "vid_link";
        public static final String COUNT = "count";
        public static final String PROGRAM = "program";
        public static final String DATE = "date";
        public static final String FROMDATE = "from_time";
        public static final String TODATE = "to_time";
        public static final String DISCRIPTION = "description";
        public static final String DURATION = "duration";
        public static final String DIFFERENTSHOWS = "different_shows";
        public static final String SHOW_NAME = "show_name";
        public static final String VIDEO_LINKK = "video_link";
        public static final String SHOWS = "shows";
        public static final String LINK_DISCRIPTION = "link_description";
        public static final String SHOW_ID = "show_id";
        public static final String VIEWERS = "viewers";
        public static final String TITLE = "title";
        public static final String CHANNEL = "channel";
        public static final String ICON = "icon";
        public static final String VIDEO_ID = "video_id";
        public static final String VIDEO_DETAIL = "video_detail";
        public static final String FAVOURITElIST = "favourites";
        public static final String REQUEST = "request";
        public static final String REQUSET_USER_ID = "requested_user";
        public static final String REQUSETED_TITLE = "req_title";
        public static final String REQUSET_PRAY_DISCRIPTION = "req_pray_description";
        public static final String REQUSETED_USER_NAME = "requested_user";
        public static final String REQUSET_ID = "req_id";
        public static final String REQUEST_COUNT = "req_count";
        public static final String TESTIMONY = "testimony";
        public static final String FAV_STATUS = "fav_status";

        public static final String PAGE_INFO = "pageInfo";
        public static final String TOTAL_RESULT = "totalResults";
        public static final String ITEMS = "items";
        public static final String VIDEOID = "videoId";
        public static final String SNIPPET = "snippet";
        public static final String MEDIUM = "medium";
        public static final String URL = "url";
        public static final String THUMBNAILS = "thumbnails";

        public static final String VID_NAME = "vid_name";
        public static final String VIDLINK = "vid_link";
        public static final String TESTIMONY_ID = "testimony_id";
        public static final String COMMENT = "comment";
        public static final String COMMENTS = "comments";
        public static final String INTROVIDEO = "introvideo";

        public static final String VIDEO = "video";

        public static final String CURRENT_PASSWORD = "current_password";

        public static final String NEW_PASSWORD = "new_password";
        public static final String BANNER = "banner";
        public static final String MESSAGES = "messages";
        public static final String VERSE = "verse";
        public static final String WORD = "word";
        public static final String SHOW = "show";
        public static final String VIEWS = "views";
        public static final String STATUS = "status";




    }

    public class Methods {
        public static final int register = 102;
        public static final int register_image = 128;
        public static final int login = 103;
        public static final int fbLOgin = 118;
        public static final int pushFbLogin = 119;
        public static final int pushLogin = 104;
        public static final int categorylist = 105;
        public static final int listvideos = 106;
        public static final int TodaySchedule = 107;
        public static final int differentshows = 108;
        public static final int listchannel = 109;
        public static final int addFavourites = 110;
        public static final int favouritelist = 111;
        public static final int removeFavourites = 112;
        public static final int createprayerrequest = 113;
        public static final int listPrayRequest = 114;
        public static final int addTestimony = 115;
        public static final int listTestimony = 116;
        public static final int listOwnPrayRequest = 117;
        public static final int resetPassword = 120;
        public static final int updateProfileImage = 121;
        public static final int updateProfile = 124;
        public static final int liveVideo = 122;
        public static final int recentlyadded = 123;
        public static final int addprayecount = 125;
        public static final int addcomment = 126;
        public static final int commentlist = 127;
        public static final int selectedShowsDetails = 129;
        public static final int removePrayerRequest = 130;
        public static final int editPrayerRequest = 131;
        public static final int deviceRegister = 132;
        public static final int loadbanners = 133;
        public static final int listOwnTestimonials = 134;
        public static final int removeTestimony = 135;
        public static final int listNotification = 136;
        public static final int getScheduledShow = 137;
        public static final int listIntroVideos = 138;
        public static final int getLiveVideo = 139;

    }


    public class SharedKey {
        public static final String DEVICE_ID = "deviceId";
        public static final String FB_ID = "fb_id";
        public static final String USER_ID = "id";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "userEmail";
        public static final String LOGIN_STATUS = "loginStatus";
        public static final String PROFILEIMAGE = "image";
        public static final String ADDRESS = "address";
        public static final String FULL_NAME = "full_name";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String PROFILE_COMPLETE = "profile_complete";
        public static final String PROFILE_IMAGE = "image";
        public static final String PLACE = "place";

    }

    public class StatusCode {
               public static final String ALREADY_EXIST = "407";
        public static final String EXISTINGEMAIL = "402";
        public static final String INVALID_LOGIN = "406";
        public static final String SUCCESS = "200";
        public static final String ANOTHER_DEVICE = "405";
        public static final String INCOMPLETE_DATA = "408";
        public static final String NO_DATA_AVAILABLE = "400";
        public static final String PASSWORD_NOT_MATCH = "410";
        public static final String INVALID_EMAIL = "404";
        public static final String RESET_NOT_FOUND = "411";
        public static final String SUCCESS_RESET = "412";
        public static final String RESET_FAILED = "413";
    }
}
