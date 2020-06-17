package com.example.mindyourmoney;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


@Entity(tableName = "Borrowers")
public class Borrower {

    @ColumnInfo(name = "Borrower_Name")
    private String mBorrowerName;

    @ColumnInfo(name = "Amount_Borrowed")
    private Integer mMoneyBorrowed;

    @ColumnInfo(name = "Date_Borrowed")
    private String mDateBorrowed;

    @ColumnInfo(name="Time_Borrowed")
    private String mTimeBorrowed;

    @ColumnInfo(name = "Payment_Status")
    private boolean mIsPaid;

    @ColumnInfo(name = "Reason_Borrowed")
    private String mBorrowedReason;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Unique_ID")
    private String mId=UUID.randomUUID().toString();


    public Borrower(){
        Date date =new Date();
        mDateBorrowed = new SimpleDateFormat("EEEEEEE, MMMM d, yyyy").format(date);
        mTimeBorrowed=DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }

    public String getBorrowerName() {
        return mBorrowerName;
    }

    public Integer getMoneyBorrowed() {
        return mMoneyBorrowed;
    }

    public String getDateBorrowed() {
        return mDateBorrowed;
    }

    public String getTimeBorrowed() {
        return mTimeBorrowed;
    }

    public boolean getIsPaid() {
        return mIsPaid;
    }

    public String getBorrowedReason() {
        return mBorrowedReason;
    }

    public String getId() {
        return mId;
    }

    public void setBorrowerName(String borrowerName) {
        mBorrowerName = borrowerName;
    }

    public void setMoneyBorrowed(Integer moneyBorrowed) {
        mMoneyBorrowed = moneyBorrowed;
    }

    public void setDateBorrowed(String dateBorrowed) {
        mDateBorrowed = dateBorrowed;
    }

    public void setTimeBorrowed(String timeBorrowed) {
        mTimeBorrowed = timeBorrowed;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setIsPaid(boolean paid) {
        mIsPaid = paid;
    }

    public void setBorrowedReason(String borrowedReason) {
        mBorrowedReason = borrowedReason;
    }


}
