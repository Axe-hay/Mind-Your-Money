package com.example.mindyourmoney;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BorrowersTableDataAccessObject {

    @Insert
    void insert(Borrower borrower);

    @Update
    void update(Borrower borrower);

    @Delete
    void delete(Borrower borrower);

    @Query("SELECT * FROM Borrowers")
    LiveData<List<Borrower>> getAllBorrowers();

}
