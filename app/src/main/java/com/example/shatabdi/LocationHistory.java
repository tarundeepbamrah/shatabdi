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

public class LocationHistory extends AppCompatActivity {
    WebView webView;
    AlertDialog dialog,dialog2;
    AppCompatButton back,login,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_location_history);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        webView = findViewById(R.id.tracksalesmanmap);
        back= findViewById(R.id.back);
        login=findViewById(R.id.login);
        logout=findViewById(R.id.logout);

        AlertDialog.Builder builder= new AlertDialog.Builder(LocationHistory.this);
        View view1 = LayoutInflater.from(LocationHistory.this).inflate(R.layout.loadingdialog,null);
        builder.setView(view1);
        dialog=builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },3000);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationHistory.this,TrackSalesman.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(LocationHistory.this);
                View view1 = LayoutInflater.from(LocationHistory.this).inflate(R.layout.loadingdialog,null);
                builder.setView(view1);
                dialog2=builder.create();
                dialog2.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog2.setCancelable(false);
                dialog2.getWindow().setGravity(Gravity.CENTER);
                dialog2.show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog2.dismiss();
                    }
                },10000);

                webView.loadUrl("https://www.google.com/maps/timeline");
                webView.getSettings().setJavaScriptEnabled(true);
                //webView.setWebViewClient(new WebViewClient());
                webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        dialog2.dismiss();
                    }
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(LocationHistory.this);
                View view1 = LayoutInflater.from(LocationHistory.this).inflate(R.layout.loadingdialog,null);
                builder.setView(view1);
                dialog2=builder.create();
                dialog2.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog2.setCancelable(false);
                dialog2.getWindow().setGravity(Gravity.CENTER);
                dialog2.show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog2.dismiss();
                    }
                },10000);

                webView.loadUrl("https://accounts.google.com/Logout");
                webView.getSettings().setJavaScriptEnabled(true);
                //webView.setWebViewClient(new WebViewClient());
                webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        dialog2.dismiss();
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(LocationHistory.this, TrackSalesman.class);
        startActivity(i);
    }
}