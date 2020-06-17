package com.example.mindyourmoney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen );

        Handler splashHandler=new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,ActivityBorrowerList.class));
                overridePendingTransition(R.anim.fade_in_transition,R.anim.fade_out_transition);
                finish();
            }
        },2500);


    }
}
