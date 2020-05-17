package com.zinedroid.android.atmadarshantv.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.zinedroid.android.atmadarshantv.Activity.HomeActivity;
import com.zinedroid.android.atmadarshantv.Adapters.TodayScheduleAdapter;
import com.zinedroid.android.atmadarshantv.Base.BaseFragment;
import com.zinedroid.android.atmadarshantv.Common.AppConstants;
import com.zinedroid.android.atmadarshantv.Common.Utility;
import com.zinedroid.android.atmadarshantv.HorizontalCalendar.ZineCalendar;
import com.zinedroid.android.atmadarshantv.HorizontalCalendar.ZineCalendarListener;
import com.zinedroid.android.atmadarshantv.R;
import com.zinedroid.android.atmadarshantv.Webservice.WebserviceCall;
import com.zinedroid.android.atmadarshantv.models.TodaySchedule;
import com.zinedroid.android.atmadarshantv.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

@SuppressLint("ValidFragment")
public class TodayScheduleFragment extends BaseFragment implements WebserviceCall.WebServiceCall {
    Context context;
    YouTubePlayer youTubePlayer1, youTubePlayer2, youTubePlayer3;
    YouTubePlayerFragment youtube1, youtube2, youtube3;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<TodaySchedule> mTodaySchedule;
    RecyclerView.Adapter mDifferentshowVideoAdapter;
    HorizontalCalendar horizontalCalendar;
    ZineCalendar zineCalendar;
    LinearLayout mVisible;
    Config.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    @SuppressLint("ValidFragment")
    public TodayScheduleFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_todayschedule, container, false);
        mainFunction(mFragmentView);
        return mFragmentView;

    }

    public void mainFunction(View mFragmentView) {
        mOnFragmnetSwitch = (Config.OnFragmnetSwitch) getActivity();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mRecyclerView = mFragmentView.findViewById(R.id.differentshows_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVisible = mFragmentView.findViewById(R.id.visible);




        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 5);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -1);


        zineCalendar = new ZineCalendar.Builder(mFragmentView, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(10f, 12f, 10f)
                .showDayName(true)
                .showMonthName(false)
                .build();

        zineCalendar.setCalendarListener(new ZineCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                String selectedDateStr = android.text.format.DateFormat.format("yyyy-MM-dd", date).toString();

             //   Toast.makeText(getContext(), selectedDateStr + " is selected!", Toast.LENGTH_SHORT).show();

                if (isNetworkAvailable()) {
                    new WebserviceCall(TodayScheduleFragment.this, AppConstants.Methods.TodaySchedule).execute(getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                            selectedDateStr);
                }
                else {
                    Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
                }



           }

        });
    }

      /*  *//* end after 2 months from now *//*
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 3);
        horizontalCalendar = new HorizontalCalendar.Builder(mFragmentView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(4)
                .defaultSelectedDate(startDate)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = android.text.format.DateFormat.format("EEE, MMM d, yyyy", date).toString();
                //   Toast.makeText(MainActivity.this, selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                android.util.Log.i("onDateSelected", selectedDateStr + " - Position = " + position);

                new WebserviceCall(TodayScheduleFragment.this, AppConstants.Methods.listvideos).execute(new String[]{getSharedPreference(AppConstants.SharedKey.USER_ID), getSharedPreference(AppConstants.SharedKey.DEVICE_ID),
                        "1"
                });

            }

        });*/



    @Override
    public void onWebServiceCall(JSONObject mJsonObject, int method) {

        try {
            Log.e("JsonResponse", mJsonObject.toString());
            switch (method) {
                case AppConstants.Methods.TodaySchedule:
                    if (mJsonObject.getString(AppConstants.APIKeys.STATUS_CODE).equalsIgnoreCase(AppConstants.StatusCode.SUCCESS)) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mVisible.setVisibility(View.GONE);
                        mTodaySchedule = new ArrayList<>();
                        JSONArray mTodayscheduleJsonArray = mJsonObject.getJSONArray(AppConstants.APIKeys.PROGRAM);
                        for (int a = 0; a < mTodayscheduleJsonArray.length(); a++) {

                            TodaySchedule todaySchedule = new TodaySchedule();
                            todaySchedule.setProfram(mTodayscheduleJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.PROGRAM));
                            todaySchedule.setImage(mTodayscheduleJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.IMAGE));
                            todaySchedule.setFrom(mTodayscheduleJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.DURATION));
                       //     todaySchedule.setTo(mTodayscheduleJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.TODATE));
                            todaySchedule.setDiscription(mTodayscheduleJsonArray.getJSONObject(a).getString(AppConstants.APIKeys.DESCRIPTION));
                            mTodaySchedule.add(todaySchedule);
                        }
                        mDifferentshowVideoAdapter = new TodayScheduleAdapter(TodayScheduleFragment.this, getActivity(), mTodaySchedule);
                        mRecyclerView.setAdapter(mDifferentshowVideoAdapter);
                    }
                    else {
                        mRecyclerView.setVisibility(View.GONE);
                        mVisible.setVisibility(View.VISIBLE);
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVideoId(@NonNull String videoUrl) {
        String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("Today's Schedule");
        OnMenuIconChange.iconchange(TodayScheduleFragment.this);
        HomeActivity.fragment = TodayScheduleFragment.this;
    }
}
