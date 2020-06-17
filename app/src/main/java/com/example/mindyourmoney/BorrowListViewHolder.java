package com.example.mindyourmoney;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class BorrowListViewHolder extends RecyclerView.ViewHolder{

    public TextView mBorrowerName;
    public TextView mBorrowedAmount;

    public BorrowListViewHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.item_borrower,parent,false));
        mBorrowedAmount=itemView.findViewById(R.id.borrowed_amount);
        mBorrowerName=itemView.findViewById(R.id.name_text_view);
    }
}
