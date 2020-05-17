package com.zinedroid.android.atmadarshantv.HorizontalCalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.zinedroid.android.atmadarshantv.R;


/**
 * Created by Cecil Paul on 5/7/18.
 */
public class ZineCalendarView  extends RecyclerView {

    private int textColorNormal, textColorSelected;
    private int selectedDateBackground;
    private int selectorColor;
    private float textSizeMonthName, textSizeDayNumber, textSizeDayName;
    private ZineCalendar zineCalendar;

    private final float FLING_SCALE_DOWN_FACTOR = 0.5f;
    private final float DEFAULT_TEXT_SIZE_MONTH_NAME = 14f;
    private final float DEFAULT_TEXT_SIZE_DAY_NUMBER = 24f;
    private final float DEFAULT_TEXT_SIZE_DAY_NAME = 14f;

    public ZineCalendarView(Context context) {
        super(context);
    }

    public ZineCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView,
                0, 0);

        try {
            textColorNormal = a.getColor(devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_textColorNormal, Color.LTGRAY);
            textColorSelected = a.getColor(devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_textColorSelected, Color.BLACK);
            selectedDateBackground = a.getColor(devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_selectedDateBackground, Color.TRANSPARENT);
            selectorColor = a.getColor(devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_selectorColor, fetchAccentColor());

            textSizeMonthName = getRawSizeValue(a, devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_textSizeMonthName,
                    DEFAULT_TEXT_SIZE_MONTH_NAME);
            textSizeDayNumber = getRawSizeValue(a, devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_textSizeDayNumber,
                    DEFAULT_TEXT_SIZE_DAY_NUMBER);
            textSizeDayName = getRawSizeValue(a, devs.mulham.horizontalcalendar.R.styleable.HorizontalCalendarView_textSizeDayName,
                    DEFAULT_TEXT_SIZE_DAY_NAME);
        } finally {
            a.recycle();
        }
    }

    /**
     *  get the raw value from a complex value ( Ex: complex = 14sp, returns 14)
     */
    private float getRawSizeValue(TypedArray a ,int index, float defValue){
        TypedValue outValue = new TypedValue();
        boolean result = a.getValue(index, outValue);
        if (!result){
            return defValue;
        }

        return TypedValue.complexToFloat(outValue.data);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX *= FLING_SCALE_DOWN_FACTOR; // (between 0 for no fling, and 1 for normal fling, or more for faster fling).

        return super.fling(velocityX, velocityY);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (isInEditMode()) {
            setMeasuredDimension(widthSpec, 150);
        } else {
            super.onMeasure(widthSpec, heightSpec);
        }

    }

    public float getSmoothScrollSpeed() {
        return getLayoutManager().getSmoothScrollSpeed();
    }

    public void setSmoothScrollSpeed(float smoothScrollSpeed) {
        getLayoutManager().setSmoothScrollSpeed(smoothScrollSpeed);
    }

    @Override
    public ZineCalendarAdapter getAdapter() {
        return (ZineCalendarAdapter) super.getAdapter();
    }

    @Override
    public ZineLayoutManager getLayoutManager() {
        return (ZineLayoutManager) super.getLayoutManager();
    }

    private int fetchAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public ZineCalendar getZineCalendar() {
        return zineCalendar;
    }

    public void setZineCalendar(ZineCalendar zineCalendar) {

        if (zineCalendar.getTextColorNormal() == 0) {
            zineCalendar.setTextColorNormal(textColorNormal);
        }
        if (zineCalendar.getTextColorSelected() == 0) {
            zineCalendar.setTextColorSelected(textColorSelected);
        }
        if (zineCalendar.getSelectorColor() == 0) {
            zineCalendar.setSelectorColor(selectorColor);
        }
        if (zineCalendar.getSelectedDateBackground() == 0) {
            zineCalendar.setSelectedDateBackground(selectedDateBackground);
        }
        if (zineCalendar.getTextSizeMonthName() == 0) {
            zineCalendar.setTextSizeMonthName(textSizeMonthName);
        }
        if (zineCalendar.getTextSizeDayNumber() == 0) {
            zineCalendar.setTextSizeDayNumber(textSizeDayNumber);
        }
        if (zineCalendar.getTextSizeDayName() == 0) {
            zineCalendar.setTextSizeDayName(textSizeDayName);
        }

        this.zineCalendar = zineCalendar;
    }

    /**
     * @return position of selected date on center of screen
     */
    public int getPositionOfCenterItem() {
        int numberOfDatesOnScreen = zineCalendar.getNumberOfDatesOnScreen();
        int firstVisibilePosition = getLayoutManager().findFirstCompletelyVisibleItemPosition();
        if (firstVisibilePosition == -1) {
            return -1;
        }
        return firstVisibilePosition + (numberOfDatesOnScreen / 2);
    }
}
