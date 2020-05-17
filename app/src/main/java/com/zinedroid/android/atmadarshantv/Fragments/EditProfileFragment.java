package com.zinedroid.android.atmadarshantv.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.utils.CircleTransform;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cecil Paul on 11/9/18.
 */
public class EditProfileFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    TextView mSubmit, mChangepass,mDeactivateTextView;
    CircleImageView mImage, mBanner;
    EditText mFullNameEditText, mPhoneNumberEditText, mEmailEditText, mCurrentPass, mNewPass, mAddress,mPlace;
    String mMobileNo,urlProfileImg;
    int imagestatus = 0;
    LinearLayout mLinearLayout;
    LinearLayout mInvisiblelayout;
    //  MultipartBody.Part body;
    ImageView mUploadImage;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    File file;
    String currentpassword,newpassword;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.activity_profile_edit, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }
    public void mainFunction(View mFragmentView ){
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange= (Utility.menuIconChange) getActivity();
        mLinearLayout = mFragmentView. findViewById(R.id.mLinearLayout);
        mInvisiblelayout = mFragmentView.findViewById(R.id.invisiblelayouttt);
        mFullNameEditText = mFragmentView. findViewById(R.id.editname);
        mPhoneNumberEditText = mFragmentView.findViewById(R.id.Phonenumber);
        mEmailEditText = mFragmentView.findViewById(R.id.email);
        mAddress = mFragmentView.findViewById(R.id.address);
        mCurrentPass = mFragmentView. findViewById(R.id.currentpass);
        mPlace = mFragmentView. findViewById(R.id.place);
        mNewPass = mFragmentView.findViewById(R.id.newpass);
        mImage = mFragmentView. findViewById(R.id.image);
        mSubmit = mFragmentView. findViewById(R.id.submit);
        mChangepass = mFragmentView.findViewById(R.id.changepass);

        mUploadImage = (FloatingActionButton) mFragmentView.findViewById(R.id.fab);
        Functions();


    }


    public void Functions(){

        mChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInvisiblelayout.setVisibility(View.VISIBLE);
                mChangepass.setVisibility(View.GONE);
            }
        });
        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        //image
        if (getSharedPreference(getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE)) != "DEFAULT") {
            urlProfileImg = "http://graph.facebook.com/"
                    + getSharedPreference(AppConstants.SharedKey.FB_ID) + "/picture?type=large";

        } else if (getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE) != ("DEFAULT")) {
            urlProfileImg = getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE);
            Log.d("kkkk", urlProfileImg);
        }
        loadimage(urlProfileImg);

        //username
        if (getSharedPreference(AppConstants.SharedKey.USER_NAME) != ("DEFAULT")) {
            try {
                mFullNameEditText.setText(getSharedPreference(AppConstants.SharedKey.USER_NAME));
            } catch (Exception e) {
                e.printStackTrace();
                mFullNameEditText.setText(getSharedPreference(AppConstants.SharedKey.USER_NAME));
            }

        } else {
            mFullNameEditText.setText("");
        }
        //email
        if (getSharedPreference(AppConstants.SharedKey.USER_EMAIL) != ("DEFAULT")) {
            try {
                mEmailEditText.setText(getSharedPreference(AppConstants.SharedKey.USER_EMAIL));
            } catch (Exception e) {
                e.printStackTrace();
                mEmailEditText.setText(getSharedPreference(AppConstants.SharedKey.USER_EMAIL));
            }

        } else {
            mEmailEditText.setText("");
        }

//phone number
        mMobileNo = getSharedPreference(AppConstants.SharedKey.PHONE_NUMBER);
        Log.d("phonstatus", mMobileNo);
        if ((mMobileNo != ("DEFAULT")) && (!mMobileNo.equals("0")) && (!mMobileNo.equals("null"))) {
            mPhoneNumberEditText.setText(mMobileNo);
        } else {
            mPhoneNumberEditText.setText("");
            mPhoneNumberEditText.setHint("Mobile No");
        }
        //aDDRESS
        if ((getSharedPreference(AppConstants.SharedKey.ADDRESS) != ("DEFAULT")) && (!getSharedPreference(AppConstants.SharedKey.ADDRESS).equals("null"))) {
            try {
                mAddress.setText(getSharedPreference(AppConstants.SharedKey.ADDRESS));
            } catch (Exception e) {
                e.printStackTrace();
                mAddress.setText(getSharedPreference(AppConstants.SharedKey.ADDRESS));
            }

        } else {
            mAddress.setText("");
        }

        //place


        if ((getSharedPreference(AppConstants.SharedKey.PLACE) != ("DEFAULT")) && (!getSharedPreference(AppConstants.SharedKey.PLACE).equals("null"))) {
            try {
                mPlace.setText(getSharedPreference(AppConstants.SharedKey.PLACE));
            } catch (Exception e) {
                e.printStackTrace();
                mPlace.setText(getSharedPreference(AppConstants.SharedKey.PLACE));
            }

        } else {
            mPlace.setText("");
        }




        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(mEmailEditText.getText().toString())) {
                    mEmailEditText.setError("Invalid Email Adddress");
                    Log.d("errorrr", "errorr");
                } else if (!isValidPhoneNumber(mPhoneNumberEditText.getText().toString())) {
                    mPhoneNumberEditText.setError("Invalid Phone Number");
                    Log.d("ooooo", mPhoneNumberEditText.getText().toString());
                    Log.d("hhh", "hhh");
                } else {
                    if (isNetworkAvailable()) {
                        if (imagestatus == 1) {
                            new WebserviceCall(EditProfileFragment.this, AppConstants.Methods.updateProfileImage).execute(getSharedPreference(AppConstants.SharedKey.DEVICE_ID), getSharedPreference(AppConstants.SharedKey.USER_ID), mFullNameEditText.getText().toString(), mEmailEditText.getText().toString(), mPhoneNumberEditText.getText().toString(), mAddress.getText().toString(), mCurrentPass.getText().toString().trim(),mNewPass.getText().toString().trim(),mPlace.getText().toString(),file.getAbsolutePath());
                        } else {
                            Log.d("hhhhh","ssssss");
                            new WebserviceCall(EditProfileFragment.this, AppConstants.Methods.updateProfile).execute(getSharedPreference(AppConstants.SharedKey.DEVICE_ID), getSharedPreference(AppConstants.SharedKey.USER_ID), mFullNameEditText.getText().toString(), mEmailEditText.getText().toString(), mPhoneNumberEditText.getText().toString(), mAddress.getText().toString(),mCurrentPass.getText().toString().trim(),mNewPass.getText().toString().trim(),mPlace.getText().toString());
                        }
                    } else {
                        Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(intent, SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        imagestatus = 1;
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  uploadimage(bytearray);
        loadimage(file.getAbsolutePath());
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                Uri selectedImageUri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                ByteArrayOutputStream bytesss = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytesss);
                String picturePath = getPath(getActivity(), selectedImageUri);
                Log.d("Picture Path++", picturePath);

                file = new File(picturePath);
                imagestatus = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadimage(file.getAbsolutePath());
    }
    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }
    // Loading profile image
    public void loadimage(String urlProfileImg) {
        Glide.with(this).load(urlProfileImg)
                .placeholder(R.drawable.noimage)
                .crossFade()
                .thumbnail(0.5f)
                .dontAnimate()
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImage);

    }


    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.updateProfileImage:
                    if (mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        Toast.makeText(getActivity(),"Your Profile Updated Successfully!",Toast.LENGTH_LONG).show();
                       /* Snackbar.make(mLinearLayout, "Your Profile Updated Successfully!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();*/
                        setSharedPreference(AppConstants.SharedKey.PROFILEIMAGE, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.IMAGE));
                        setSharedPreference(AppConstants.SharedKey.ADDRESS, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PROFILE_ADDRESS));
                        setSharedPreference(AppConstants.SharedKey.PHONE_NUMBER, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PHONE_NO));
                        setSharedPreference(AppConstants.SharedKey.USER_NAME, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.USER_NAME));

                    }
                    break;
                case AppConstants.Methods.updateProfile:

                    try{



                        if (mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {

                            Toast.makeText(getActivity(),"Your Profile Updated Successfully!",Toast.LENGTH_LONG).show();


                           /* Snackbar.make(mLinearLayout, "Your Profile Updated Successfully!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            setSharedPreference(AppConstants.SharedKey.ADDRESS, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PROFILE_ADDRESS));
                            setSharedPreference(AppConstants.SharedKey.PHONE_NUMBER, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PHONE_NO));

                            setSharedPreference(AppConstants.SharedKey.USER_NAME, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.USER_NAME));


                        }


                    }catch (JSONException J){

                    }

                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.PASSWORD_NOT_MATCH)){
                        Snackbar.make(mLinearLayout, "Current Password is wrong", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Edit Profile ");
        OnMenuIconChange.iconchange(EditProfileFragment.this);
        HomeActivity.fragment = EditProfileFragment.this;
    }
}
