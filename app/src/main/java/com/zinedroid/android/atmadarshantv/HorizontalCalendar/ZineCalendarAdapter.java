package com.zinedroid.android.atmadarshantv.HorizontalCalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.zinedroid.android.atmadarshantv.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cecil Paul on 5/7/18.
 */
public class ZineCalendarAdapter extends RecyclerView.Adapter<ZineCalendarAdapter.DayViewHolder> {

    private final Context context;
    private ArrayList<Date> datesList;
    private int widthCell;
    private ZineCalendar zineCalendar;
    private int numberOfDates;
    private ZineCalendarView zineCalendarView;

    ZineCalendarAdapter(ZineCalendarView zineCalendarView, ArrayList<Date> datesList) {
        this.zineCalendarView = zineCalendarView;
        this.context = zineCalendarView.getContext();
        this.datesList = datesList;
        this.zineCalendar = zineCalendarView.getZineCalendar();
        this.numberOfDates = zineCalendar.getNumberOfDatesOnScreen();
        calculateCellWidth();
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar, viewGroup, false);

        convertView.setMinimumWidth(widthCell);

        final DayViewHolder holder = new DayViewHolder(convertView);
        holder.selectionView.setBackgroundColor(zineCalendar.getSelectorColor());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == -1)
                    return;

                Date date = datesList.get(holder.getAdapterPosition());

                if (!date.before(zineCalendar.getDateStartCalendar())
                        && !date.after(zineCalendar.getDateEndCalendar())) {
                    zineCalendarView.setSmoothScrollSpeed(ZineLayoutManager.SPEED_SLOW);
                    zineCalendar.centerCalendarToPosition(holder.getAdapterPosition());
                }
            }
        });

        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Date date = datesList.get(holder.getAdapterPosition());
                ZineCalendarListener calendarListener = zineCalendar.getCalendarListener();
                if ((calendarListener != null) && !date.before(zineCalendar.getDateStartCalendar())
                        && !date.after(zineCalendar.getDateEndCalendar())) {
                    return calendarListener.onDateLongClicked(date, holder.getAdapterPosition());
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        Date day = datesList.get(position);
        int selectedItemPosition = zineCalendar.getSelectedDatePosition();

        // Selected Day
        if (position == selectedItemPosition) {
            holder.txtDayNumber.setTextColor(zineCalendar.getTextColorSelected());
            holder.txtMonthName.setTextColor(zineCalendar.getTextColorSelected());
            holder.txtDayName.setTextColor(zineCalendar.getTextColorSelected());
            holder.layoutBackground.setBackgroundColor(zineCalendar.getSelectedDateBackground());
            holder.selectionView.setVisibility(View.VISIBLE);
        }
        // Unselected Days
        else {
            holder.txtDayNumber.setTextColor(zineCalendar.getTextColorNormal());
            holder.txtMonthName.setTextColor(zineCalendar.getTextColorNormal());
            holder.txtDayName.setTextColor(zineCalendar.getTextColorNormal());
            holder.layoutBackground.setBackgroundColor(Color.TRANSPARENT);
            holder.selectionView.setVisibility(View.INVISIBLE);
        }

        holder.txtDayNumber.setText(DateFormat.format(zineCalendar.getFormatDayNumber(), day).toString());
        holder.txtDayNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                zineCalendar.getTextSizeDayNumber());

        if (zineCalendar.isShowDayName()) {
            holder.txtDayName.setText(DateFormat.format(zineCalendar.getFormatDayName(), day).toString());
            holder.txtDayName.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    zineCalendar.getTextSizeDayName());
        } else {
            holder.txtDayName.setVisibility(View.GONE);
        }

        if (zineCalendar.isShowMonthName()) {
            holder.txtMonthName.setText(DateFormat.format(zineCalendar.getFormatMonth(), day).toString());
            holder.txtMonthName.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    zineCalendar.getTextSizeMonthName());
        } else {
            holder.txtMonthName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }

    public Date getItem(int position) {
        return datesList.get(position);
    }

    /**
     * calculate each item width depends on {@link ZineCalendar#numberOfDatesOnScreen}
     */
    private void calculateCellWidth() {

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        int widthScreen;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);

            widthScreen = size.x;
        } else {
            widthScreen = display.getWidth();
        }

        widthCell = widthScreen / numberOfDates;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView txtDayNumber;
        TextView txtDayName;
        TextView txtMonthName;
        View selectionView;
        View layoutBackground;
        View rootView;

        public DayViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            txtDayNumber = rootView.findViewById(R.id.dayNumber);
            txtDayName = rootView.findViewById(R.id.dayName);
            txtMonthName = rootView.findViewById(R.id.monthName);
            layoutBackground = rootView.findViewById(R.id.layoutBackground);
            selectionView = rootView.findViewById(R.id.selection_view);
        }
    }
}