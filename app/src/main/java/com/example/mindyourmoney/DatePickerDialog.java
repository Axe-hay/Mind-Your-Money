package com.example.mindyourmoney;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerDialog extends AppCompatDialogFragment {

    private DatePicker mDatePicker;
    private Date date;
    private String mDate;

    private DatePickerDialog.DatePickerDialogListener mListener;

    public DatePickerDialog(String date){
        mDate=date;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

        try {
            date= new SimpleDateFormat("EEEEEEE, MMMM d, yyyy").parse(mDate);
        } catch (ParseException e) {
            Log.e("Date_Error","",e);
        }

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_WEEK);

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_fragment,null);

        mDatePicker=v.findViewById(R.id.borrow_date_picker);
        mDatePicker.setMaxDate(new Date().getTime());
        mDatePicker.init(year,month,day,null);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("DATE WHEN BORROWED").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year=mDatePicker.getYear();
                int month=mDatePicker.getMonth();
                int day=mDatePicker.getDayOfMonth();
                GregorianCalendar cal=new GregorianCalendar();
                cal.set(year,month,day);
                Date currentDate = cal.getTime();
                String date= new SimpleDateFormat("EEEEEEE, MMMM d, yyyy").format(currentDate);
                mListener.applyDate(date);
            }
        }).create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener= (DatePickerDialog.DatePickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"DatePickerDialogListener to be implemented");
        }
    }

    public interface DatePickerDialogListener{
        void applyDate(String date);
    }

}
