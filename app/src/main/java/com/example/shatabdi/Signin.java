package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Signin extends AppCompatActivity {
    AppCompatButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_main);

        login= findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog;
                AlertDialog.Builder builder= new AlertDialog.Builder(Signin.this);
                View view1 = LayoutInflater.from(Signin.this).inflate(R.layout.loadingdialog,null);
                builder.setView(view1);
                dialog=builder.create();
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog.setCancelable(false);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                dialog.getWindow().setLayout(600,400);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent i= new Intent(Signin.this,SalesmanDashboard.class);
                        startActivity(new Intent(getApplicationContext(),SalesmanDashboard.class));
                    }
                },3000);

            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        alert.getWindow().setLayout(600,400);
        alert.show();
    }
}