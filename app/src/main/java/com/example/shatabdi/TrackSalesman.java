package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TrackSalesman extends AppCompatActivity {
    AppCompatButton livelocation,locationhistory,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_track_salesman);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        livelocation=findViewById(R.id.livelocation);
        locationhistory=findViewById(R.id.locationhistory);
        back= findViewById(R.id.back);

        livelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrackSalesman.this,LiveTracking.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrackSalesman.this,ManagerDashboard.class);
                startActivity(i);
            }
        });
        locationhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrackSalesman.this,LocationHistory.class);
                startActivity(i);
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(TrackSalesman.this, ManagerDashboard.class);
        startActivity(i);
    }
}