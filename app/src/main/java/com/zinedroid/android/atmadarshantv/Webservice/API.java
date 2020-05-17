package com.zinedroid.android.atmadarshantv.Webservice;

import com.google.gson.JsonObject;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public interface API {

    @FormUrlEncoded
    @POST("/login")
    JsonObject login(@Field(AppConstants.APIKeys.EMAIL) String email_id,
                     @Field(AppConstants.APIKeys.PASSWORD) String password,
                     @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                     @Field(AppConstants.APIKeys.LOG_STATUS) String status);

    @FormUrlEncoded
    @POST("/login")
    JsonObject PushLogin(@Field(AppConstants.APIKeys.EMAIL) String email_id,
                         @Field(AppConstants.APIKeys.PASSWORD) String password,
                         @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                         @Field(AppConstants.APIKeys.LOG_STATUS) String status);

    @FormUrlEncoded
    @POST("/listCategory")
    JsonObject listcategory(@Field(AppConstants.APIKeys.USER_ID) String userId,
                            @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listRecentlyAddedVideo")
    JsonObject recentlyadded(@Field(AppConstants.APIKeys.USER_ID) String userId,
                             @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listShowsName")
    JsonObject differentshows(@Field(AppConstants.APIKeys.USER_ID) String userId,
                              @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listOwnPrayRequest")
    JsonObject listOwnPrayRequest(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                  @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);


    @FormUrlEncoded
    @POST("/listOwnTestimonials")
    JsonObject listOwnTestimonials(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                   @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listNotification")
    JsonObject listNotification(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);


    @FormUrlEncoded
    @POST("/ListChannel")
    JsonObject channellist(@Field(AppConstants.APIKeys.USER_ID) String userId,
                           @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/ListFavourites")
    JsonObject ListFavourites(@Field(AppConstants.APIKeys.USER_ID) String userId,
                              @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listPrayRequest")
    JsonObject listPrayRequest(@Field(AppConstants.APIKeys.USER_ID) String userId,
                               @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/ListBanners")
    JsonObject loadbanners(@Field(AppConstants.APIKeys.USER_ID) String userId,
                           @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listIntroVideos")
    JsonObject listIntroVideos(@Field(AppConstants.APIKeys.USER_ID) String userId,
                           @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listTestimony")
    JsonObject listTestimony(@Field(AppConstants.APIKeys.USER_ID) String userId,
                             @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/listVideos")
    JsonObject listvideos(@Field(AppConstants.APIKeys.USER_ID) String userId,
                          @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                          @Field(AppConstants.APIKeys.CATGORYY_ID) String category_id);

    @FormUrlEncoded
    @POST("/listComments")
    JsonObject listComments(@Field(AppConstants.APIKeys.USER_ID) String userId,
                            @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                            @Field(AppConstants.APIKeys.TESTIMONY_ID) String category_id);

    @FormUrlEncoded
    @POST("/addPrayerCount")
    JsonObject addprayecount(@Field(AppConstants.APIKeys.USER_ID) String userId,
                             @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                             @Field(AppConstants.APIKeys.REQUSET_ID) String req_id);



    @FormUrlEncoded
    @POST("/removePrayerRequest")
    JsonObject removePrayerRequest(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                   @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                   @Field(AppConstants.APIKeys.REQUSET_ID) String req_id);

    @FormUrlEncoded
    @POST("/removeTestimony")
    JsonObject removeTestimony(@Field(AppConstants.APIKeys.USER_ID) String userId,
                               @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                               @Field(AppConstants.APIKeys.ID) String req_id);



    @FormUrlEncoded
    @POST("/editPrayerRequest")
    JsonObject editPrayerRequest(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                 @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                 @Field(AppConstants.APIKeys.REQUSET_ID) String req_id,
                                 @Field(AppConstants.APIKeys.TITLE) String titile,
                                 @Field(AppConstants.APIKeys.DISCRIPTION) String discription);


    @FormUrlEncoded
    @POST("/ListProgramSchedule")
    JsonObject ListProgramSchedule(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                   @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                   @Field(AppConstants.APIKeys.DATE) String category_id);

    @FormUrlEncoded
    @POST("/selectedShowsDetails")
    JsonObject selectedShowsDetails(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                    @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                    @Field(AppConstants.APIKeys.SHOW_ID) String category_id);

    @FormUrlEncoded
    @POST("/getScheduledShow")
    JsonObject getScheduledShow(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                    @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                    @Field(AppConstants.APIKeys.SHOW) String show);


    @FormUrlEncoded
    @POST("/addFavourites")
    JsonObject addtofavourites(@Field(AppConstants.APIKeys.USER_ID) String userId,
                               @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                               @Field(AppConstants.APIKeys.VIDEO_ID) String videoid,
                               @Field(AppConstants.APIKeys.VIDEO_DETAIL) String videodetail);

    @FormUrlEncoded
    @POST("/removeFavourites")
    JsonObject removeFavourites(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                @Field(AppConstants.APIKeys.VIDEO_ID) String videoid,
                                @Field(AppConstants.APIKeys.VIDEO_DETAIL) String videodetail);

    @FormUrlEncoded
    @POST("/addComment")
    JsonObject addcomment(@Field(AppConstants.APIKeys.USER_ID) String userId,
                          @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                          @Field(AppConstants.APIKeys.TESTIMONY_ID) String videoid,
                          @Field(AppConstants.APIKeys.COMMENT) String videodetail);

    @FormUrlEncoded
    @POST("/addPrayerRequest")
    JsonObject createprayerrequest(@Field(AppConstants.APIKeys.USER_ID) String userId,
                                   @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                   @Field(AppConstants.APIKeys.TITLE) String title,
                                   @Field(AppConstants.APIKeys.REQUEST) String request);


    @FormUrlEncoded
    @POST("/deviceRegister")
    JsonObject deviceRegister(@Field(AppConstants.APIKeys.DEVICE_ID) String deviceId
    );

    @FormUrlEncoded
    @POST("/addTestimony")
    JsonObject addTestimony(@Field(AppConstants.APIKeys.USER_ID) String userId,
                            @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                            @Field(AppConstants.APIKeys.TITLE) String title,
                            @Field(AppConstants.APIKeys.DISCRIPTION) String request);

    @FormUrlEncoded
    @POST("/register")
    JsonObject register(
            @Field(AppConstants.APIKeys.FULLNAME) String fullname,
            @Field(AppConstants.APIKeys.PHONE_NO) String phonenumber,
            @Field(AppConstants.APIKeys.USERNAME) String userName,
            @Field(AppConstants.APIKeys.PASSWORD) String password,
            @Field(AppConstants.APIKeys.EMAIL) String email_id,
            @Field(AppConstants.APIKeys.PLACE) String place,
            @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);


    @Multipart
    @POST("/register")
    JsonObject register_image(
            @Part(AppConstants.APIKeys.FULLNAME) String fullname,
            @Part(AppConstants.APIKeys.PHONE_NO) String phonenumber,
            @Part(AppConstants.APIKeys.USERNAME) String userName,
            @Part(AppConstants.APIKeys.PASSWORD) String password,
            @Part(AppConstants.APIKeys.EMAIL) String email_id,
            @Part(AppConstants.APIKeys.PLACE) String place,
            @Part(AppConstants.APIKeys.DEVICE_ID) String deviceId,
            @Part(AppConstants.APIKeys.IMAGE) TypedFile image);

    @FormUrlEncoded
    @POST("/fbLogin")
    JsonObject fbLogin(@Field(AppConstants.APIKeys.EMAIL) String email_id,
                       @Field(AppConstants.APIKeys.FB_ID) String fbId,
                       @Field(AppConstants.APIKeys.NAME) String password,
                       @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                       @Field(AppConstants.APIKeys.PHONE_NO) String phoneNo,
                       @Field(AppConstants.APIKeys.IMAGE_URL) String imageUrl,
                       @Field(AppConstants.APIKeys.LOG_STATUS) String status);

    @FormUrlEncoded
    @POST("/fbLogin")
    JsonObject pushFbLogin(@Field(AppConstants.APIKeys.EMAIL) String email_id,
                           @Field(AppConstants.APIKeys.FB_ID) String fbId,
                           @Field(AppConstants.APIKeys.NAME) String password,
                           @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                           @Field(AppConstants.APIKeys.PHONE_NO) String phoneNo,
                           @Field(AppConstants.APIKeys.IMAGE_URL) String imageUrl,
                           @Field(AppConstants.APIKeys.LOG_STATUS) String status);


    /*  @Multipart
      @POST("/insert.php")
      JsonObject uploadImages(@Part("image") TypedFile image,
                              @Part("uniq_name") String name,
                              @Part("folder_name") String classId);
  */
/*
    @FormUrlEncoded
    @POST("/UpdateProfile")
    JsonObject updateprofileee(@Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                               @Field(AppConstants.APIKeys.USER_ID) String userId,
                               @Field(AppConstants.APIKeys.USERNAME) String username,
                               @Field(AppConstants.APIKeys.EMAIL) String Type,
                               @Field(AppConstants.APIKeys.PHONE_NUMBER) String Promocode,
                               @Field(AppConstants.APIKeys.ADDRESS) String amount,
                               @Field(AppConstants.APIKeys.CURRENT_PASSWORD) String TransactionId,
                               @Field(AppConstants.APIKeys.NEW_PASSWORD) String orderid);*/
    @Multipart
    @POST("/UpdateProfile")
    JsonObject updateProfileImage(@Part(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                                  @Part(AppConstants.APIKeys.USER_ID) String userId,
                                  @Part(AppConstants.APIKeys.USERNAME) String username,
                                  @Part(AppConstants.APIKeys.EMAIL) String Type,
                                  @Part(AppConstants.APIKeys.PHONE_NO) String Promocode,
                                  @Part(AppConstants.APIKeys.ADDRESS) String amount,
                                  @Part(AppConstants.APIKeys.CURRENT_PASSWORD) String current,
                                  @Part(AppConstants.APIKeys.NEW_PASSWORD) String newpass,
                                  @Part(AppConstants.APIKeys.PLACE) String place,
                                  @Part(AppConstants.APIKeys.IMAGE) TypedFile image);
    @FormUrlEncoded
    @POST("/ListLiveVideos")
    JsonObject getlivevideo(@Field(AppConstants.APIKeys.USER_ID) String userId,
                            @Field(AppConstants.APIKeys.DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST("/UpdateProfile")
    JsonObject updateProfile(@Field(AppConstants.APIKeys.DEVICE_ID) String deviceId,
                             @Field(AppConstants.APIKeys.USER_ID) String userId,
                             @Field(AppConstants.APIKeys.USERNAME) String username,
                             @Field(AppConstants.APIKeys.EMAIL) String Type,
                             @Field(AppConstants.APIKeys.PHONE_NO) String Promocode,
                             @Field(AppConstants.APIKeys.ADDRESS) String amount,
                             @Field(AppConstants.APIKeys.CURRENT_PASSWORD) String current,
                             @Field(AppConstants.APIKeys.NEW_PASSWORD) String newpass,
                             @Field(AppConstants.APIKeys.PLACE) String place);


    @FormUrlEncoded
    @POST("/forgotPassword")
    JsonObject forgetPassword(@Field(AppConstants.APIKeys.EMAIL) String email);

    /*  @GET("/search?part=snippet&channelId=UCpcA8YdNdsPE2tPnezqL56w&type=video&eventType=live&key=AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c")
      JsonObject livevideo();*/
  /*  @GET("/search?part=snippet&channelId=UC_nG0qNQTzEtfQia_BM8WgA&type=video&eventType=live&key=AIzaSyA9Kka548d7RZYVBypAy4UWgvDvbUCP16c")
    JsonObject livevideo();*/


    @GET("/search?part=snippet&channelId=UC_nG0qNQTzEtfQia_BM8WgA&type=video&eventType=live&key=AIzaSyBRGlXm2sXMbqiqoVIoF0WY-ZyFg-zzyJI")
    JsonObject livevideo();

   /* @GET("/search?part=snippet&channelId=UC_nG0qNQTzEtfQia_BM8WgA&type=video&eventType=live&key=AIzaSyBRGlXm2sXMbqiqoVIoF0WY-ZyFg-zzyJI")
    String livevideo();
*/
}
