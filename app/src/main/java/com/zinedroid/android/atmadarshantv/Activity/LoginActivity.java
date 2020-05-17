package com.zinedroid.android.atmadarshantv.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends BaseActivity implements WebserviceCall.WebServiceCall {
    TextView mLogin, mREgister;
    ImageView mVisibleIconImageView, mVisibleNotIconImageView;
    EditText mUsername, mPassword,userInputphone;
    String mUserName, mPassWord;
    private ImageView fbLoginButton;
    private CallbackManager callbackManager;
    String fb_id = null, userName = null, email = null;
    String imageUrl, phone, mail;
    TextView mForgetPasswordTextView;
    DialogInterface mDialog;
    AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = findViewById(R.id.fbLoginButton);
        mLogin = findViewById(R.id.login);
        mREgister = findViewById(R.id.register);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mVisibleIconImageView = findViewById(R.id.mVisibleIconImageView);
        mVisibleNotIconImageView = findViewById(R.id.mVisibleNotIconImageView);


        mVisibleIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mVisibleNotIconImageView.setVisibility(View.VISIBLE);
            }
        });
        mVisibleNotIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mVisibleIconImageView.setVisibility(View.VISIBLE);
                mVisibleNotIconImageView.setVisibility(View.GONE);
            }
        });



        mForgetPasswordTextView = findViewById(R.id.mForgetPasswordTextView);

        mForgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(LoginActivity.this);
                View dialogView = li.inflate(R.layout.edit_email_dialog, null);

                alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setView(dialogView);
                TextView dialogtitle = dialogView.findViewById(R.id.dialogtitle);
                dialogtitle.setText("Reset Password?");
                userInputphone = dialogView
                        .findViewById(R.id.mPhoneNoEditText);
                alertDialogBuilder
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {


                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();

                                    }
                                })
                        .setPositiveButton("Reset Password",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        mDialog = dialog;
                                        if (isValidEmail(userInputphone.getText().toString())) {
                                            if (isNetworkAvailable()) {
                                                new WebserviceCall(LoginActivity.this, AppConstants.Methods.resetPassword).execute(userInputphone.getText().toString());
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Connect to the network and try again", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            userInputphone.setError("invalid email");
                                        }
                                    }
                                });
                alertDialogBuilder.show();
            }
        });
////////////////
fbLoginButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(
                LoginActivity.this,
                Arrays.asList( "email",  "public_profile")
        );
    }
});

        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("onSuccess", "--------" + loginResult.getAccessToken());
                        Log.e("Token", "--------" + loginResult.getAccessToken().getToken());
                        Log.e("Permision", "--------" + loginResult.getRecentlyGrantedPermissions());
                        Log.e("OnGraph", "------------------------");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.e("GraphResponse", "-------------" + response.toString());
                                        Log.e("fb_json", "-------------" + object.toString());
                                        try {

                                            if (object.has("id")) {
                                                fb_id = object.getString("id");
                                            }
                                            if (object.has("name")) {
                                                userName = object.getString("name");
                                            }
                                            if (object.has("email")) {
                                                email = object.getString("email");
                                            }
                                            setSharedPreference(AppConstants.SharedKey.FB_ID, fb_id);
                                            imageUrl = "http://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
                                            setSharedPreference(AppConstants.SharedKey.PROFILE_IMAGE, imageUrl);
                                            Log.e("profileImage", imageUrl);

                                            new WebserviceCall(LoginActivity.this, AppConstants.Methods.fbLOgin).execute(email, fb_id, userName, getSharedPreference(AppConstants.SharedKey.DEVICE_ID), "", imageUrl);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,gender,birthday,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                      //  Log.e(TAG, "Name: " + userName );

                    }

                    @Override
                    public void onCancel() {
                        Snackbar.make(fbLoginButton, "Facebook Login Cancelled", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Snackbar.make(fbLoginButton, "No Internet, Try Again", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );




















/*


        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("onSuccess", "--------" + loginResult.getAccessToken());
                Log.e("Token", "--------" + loginResult.getAccessToken().getToken());
                Log.e("Permision", "--------" + loginResult.getRecentlyGrantedPermissions());
                Log.e("OnGraph", "------------------------");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.e("GraphResponse", "-------------" + response.toString());
                                Log.e("fb_json", "-------------" + object.toString());
                                try {

                                    if (object.has("id")) {
                                        fb_id = object.getString("id");
                                    }
                                    if (object.has("name")) {
                                        userName = object.getString("name");
                                    }
                                    if (object.has("email")) {
                                        email = object.getString("email");
                                    }
                                    setSharedPreference(AppConstants.SharedKey.FB_ID, fb_id);
                                    imageUrl = "http://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
                                    setSharedPreference(AppConstants.SharedKey.PROFILE_IMAGE, imageUrl);
                                    Log.e("profileImage", imageUrl);

                                    new WebserviceCall(LoginActivity.this, AppConstants.Methods.fbLOgin).execute(email, fb_id, userName, getSharedPreference(AppConstants.SharedKey.DEVICE_ID), "", imageUrl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Snackbar.make(fbLoginButton, "Facebook Login Cancelled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Snackbar.make(fbLoginButton, "No Internet, Try Again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPassWord = mPassword.getText().toString();
                Log.d("password", mPassWord);
                mUserName = mUsername.getText().toString();
                if (isNetworkAvailable()) {
                    if (!(mUserName.equalsIgnoreCase(""))) {
                        if (!(mPassWord.equals(""))) {
                            new WebserviceCall(LoginActivity.this, AppConstants.Methods.login).execute(mUserName, mPassWord, getSharedPreference(AppConstants.SharedKey.DEVICE_ID), "1");
                        } else {
                            Snackbar.make(v, "Password field cannot left blank!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            mPassword.setError("Enter password");
                        }
                    } else {
                        Snackbar.make(v, "Please enter your email!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mUsername.setError("Enter E mail");
                    }
                } else {
                    Snackbar.make(v, "Please connect to the Internet and try again!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        mREgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {
        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.login:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.INVALID_LOGIN)) {

                        //Toast.makeText(getApplicationContext(),"Username or Password is incorrect",Toast.LENGTH_LONG).show();
                        Snackbar.make(mLogin, "Username or Password is incorrect", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        loginSuccess(mJsonObject);


                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.ANOTHER_DEVICE)) {
                        Snackbar.make(mLogin, "Try again later", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                       // verifyPushLogin(method);
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.INVALID_EMAIL)) {
                        Snackbar.make(mLogin, "Invalid username", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.ALREADY_EXIST)) {

                        Snackbar.make(mLogin, "This email already exists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        //verifyPushLogin(method);
                    }


                    break;
                case AppConstants.Methods.fbLOgin:
                 //   Toast.makeText(LoginActivity.this, "fbloginresponse"+mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE), Toast.LENGTH_LONG).show();



                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        loginSuccess(mJsonObject);
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.ANOTHER_DEVICE)) {
                        Snackbar.make(mLogin, "Try again later", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                       // verifyPushLogin(method);
                    }
                    else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.ALREADY_EXIST)) {

                        Snackbar.make(mLogin, "This email already exists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        //verifyPushLogin(method);
                    }
                    break;
                case AppConstants.Methods.pushLogin:
                    loginSuccess(mJsonObject);

                    break;
                case AppConstants.Methods.pushFbLogin:
                    loginSuccess(mJsonObject);

                    break;
                case AppConstants.Methods.resetPassword:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS_RESET)) {
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Successfully send your password to you given email", Toast.LENGTH_LONG).show();
                    } else if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.RESET_NOT_FOUND)) {
                        Toast.makeText(LoginActivity.this, "The given mail Id is not registered", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Password couldn't send to your given email", Toast.LENGTH_LONG).show();
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

    private void verifyPushLogin(final int method) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("AtmadarshanTv Alert!");
        alertDialogBuilder.setMessage("Your email Id is registered in another Device. If you continue you will not be able to use " + getString(R.string.app_name) + " in the first device.\nAre you Sure want to continue?");
        alertDialogBuilder.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (method == AppConstants.Methods.login) {
                            new WebserviceCall(LoginActivity.this, AppConstants.Methods.pushLogin).execute(mUserName, mPassWord, getSharedPreference(AppConstants.SharedKey.DEVICE_ID));
                        } else {
                            new WebserviceCall(LoginActivity.this, AppConstants.Methods.pushFbLogin).execute(email, fb_id, userName, getSharedPreference(AppConstants.SharedKey.DEVICE_ID), "", imageUrl);
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void loginSuccess(final JSONObject mJsonObject) {
        try {
            Snackbar.make(mLogin, "Successfully Loged in!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            setSharedPreference(AppConstants.SharedKey.USER_ID, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.ID));
            setSharedPreference(AppConstants.SharedKey.USER_NAME, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.USER_NAME));
            setSharedPreference(AppConstants.SharedKey.FULL_NAME, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.FULLNAME));
            setSharedPreference(AppConstants.SharedKey.USER_EMAIL, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.EMAIL));
            setSharedPreference(AppConstants.SharedKey.PHONE_NUMBER, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PHONE_NO));
            setSharedPreference(AppConstants.SharedKey.ADDRESS, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PROFILE_ADDRESS));
            setSharedPreference(AppConstants.SharedKey.PROFILEIMAGE, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.IMAGE));
               setSharedPreference(AppConstants.SharedKey.PLACE, mJsonObject.getJSONObject(AppConstants.APIKeys.USER_DETAILS).getString(AppConstants.APIKeys.PLACE));
            setSharedPreference(AppConstants.SharedKey.LOGIN_STATUS, "true");
            Log.d("status", getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS));
            Intent i=new Intent(LoginActivity.this, HomeActivity.class);
            i.putExtra("PUSHNOTIFICATION_MESSAGE", "HOMENOTIFICATION");
            startActivity(i);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
