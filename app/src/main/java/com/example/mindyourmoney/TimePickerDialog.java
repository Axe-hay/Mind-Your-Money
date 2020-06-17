package com.example.mindyourmoney;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.DialogCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimePickerDialog extends AppCompatDialogFragment {

    private TimePicker mTimePicker;
    private Date time;
    private String mTime;

    private TimePickerDialogListener mListener;

    public TimePickerDialog(String time){
        mTime=time;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_fragment,null);
        mTimePicker=v.findViewById(R.id.borrowed_time_picker);

        setRetainInstance(true);


        mTimePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getActivity()));

        try {
            if(android.text.format.DateFormat.is24HourFormat(getActivity()))
                time=new SimpleDateFormat("HH:mm").parse(mTime);
            else
                time= new SimpleDateFormat("h:mm a").parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(time);
        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int minutes=calendar.get(Calendar.MINUTE);
        mTimePicker.setCurrentHour(hours);
        mTimePicker.setCurrentMinute(minutes);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("TIME WHEN BORROWED").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hours=mTimePicker.getCurrentHour();
                int minutes=mTimePicker.getCurrentMinute();
                Calendar cal=Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,hours);
                cal.set(Calendar.MINUTE,minutes);
                Date timing = cal.getTime();
                String time=DateFormat.getTimeInstance(DateFormat.SHORT).format(timing);
                mListener.applyTime(time);
            }
        }).create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener= (TimePickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"TimePickerDialogListener to be implemented");
        }
    }

    public interface TimePickerDialogListener{
        void applyTime(String time);
    }
}
