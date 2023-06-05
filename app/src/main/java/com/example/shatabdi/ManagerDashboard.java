package com.example.shatabdi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManagerDashboard extends AppCompatActivity {
    AppCompatButton salesmanreport,dealerreport,locationreport,trackuserlocation;
    TextView logout;
    //String name;
    //TextView mname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_manager_dashboard);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        salesmanreport= findViewById(R.id.salesmanreport);
        logout=findViewById(R.id.logout);
        dealerreport= findViewById(R.id.dealerreport);
        locationreport= findViewById(R.id.locationreport);
        trackuserlocation= findViewById(R.id.trackuserlocation);
        //mname=findViewById(R.id.mname);
        //name=getIntent().getExtras().getString("name");
        //mname.setText(name);
        salesmanreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ManagerDashboard.this,SalesmanView.class);
                //i.putExtra("name",name);
                startActivity(i);
            }
        });

        trackuserlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ManagerDashboard.this,TrackSalesman.class);
                //i.putExtra("name",name);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManagerDashboard.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog1;
                        AlertDialog.Builder builder1= new AlertDialog.Builder(ManagerDashboard.this);
                        View view1 = LayoutInflater.from(ManagerDashboard.this).inflate(R.layout.loadingdialog,null);
                        builder1.setView(view1);
                        dialog1=builder1.create();
                        dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
                        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                        dialog1.setCancelable(false);
                        dialog1.getWindow().setGravity(Gravity.CENTER);
                        dialog1.show();
                        //dialog1.getWindow().setLayout(600,400);

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FirebaseAuth.getInstance().signOut();
                                dialog1.dismiss();
                                Intent i= new Intent(ManagerDashboard.this,Signin.class);
                                startActivity(i);
                            }
                        },3000);
                    }
                });
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                android.app.AlertDialog alert=builder.create();
                alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                //alert.getWindow().setLayout(600,400);
                alert.show();
            }
        });

        dealerreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ManagerDashboard.this,DealerView.class);
                //i.putExtra("name",name);
                startActivity(i);
            }
        });
        locationreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ManagerDashboard.this,LocationReport.class);
                //i.putExtra("name",name);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
        android.app.AlertDialog alert=builder.create();
        alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        //alert.getWindow().setLayout(600,400);
        alert.show();
    }
}