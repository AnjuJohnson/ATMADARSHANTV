package com.zinedroid.android.atmadarshantv.HorizontalCalendar;

import java.util.Date;


/**
 * Created by Cecil Paul on 5/7/18.
 */
public abstract class ZineCalendarListener {

    public abstract void onDateSelected(Date date, int position);

    public void onCalendarScroll(ZineCalendarView calendarView, int dx, int dy){}

    public boolean onDateLongClicked(Date date, int position){
        return false;
    }

}