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

public class LiveTracking extends AppCompatActivity {
    WebView webView;
    AlertDialog dialog;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_live_tracking);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        webView = findViewById(R.id.tracksalesmanmap);
        back= findViewById(R.id.back);

        AlertDialog.Builder builder= new AlertDialog.Builder(LiveTracking.this);
        View view1 = LayoutInflater.from(LiveTracking.this).inflate(R.layout.loadingdialog,null);
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
        },10000);

        webView.loadUrl("https://www.google.com/maps");
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveTracking.this,TrackSalesman.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(LiveTracking.this, TrackSalesman.class);
        startActivity(i);
    }
}