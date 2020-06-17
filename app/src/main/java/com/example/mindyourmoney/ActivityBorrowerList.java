package com.example.mindyourmoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ActivityBorrowerList extends AppCompatActivity {

    public static final int REQUEST_ADD_BORROWER=1;
    public static final int REQUEST_DISPLAY_BORROWER=2;

    private BorrowerViewModel mBorrowerViewModel;
    private BorrowerListAdapter mBorrowerListAdapter;
    private RecyclerView mBorrowListRecycler;
    private FloatingActionButton mAddBorrowerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBorrowerListAdapter=new BorrowerListAdapter();

        mAddBorrowerButton=findViewById(R.id.add_borrower_float_action_button);

        mBorrowListRecycler=findViewById(R.id.borrower_items_recycler);
        mBorrowListRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBorrowListRecycler.setAdapter(mBorrowerListAdapter);


        mBorrowerViewModel= new ViewModelProvider(this).get(BorrowerViewModel.class);
        mBorrowerViewModel.getAllBorrowers().observe(this, new Observer<List<Borrower>>() {
            @Override
            public void onChanged(List<Borrower> borrowers) {
                mBorrowerListAdapter.submitList(borrowers);
            }
        });

        mAddBorrowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityBorrowerList.this,ActivityBorrowerDetails.class);
                startActivityForResult(intent,REQUEST_ADD_BORROWER);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.LEFT){
                    mBorrowerViewModel.delete(mBorrowerListAdapter.getBorrowerAt(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(mBorrowListRecycler);

        mBorrowerListAdapter.setOnItemClickListener(new BorrowerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Borrower borrower) {
                Intent intent=new Intent(ActivityBorrowerList.this,ActivityBorrowerDetails.class);
                intent.putExtra(ActivityBorrowerDetails.EXTRA_NAME,borrower.getBorrowerName());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_AMOUNT,borrower.getMoneyBorrowed());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_REASON,borrower.getBorrowedReason());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_PAID,borrower.getIsPaid());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_ID,borrower.getId());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_TIME,borrower.getTimeBorrowed());
                intent.putExtra(ActivityBorrowerDetails.EXTRA_DATE,borrower.getDateBorrowed());
                startActivityForResult(intent,REQUEST_DISPLAY_BORROWER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_ADD_BORROWER && resultCode==RESULT_OK){
            String name=data.getStringExtra(ActivityBorrowerDetails.EXTRA_NAME);
            String reason=data.getStringExtra(ActivityBorrowerDetails.EXTRA_REASON);
            boolean paid=data.getBooleanExtra(ActivityBorrowerDetails.EXTRA_PAID,false);
            int amount=data.getIntExtra(ActivityBorrowerDetails.EXTRA_AMOUNT,100);
            String date=data.getStringExtra(ActivityBorrowerDetails.EXTRA_DATE);
            String time=data.getStringExtra(ActivityBorrowerDetails.EXTRA_TIME);

            Borrower borrower=new Borrower();
            borrower.setBorrowerName(name);
            borrower.setIsPaid(paid);
            borrower.setBorrowedReason(reason);
            borrower.setMoneyBorrowed(amount);
            borrower.setDateBorrowed(date);
            borrower.setTimeBorrowed(time);

            mBorrowerViewModel.insert(borrower);
        }

        else if(requestCode==REQUEST_DISPLAY_BORROWER && resultCode==RESULT_OK){
            String name=data.getStringExtra(ActivityBorrowerDetails.EXTRA_NAME);
            String reason=data.getStringExtra(ActivityBorrowerDetails.EXTRA_REASON);
            boolean paid=data.getBooleanExtra(ActivityBorrowerDetails.EXTRA_PAID,false);
            int amount=data.getIntExtra(ActivityBorrowerDetails.EXTRA_AMOUNT,100);
            String id=data.getStringExtra(ActivityBorrowerDetails.EXTRA_ID);
            String date=data.getStringExtra(ActivityBorrowerDetails.EXTRA_DATE);
            String time=data.getStringExtra(ActivityBorrowerDetails.EXTRA_TIME);

            Borrower borrower=new Borrower();
            borrower.setBorrowerName(name);
            borrower.setIsPaid(paid);
            borrower.setBorrowedReason(reason);
            borrower.setMoneyBorrowed(amount);
            borrower.setTimeBorrowed(time);
            borrower.setDateBorrowed(date);
            borrower.setId(id);

            mBorrowerViewModel.update(borrower);
        }
    }
}
