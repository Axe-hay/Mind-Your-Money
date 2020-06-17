package com.example.mindyourmoney;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BorrowerViewModel extends AndroidViewModel {

    private BorrowerRepository mBorrowerRepository;
    private LiveData<List<Borrower>> allBorrowers;

    public BorrowerViewModel(@NonNull Application application) {
        super(application);
        mBorrowerRepository=new BorrowerRepository(application);
        allBorrowers=mBorrowerRepository.getAllBorrowers();
    }

    public void insert(Borrower borrower){
        mBorrowerRepository.insert(borrower);
    }

    public void update(Borrower borrower){
        mBorrowerRepository.update(borrower);
    }

    public void delete(Borrower borrower){
        mBorrowerRepository.delete(borrower);
    }

    public LiveData<List<Borrower>> getAllBorrowers(){
        return allBorrowers;
    }
}
