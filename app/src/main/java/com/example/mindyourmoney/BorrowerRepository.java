package com.example.mindyourmoney;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

public class BorrowerRepository {

    private BorrowersTableDataAccessObject mBorrowerDao;
    private LiveData<List<Borrower>> allBorrowers;

    public BorrowerRepository(Application application){
        BorrowerDatabase database=BorrowerDatabase.getInstance(application);
        mBorrowerDao=database.borrowersDao();
        allBorrowers=mBorrowerDao.getAllBorrowers();
    }

    public void insert(Borrower borrower){
        new InsertBorrowerAsyncTask(mBorrowerDao).execute(borrower);
    }

    public void update(Borrower borrower){
        new UpdateBorrowerAsyncTask(mBorrowerDao).execute(borrower);
    }

    public void delete(Borrower borrower){
        new DeleteBorrowerAsyncTask(mBorrowerDao).execute(borrower);
    }

    public LiveData<List<Borrower>> getAllBorrowers(){
        return allBorrowers;
    }




    private static class InsertBorrowerAsyncTask extends AsyncTask<Borrower,Void,Void>{
        private BorrowersTableDataAccessObject borrowersDao;

        private InsertBorrowerAsyncTask(BorrowersTableDataAccessObject borrowersDao){
            this.borrowersDao=borrowersDao;
        }

        @Override
        protected Void doInBackground(Borrower... borrowers) {
            borrowersDao.insert(borrowers[0]);
            return null;
        }
    }

    private static class UpdateBorrowerAsyncTask extends AsyncTask<Borrower,Void,Void>{
        private BorrowersTableDataAccessObject borrowersDao;

        private UpdateBorrowerAsyncTask(BorrowersTableDataAccessObject borrowersDao){
            this.borrowersDao=borrowersDao;
        }

        @Override
        protected Void doInBackground(Borrower... borrowers) {
            borrowersDao.update(borrowers[0]);
            return null;
        }
    }

    private static class DeleteBorrowerAsyncTask extends AsyncTask<Borrower,Void,Void>{
        private BorrowersTableDataAccessObject borrowersDao;

        private DeleteBorrowerAsyncTask(BorrowersTableDataAccessObject borrowersDao){
            this.borrowersDao=borrowersDao;
        }

        @Override
        protected Void doInBackground(Borrower... borrowers) {
            borrowersDao.delete(borrowers[0]);
            return null;
        }
    }

}
