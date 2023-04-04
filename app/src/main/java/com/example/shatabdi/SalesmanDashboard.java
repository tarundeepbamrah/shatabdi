package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class SalesmanDashboard extends AppCompatActivity {

    AppCompatButton finddealers;
    TextView logout;
    //private FirebaseAuth mAuth;

    String[] city = {"Delhi","Noida","Meerut"},area = {"Gandhi Nagar","Gill Road"};
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    ArrayAdapter<String> adapteritem,adapteritem2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_salesman_dashboard);
        finddealers=findViewById(R.id.finddealers);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AppCompatButton cancel,yes;
                LinearLayout exit=findViewById(R.id.exit);
                AlertDialog.Builder builder=new AlertDialog.Builder(SalesmanDashboard.this);
                View view1 = LayoutInflater.from(SalesmanDashboard.this).inflate(R.layout.confirmexit,exit);
                cancel=view1.findViewById(R.id.cancel);
                yes=view1.findViewById(R.id.yes);
                builder.setView(view1);
                AlertDialog dialog=builder.create();

                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setLayout(700,400);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i= new Intent(SalesmanDashboard.this,Signin.class);
                        startActivity(i);
                    }
                });*/

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SalesmanDashboard.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mAuth = FirebaseAuth.getInstance();
                        //mAuth.signOut();
                        //Toast.makeText(SalesmanDashboard.this, "Logged out", Toast.LENGTH_SHORT).show();

                        AlertDialog dialog1;
                        AlertDialog.Builder builder1= new AlertDialog.Builder(SalesmanDashboard.this);
                        View view1 = LayoutInflater.from(SalesmanDashboard.this).inflate(R.layout.loadingdialog,null);
                        builder1.setView(view1);
                        dialog1=builder1.create();
                        dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
                        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                        dialog1.setCancelable(false);
                        dialog1.getWindow().setGravity(Gravity.CENTER);
                        dialog1.show();
                        dialog1.getWindow().setLayout(600,400);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog1.dismiss();
                                Intent i= new Intent(SalesmanDashboard.this,Signin.class);
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
                alert.getWindow().setLayout(600,400);
                alert.show();
            }
        });

        autoCompleteTextView=findViewById(R.id.auto_complete_txt_city);
        adapteritem= new ArrayAdapter<String>(this,R.layout.list_item,city);
        autoCompleteTextView.setAdapter(adapteritem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city=adapterView.getItemAtPosition(i).toString();
            }
        });

        autoCompleteTextView2=findViewById(R.id.auto_complete_txt_area);
        adapteritem2= new ArrayAdapter<String>(this,R.layout.list_item,area);
        autoCompleteTextView2.setAdapter(adapteritem2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String area=adapterView.getItemAtPosition(i).toString();
            }
        });
        finddealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SalesmanDashboard.this,Dealers.class);
                getWindow().getAttributes().windowAnimations=R.style.animation;
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout and Exit?");
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
        alert.getWindow().setLayout(600,400);
        alert.show();
    }
}