package com.example.mindyourmoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import static android.widget.CompoundButton.*;

public class ActivityBorrowerDetails extends AppCompatActivity implements TimePickerDialog.TimePickerDialogListener,DatePickerDialog.DatePickerDialogListener {

    public static final String EXTRA_NAME="com.example.mindyourmoney.EXTRA_NAME";
    public static final String EXTRA_REASON="com.example.mindyourmoney.EXTRA_REASON";
    public static final String EXTRA_PAID="com.example.mindyourmoney.EXTRA_PAID";
    public static final String EXTRA_AMOUNT="com.example.mindyourmoney.EXTRA_AMOUNT";
    public static final String EXTRA_ID="com.example.mindyourmoneu.EXTRA_ID";
    public static final String EXTRA_DATE="com.example.mindyourmoneu.EXTRA_DATE";
    public static final String EXTRA_TIME="com.example.mindyourmoneu.EXTRA_TIME";

    public static final int REQUEST_CONTACTS=0;
    public static final int REQUEST_CONTACTS_PERMISSION=1;

    private CheckBox mPaidCheckBox;
    private FrameLayout mExternalLayout;
    private EditText mBorrowerName;
    private EditText mBorrowReason;
    private EditText mAmountBorrowed;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mSendMessage;
    private Button mPickNameFromContacts;
    final Intent pickContact=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_details);

        mExternalLayout = findViewById(R.id.external_layout);
        mPaidCheckBox = findViewById(R.id.paid_check);
        mBorrowerName=findViewById(R.id.borrower_name_edit_text);
        mBorrowReason=findViewById(R.id.reason_edit_text);
        mAmountBorrowed=findViewById(R.id.amount_borrowed);
        mDateButton=findViewById(R.id.date_button);
        mTimeButton=findViewById(R.id.time_button);
        mSendMessage=findViewById(R.id.send_message_button);
        mPickNameFromContacts=findViewById(R.id.select_name_from_contacts_button);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        final Intent callingIntent=getIntent();



        if(callingIntent.hasExtra(EXTRA_ID)){
            setTitle("Update Details");
            mAmountBorrowed.setText(getString(R.string.amount,callingIntent.getIntExtra(EXTRA_AMOUNT,100)));
            mPaidCheckBox.setChecked(callingIntent.getBooleanExtra(EXTRA_PAID,false));
            mTimeButton.setText(callingIntent.getStringExtra(EXTRA_TIME));
            mDateButton.setText(callingIntent.getStringExtra(EXTRA_DATE));
            if(callingIntent.getBooleanExtra(EXTRA_PAID,false)) {
                mExternalLayout.setBackgroundColor(getResources().getColor(R.color.positive_green));
                mSendMessage.setEnabled(false);
            }
            else
                mSendMessage.setEnabled(true);
            mBorrowerName.setText(callingIntent.getStringExtra(EXTRA_NAME));
            mBorrowReason.setText(callingIntent.getStringExtra(EXTRA_REASON));
        }else {
            setTitle("Add Borrower");
            mDateButton.setText(new Borrower().getDateBorrowed());
            mTimeButton.setText(new Borrower().getTimeBorrowed());
        }

        mTimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog=new TimePickerDialog(mTimeButton.getText().toString());
                dialog.show(getSupportFragmentManager(),"TimePicker");
            }
        });

        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(mDateButton.getText().toString());
                dialog.show(getSupportFragmentManager(),"DatePicker");
            }
        });

        mPaidCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    ColorDrawable[] color={new ColorDrawable(getResources().getColor(R.color.negative_red)),new ColorDrawable(getResources().getColor(R.color.positive_green))};
                    TransitionDrawable trans=new TransitionDrawable(color);
                    mExternalLayout.setBackground(trans);
                    trans.startTransition(1500);
                    mSendMessage.setEnabled(false);
                }
                else{
                    ColorDrawable[] color={new ColorDrawable(getResources().getColor(R.color.positive_green)),new ColorDrawable(getResources().getColor(R.color.negative_red))};
                    TransitionDrawable trans=new TransitionDrawable(color);
                    mExternalLayout.setBackground(trans);
                    trans.startTransition(1500);
                    mSendMessage.setEnabled(true);
                }
            }
        });

        mSendMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder intentBuilder=ShareCompat.IntentBuilder.from(ActivityBorrowerDetails.this);
                Intent intent=intentBuilder.setType("text/plain").setText(getMessageText()).setChooserTitle("Choose Messaging Client").createChooserIntent();
                startActivity(intent);
            }
        });

        PackageManager packageManager=ActivityBorrowerDetails.this.getPackageManager();

        if(packageManager.resolveActivity(pickContact,packageManager.MATCH_DEFAULT_ONLY)==null)
            mPickNameFromContacts.setEnabled(false);
        mPickNameFromContacts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasContactPermissions())
                   startActivityForResult(pickContact,REQUEST_CONTACTS);
                else
                    requestPermissions();
            }
        });

    }

    private boolean hasContactPermissions(){
        int result= ContextCompat.checkSelfPermission(ActivityBorrowerDetails.this,Manifest.permission.READ_CONTACTS);
        return result==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(ActivityBorrowerDetails.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACTS_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CONTACTS_PERMISSION: if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                                                  startActivityForResult(pickContact,REQUEST_CONTACTS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CONTACTS && data != null) {
            Uri contactURI = data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            Cursor contactsCursor = ActivityBorrowerDetails.this.getContentResolver().query(contactURI, queryFields, null, null, null);
            try {
                if (contactsCursor.getCount() == 0)
                    return;
                contactsCursor.moveToFirst();
                String borrowerName = contactsCursor.getString(0);
                mBorrowerName.setText(borrowerName);
            } finally {
                contactsCursor.close();
            }
        }
    }

    private String getMessageText(){
        String solvedString=null;
        String reason=mBorrowReason.getText().toString().trim();
        String date=mDateButton.getText().toString();
        String time=mTimeButton.getText().toString();
        String amount=mAmountBorrowed.getText().toString();
        String name=mBorrowerName.getText().toString();
        if(reason.isEmpty()){
            solvedString=getString(R.string.message_no_reason,name,amount,date,time);
        }
        else{
            solvedString=getString(R.string.message_reason,name,amount,date,time,reason);
        }

        return solvedString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_borrower_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_details: saveBorrowerDetails();
                                    return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveBorrowerDetails(){
        String name=mBorrowerName.getText().toString();
        String reason=mBorrowReason.getText().toString();
        boolean paid=mPaidCheckBox.isChecked();
        String date=mDateButton.getText().toString();
        String time=mTimeButton.getText().toString();

        if(name.trim().isEmpty() || mAmountBorrowed.getText().toString().substring(1).isEmpty()) {
            Toast.makeText(this, "Amount and Name fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount=Integer.parseInt(mAmountBorrowed.getText().toString().substring(1));


        Intent data=new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_REASON,reason);
        data.putExtra(EXTRA_PAID,paid);
        data.putExtra(EXTRA_AMOUNT,amount);
        data.putExtra(EXTRA_DATE,date);
        data.putExtra(EXTRA_TIME,time);

        String id=getIntent().getStringExtra(EXTRA_ID);
        if(id!=null){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);

        finish();


    }

    @Override
    public void applyTime(String time) {
        mTimeButton.setText(time);
    }

    @Override
    public void applyDate(String date) {
        mDateButton.setText(date);
    }
}