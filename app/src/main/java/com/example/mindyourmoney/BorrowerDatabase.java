package com.example.mindyourmoney;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Borrower.class},version = 1)
public abstract class BorrowerDatabase extends RoomDatabase {

    private static BorrowerDatabase sBorrowerDatabaseInstance;

    public abstract BorrowersTableDataAccessObject borrowersDao();

    public static synchronized BorrowerDatabase getInstance(Context context){
        if(sBorrowerDatabaseInstance==null)
            sBorrowerDatabaseInstance= Room.databaseBuilder(context.getApplicationContext(),BorrowerDatabase.class,"borrower_database")
                    .fallbackToDestructiveMigration().build();
        return sBorrowerDatabaseInstance;
    }



}
