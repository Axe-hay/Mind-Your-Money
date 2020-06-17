package com.example.mindyourmoney;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BorrowerListAdapter extends ListAdapter<Borrower,RecyclerView.ViewHolder> {

    private OnItemClickListener mListener;

    public BorrowerListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Borrower> DIFF_CALLBACK=new DiffUtil.ItemCallback<Borrower>() {
        @Override
        public boolean areItemsTheSame(@NonNull Borrower oldItem, @NonNull Borrower newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Borrower oldItem, @NonNull Borrower newItem) {
            return oldItem.getIsPaid()==newItem.getIsPaid() &&
                    oldItem.getBorrowedReason().equals(newItem.getBorrowedReason()) &&
                    oldItem.getBorrowerName().equals(newItem.getBorrowerName()) &&
                    oldItem.getMoneyBorrowed().equals(newItem.getMoneyBorrowed()) &&
                    oldItem.getDateBorrowed().equals(newItem.getDateBorrowed()) &&
                    oldItem.getTimeBorrowed().equals(newItem.getTimeBorrowed());
        }
    };


    public Borrower getBorrowerAt(int position){
        return getItem(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new BorrowListViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Borrower currentBorrower=getItem(position);
        final BorrowListViewHolder hold=(BorrowListViewHolder) holder;
        hold.mBorrowerName.setText(currentBorrower.getBorrowerName());
        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null && hold.getAdapterPosition()!=RecyclerView.NO_POSITION)
                    mListener.onItemClick(getItem(hold.getAdapterPosition()));
            }
        });
        if(currentBorrower.getIsPaid())
            hold.mBorrowedAmount.setBackgroundColor(Color.GREEN);
        else
            hold.mBorrowedAmount.setBackgroundColor(Color.RED);
        hold.mBorrowedAmount.setText("₹"+currentBorrower.getMoneyBorrowed().toString());
    }


    public interface OnItemClickListener{
        void onItemClick(Borrower borrower);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}
