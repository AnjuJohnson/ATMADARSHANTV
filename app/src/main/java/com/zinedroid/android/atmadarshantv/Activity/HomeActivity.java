package com.zinedroid.android.atmadarshantv.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.orm.SugarRecord;
import com.zinedroid.android.atmadarshantv.Base.BaseActivity;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.Fragments.AboutUsFragment;
import com.zinedroid.android.atmadarshantv.Fragments.CategoriesFragment;
import com.zinedroid.android.atmadarshantv.Fragments.Daily_word_Fragment;
import com.zinedroid.android.atmadarshantv.Fragments.DifferentShowsNewFragment;
import com.zinedroid.android.atmadarshantv.Fragments.EditProfileFragment;
import com.zinedroid.android.atmadarshantv.Fragments.FacebookLikeFragment;
import com.zinedroid.android.atmadarshantv.Fragments.FavouritesList;
import com.zinedroid.android.atmadarshantv.Fragments.HomeFragment;
import com.zinedroid.android.atmadarshantv.Fragments.MyPrayRequestFragment;
import com.zinedroid.android.atmadarshantv.Fragments.MyProfileFragment;
import com.zinedroid.android.atmadarshantv.Fragments.MyTestimonyFragment;
import com.zinedroid.android.atmadarshantv.Fragments.OurPublicationsFragment;
import com.zinedroid.android.atmadarshantv.Fragments.PrayerRequestFragment;
import com.zinedroid.android.atmadarshantv.Fragments.TodayScheduleFragment;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.models.Video;
import com.zinedroid.android.atmadarshantv.utils.BottomNavigationViewHelper;
import com.zinedroid.android.atmadarshantv.utils.CircleTransform;
import com.zinedroid.android.atmadarshantv.utils.Config;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Config.OnFragmnetSwitch, Utility.menuIconChange {

    Toolbar toolbar;
    DrawerLayout drawer;
    String message, body, title;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private static final String TAG_HOME = "Atmadarshan TV";
    public static String CURRENT_TAG = TAG_HOME;
    private TextView mNameTextView, mTitleTextView;
    android.support.v4.app.FragmentManager mFragmentManager;
    ImageView mEdit, imageView;
    public static BaseFragment fragment;
    private View navHeader;
    private android.content.Context mContext;
    private android.widget.RelativeLayout mRelativeLayout;
    private android.widget.PopupWindow mPopupWindow;
    String urlProfileImg;
    ImageView mMenuIcon;
    ImageView mFbLikeView, refreshImageView;
    Profile profile = Profile.getCurrentProfile();
    BaseFragment baseFragmentt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setuoview();
        loadNavHeader();
        if (savedInstanceState == null) {
            //   navItemIndex = 0;
            //loading homefragmentSplashActivity

            Intent intent = getIntent();
            message = intent.getStringExtra("PUSHNOTIFICATION_MESSAGE");
            body = intent.getStringExtra("body");
            title = intent.getStringExtra("title");


            if (message.equalsIgnoreCase("MESSAGE")) {
                Log.d("pushnotification", "77777");
                ChangeFragment(new Daily_word_Fragment(HomeActivity.this), CURRENT_TAG, true);

            } else {
                Log.d("pushnotification", "44444");
                loadHomeFragment();
                CURRENT_TAG = TAG_HOME;
                ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);

            }


        }

    }


    private void loadHomeFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //   toggleFab();
            return;
        }


        // update the main content by replacing fragments
        BaseFragment fragment = new HomeFragment(HomeActivity.this);
        ChangeFragment(fragment, CURRENT_TAG, true);
        drawer.closeDrawers();

       /* if (fragment.getClass().getSimpleName().equalsIgnoreCase("CategoriesFragment")) {
            Log.d("CategoriesFragment", "categories");

        }
*/

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    public void setuoview() {
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitleTextView = findViewById(R.id.mTitleTextView);
        mEdit = navHeader.findViewById(R.id.edit);
        imageView = navHeader.findViewById(R.id.imageView);
        mNameTextView = navHeader.findViewById(R.id.name);
        drawer = findViewById(R.id.drawer_layout);
        fab = findViewById(R.id.fab);
        mRelativeLayout = findViewById(R.id.relative);
        mMenuIcon = findViewById(R.id.menu_icon);
        refreshImageView = findViewById(R.id.refreshImageView);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //  toolbar.setTitle("Home");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getApplicationContext() != null) {
                    // showPopup(view);
                    CURRENT_TAG = "Categories";
                    ChangeFragment(new CategoriesFragment(HomeActivity.this), CURRENT_TAG, true);

                }
            }
        });


        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
                    Log.d("HomeFragment", "HomeFragment");
                    ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);

                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DifferentShowsNewFragment")) {
                    ChangeFragment(new DifferentShowsNewFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TodayScheduleFragment")) {
                    ChangeFragment(new TodayScheduleFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("FavouritesList")) {


                    ChangeFragment(new FavouritesList(HomeActivity.this), CURRENT_TAG, true);
                } /*else if (fragment.getClass().getSimpleName().equalsIgnoreCase("LiveRadioFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        }*/ else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyProfileFragment")) {
                    ChangeFragment(new MyProfileFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PrayerRequestFragment")) {
                    ChangeFragment(new PrayerRequestFragment(HomeActivity.this, baseFragmentt), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("FacebookLikeFragment")) {
                    ChangeFragment(new FacebookLikeFragment(), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Daily_word_Fragment")) {
                    ChangeFragment(new Daily_word_Fragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AboutUsFragment")) {
                    ChangeFragment(new AboutUsFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CategoriesFragment")) {
                    ChangeFragment(new CategoriesFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("OurPublicationsFragment")) {
                    ChangeFragment(new OurPublicationsFragment(HomeActivity.this), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyPrayRequestFragment")) {
                    ChangeFragment(new MyPrayRequestFragment(HomeActivity.this, baseFragmentt), CURRENT_TAG, true);
                } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyTestimonyFragment")) {
                    ChangeFragment(new MyTestimonyFragment(HomeActivity.this, baseFragmentt), CURRENT_TAG, true);
                }
            }
        });




       /* mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });*/
    }

    private void loadNavHeader() {
        try {
            if (getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE) != "DEFAULT") {

                urlProfileImg = getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE);
                Log.d("imagefacebook", urlProfileImg);

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                    drawer.closeDrawer(GravityCompat.START);
                    android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(HomeActivity.this);

                    dlgAlert.setMessage("You need to login");
                    //       dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                                }
                            });
                    dlgAlert.create().show();

                } else {
                    drawer.closeDrawer(GravityCompat.START);
                    ChangeFragment(new EditProfileFragment(), "Edit Profile", true);
                }


             /*
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);*/
            }
        });


        updateHeader();
    }

    public void updateHeader() {
        if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
            Menu menu = navigationView.getMenu();
            MenuItem logout = menu.findItem(R.id.logout);
            logout.setTitle("Login");

        } else {
            Menu menu = navigationView.getMenu();
            MenuItem logout = menu.findItem(R.id.logout);
            logout.setTitle("Logout");

        }


        try {
            mNameTextView.setText(profile.getName());
            Log.d("8888888", getSharedPreference(AppConstants.SharedKey.USER_NAME));
        } catch (Exception e) {
            e.printStackTrace();


            if (!getSharedPreference(AppConstants.SharedKey.USER_NAME).equalsIgnoreCase("DEFAULT")) {
                Log.d("333333", getSharedPreference(AppConstants.SharedKey.USER_NAME));
                mNameTextView.setText(getSharedPreference(AppConstants.SharedKey.USER_NAME));
            } else {
                mNameTextView.setText("Guest");
            }


        }
        urlProfileImg = getSharedPreference(AppConstants.SharedKey.PROFILEIMAGE);
        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .placeholder(R.mipmap.user)
                .centerCrop()
                .crossFade()
                .thumbnail(0.5f)
                .dontAnimate()
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BaseFragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    //    toolbar.setTitle("Shop");
                    CURRENT_TAG = TAG_HOME;

                    ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
                    break;
                case R.id.different:

                    CURRENT_TAG = "Shows";
                    ChangeFragment(new DifferentShowsNewFragment(HomeActivity.this), CURRENT_TAG, true);
                    break;
                case R.id.watch_live:

                    CURRENT_TAG = "About Us";
                    ChangeFragment(new AboutUsFragment(HomeActivity.this), CURRENT_TAG, true);
                    break;
                case R.id.today_schedule:

                    CURRENT_TAG = "Today's Schedule";
                    ChangeFragment(new TodayScheduleFragment(HomeActivity.this), CURRENT_TAG, true);
                    break;
                /*case R.id.radio:

                    CURRENT_TAG = "Channel List";
                    ChangeFragment(new LiveRadioFragment(HomeActivity.this), CURRENT_TAG, true);
                    break;*/
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();

            }
            return true;
        }
    };


    @Override
    public void onBackPressed() {
        /*  drawer = (DrawerLayout) findViewById(R.id.drawer_layout);*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            new AlertDialog.Builder(HomeActivity.this)
                    .setMessage("Are you sure you want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            // this.finish();
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DifferentShowsNewFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TodayScheduleFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("FavouritesList")) {


            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } /*else if (fragment.getClass().getSimpleName().equalsIgnoreCase("LiveRadioFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        }*/ else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyProfileFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PrayerRequestFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("FacebookLikeFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Daily_word_Fragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("LiveVideoFragment")) {
            ChangeFragment(new HomeFragment(HomeActivity.this), CURRENT_TAG, true);
        } else {
            super.onBackPressed();
        }
    }

    /*   @Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.main, menu);
           return true;
       }

       @Override
       public boolean onOptionsItemSelected(MenuItem item) {
           // Handle action bar item clicks here. The action bar will
           // automatically handle clicks on the Home/Up button, so long
           // as you specify a parent activity in AndroidManifest.xml.
           int id = item.getItemId();

           //noinspection SimplifiableIfStatement
           if (id == R.id.action_settings) {
               return true;
           }

           return super.onOptionsItemSelected(item);
       }
   */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        BaseFragment baseFragment = null;
        int id = item.getItemId();

        if (id == R.id.watch_live) {

            CURRENT_TAG = "About Us";
            ChangeFragment(new AboutUsFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.differentshows) {

            CURRENT_TAG = "Different Shows";
            ChangeFragment(new DifferentShowsNewFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.today_schedule) {

            CURRENT_TAG = "Today's Schedule";
            ChangeFragment(new TodayScheduleFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.livevideo) {

            CURRENT_TAG = "Radio";
//            ChangeFragment(new LiveVideoFragment(HomeActivity.this), CURRENT_TAG, true);
            startActivity(new Intent(this, LiveVideoActivity.class));
        } else if (id == R.id.daily_word) {

            CURRENT_TAG = "Daily Word";
            ChangeFragment(new Daily_word_Fragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.profile) {

            if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                drawer.closeDrawer(GravityCompat.START);
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(HomeActivity.this);

                dlgAlert.setMessage("You need to login");
                //       dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }
                        });
                dlgAlert.create().show();

            } else {

                CURRENT_TAG = "My Profile";
                ChangeFragment(new MyProfileFragment(HomeActivity.this), CURRENT_TAG, true);

            }
        } else if (id == R.id.settings) {


            if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                drawer.closeDrawer(GravityCompat.START);
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(HomeActivity.this);

                dlgAlert.setMessage("You need to login");
                //       dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }
                        });
                dlgAlert.create().show();

            } else {
                CURRENT_TAG = "Settings";
                ChangeFragment(new FavouritesList(HomeActivity.this), CURRENT_TAG, true);
            }
        } else if (id == R.id.prayerrequest) {

            CURRENT_TAG = "Prayer Requests and Testimonies";
            ChangeFragment(new PrayerRequestFragment(HomeActivity.this, baseFragment), CURRENT_TAG, true);
        }/* else if (id == R.id.donate) {

            CURRENT_TAG = "Donate";
            ChangeFragment(new DonateFragment(HomeActivity.this), CURRENT_TAG, true);
        }
*/ else if (id == R.id.fblike) {
            CURRENT_TAG = "Like our Page";
            fragment = new FacebookLikeFragment();
            ChangeFragment(fragment, "Like Our Page", true);

        } else if (id == R.id.our_publications) {

            CURRENT_TAG = "Our Publications";
            ChangeFragment(new OurPublicationsFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.logout) {
            // mTitleTextView.setVisibility(View.VISIBLE);
            CURRENT_TAG = "Logout";
            if (!getSharedPreference(AppConstants.SharedKey.LOGIN_STATUS).equalsIgnoreCase("true")) {
                item.setTitle("Login");

                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();

               /* new AlertDialog.Builder(HomeActivity.this)
                        .setMessage("Are you sure you want to Exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();*/

            } else {
                item.setTitle("Logout");

                new AlertDialog.Builder(HomeActivity.this)
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SugarRecord.deleteAll(Video.class);
                                Video.deleteAll(Video.class);
                                LoginManager.getInstance().logOut();

                               /* SharedPreferences preferences =getSharedPreferences(AppConstants.SHARED_KEY,Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                finish();
*/
                                SharedPreferences settings = getSharedPreferences(AppConstants.SHARED_KEY, Context.MODE_PRIVATE);
                                settings.edit().clear().commit();

                                //   clearSharedPreference();

                                startActivity(new Intent(HomeActivity.this, SplashActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onFragmentChange(BaseFragment fragment, String title, boolean isReplace) {
        ChangeFragment(fragment, title, isReplace);
        CURRENT_TAG = title;
    }

    private void ChangeFragment(BaseFragment fragment, String title, boolean isReplace) {
        mTitleTextView.setText(title);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        if (isReplace) {
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        } else {
            fragmentTransaction.add(R.id.frame, fragment, CURRENT_TAG);
        }
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void loadTitle(String Title) {

        mTitleTextView.setText(Title);
    }

    @Override
    public void iconchange(BaseFragment fragment) {
        if (fragment.getClass().getSimpleName().equalsIgnoreCase("CategoriesFragment")) {
            Log.d("11111111111", "categories");
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_close_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragmentManager.popBackStack();
                }
            });

        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment")) {
            Log.d("homefragment", "homefragment");
            // fragment=new HomeFragment(HomeActivity.this,"home");
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);
                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DifferentShowsNewFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        }
       /* else if (fragment.getClass().getSimpleName().equalsIgnoreCase("NoInternetFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        }*/

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("OurPublicationsFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CategoryVideoListFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ChannelListFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CommentListFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CreateRequestFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Daily_word_Fragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DifferentshowItemFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("FavouritesList")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } /*else if (fragment.getClass().getSimpleName().equalsIgnoreCase("LiveRadioFragment")) {

            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } */ else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyProfileFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PrayerRequestFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("RadioPlayerFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TodayScheduleFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ViewAllFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("WriteCommentFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PrayerDiscriptionFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("EditProfileFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ViewAllRequest")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ViewAllTestimoniesFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CreateRequestFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CreateRequestFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyPrayRequestFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MyTestimonyFragment")) {
            refreshImageView.setVisibility(View.VISIBLE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("PrayerDiscriptionFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("LiveVideoFragment")) {
            refreshImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.ic_menu_black_24dp);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHeader();
                    loadNavHeader();
                    drawer.openDrawer(Gravity.START);

                }
            });
        }


    }
}
