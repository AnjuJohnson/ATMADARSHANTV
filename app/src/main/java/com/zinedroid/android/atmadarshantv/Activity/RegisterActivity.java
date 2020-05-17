package com.zinedroid.android.atmadarshantv.Activity;

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
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.utils.CircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements WebserviceCall.WebServiceCall {
    TextView mLogin, mCreateAccount;
    EditText mFullName, mMobileNumber, mAddress, mEmail, mUsername, mPassword, mConfirmPassword,mPlace;
    ImageView mProfileImage;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    File file;
    int imagestatus = 0;
    CircleImageView mImageViewCircleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupview();
        functions();
    }

    public void setupview() {
        mPlace = findViewById(R.id.place);
        mFullName = findViewById(R.id.fullname);
        mMobileNumber = findViewById(R.id.mobilenumber);
        mAddress = findViewById(R.id.address);
        mEmail = findViewById(R.id.email);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mProfileImage = findViewById(R.id.uploadimage);
        mImageViewCircleImageView = findViewById(R.id.image);
        mLogin = findViewById(R.id.login);
        mCreateAccount = findViewById(R.id.create_account);
    }

    public void functions() {
        mConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mPassword.getText().length() < 6) {
                        mPassword.setError("Min 6 characters");
                    }
                }
            }
        });
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processVerification();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegisterActivity.this);

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
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytesss = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytesss);
                String picturePath = getPath(getApplicationContext().getApplicationContext(), selectedImageUri);
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
        mProfileImage.setVisibility(View.GONE);
        mImageViewCircleImageView.setVisibility(View.VISIBLE);
        Glide.with(this).load(urlProfileImg)
                .placeholder(R.drawable.noimage)
                .crossFade()
                .thumbnail(0.5f)
                .dontAnimate()
                .bitmapTransform(new CircleTransform(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageViewCircleImageView);

    }

    private void processVerification() {
        String FullName = mFullName.getText().toString();
        String MobileNumber = mMobileNumber.getText().toString();
   //     String Address = mAddress.getText().toString();
        String email = mEmail.getText().toString();
        String UserName = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String place = mPlace.getText().toString();
        String cnfPassword = mConfirmPassword.getText().toString();
        Log.d("username length", String.valueOf(UserName.length()));
        if (isNetworkAvailable()) {
            if (FullName.length() > 3) {
                if (isValidEmail(email)) {

                    if(UserName.length()>3){
                        if (password.length() > 5) {
                            if (password.equalsIgnoreCase(cnfPassword)) {

                                if (isNetworkAvailable()) {
                                    if (imagestatus == 1) {
                                        new WebserviceCall(RegisterActivity.this, AppConstants.Methods.register_image).execute(FullName,MobileNumber, UserName, password,email,place, getSharedPreference(AppConstants.SharedKey.DEVICE_ID),file.getAbsolutePath());

                                    }
                                    else {
                                        new WebserviceCall(RegisterActivity.this, AppConstants.Methods.register).execute(FullName,MobileNumber, UserName, password,email,place, getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
                                    }
                                }



                            } else {
                                Snackbar.make(mCreateAccount, "Your Password doesnot match!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                mConfirmPassword.setError("Password Missmatch");

                            }
                        } else {
                            Snackbar.make(mCreateAccount, "Password must be min 6 characters", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }


                    }
                    else {
                        Snackbar.make(mCreateAccount, "Please enter more than 3 characters", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mUsername.setError("More than 3 characters needed");

                    }




                } else {
                    Snackbar.make(mCreateAccount, "Please enter a valid email", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mEmail.setError("invalid email");
                }
            } else {
                Snackbar.make(mCreateAccount, "Please enter your Full name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFullName.setError("invalid Name");
            }
        } else {
            Snackbar.make(mCreateAccount, "Please Connect to the Internet and try aagain", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            switch (method) {




                case AppConstants.Methods.register:

                 //   Toast.makeText(RegisterActivity.this,"registerresponse"+mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE), Toast.LENGTH_LONG).show();


                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {

                     //   Toast.makeText(getApplicationContext(),"Successfully Registered!",Toast.LENGTH_LONG).show();
                        Snackbar.make(mCreateAccount, "Successfully Registered!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        this.finish();
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.EXISTINGEMAIL)) {
                        Snackbar.make(mCreateAccount, "This Email already exists. Please try with another E mail!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    break;
                case AppConstants.Methods.register_image:

                //    Toast.makeText(RegisterActivity.this,"registerimageresponse"+mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE), Toast.LENGTH_LONG).show();


                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        Toast.makeText(getApplicationContext(),"Successfully Registered!",Toast.LENGTH_LONG).show();
                      /*  Snackbar.make(mCreateAccount, "Successfully Registered!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();*/
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        this.finish();
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.EXISTINGEMAIL)) {
                        Snackbar.make(mCreateAccount, "This Email already exists. Please try with another E mail!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception l){
            l.printStackTrace();
        }
    }
}

