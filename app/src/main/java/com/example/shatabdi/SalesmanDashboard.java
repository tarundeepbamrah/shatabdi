package com.example.shatabdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SalesmanDashboard extends AppCompatActivity {

    AppCompatButton finddealers;
    TextView logout;

    String[] city = {"Delhi","Noida","Meerut"};
    String[] area = {"Gandhi Nagar","Gill Road"};

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView2;
    ArrayAdapter<String> adapteritem;
    ArrayAdapter<String> adapteritem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_dashboard);

        finddealers=findViewById(R.id.finddealers);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton cancel,yes;
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
                });

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
    }
}