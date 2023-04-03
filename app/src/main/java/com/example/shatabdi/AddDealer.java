package com.example.shatabdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDealer extends AppCompatActivity {

    AppCompatButton adddealer;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton cancel,yes;
                LinearLayout exit=findViewById(R.id.exit);
                AlertDialog.Builder builder=new AlertDialog.Builder(AddDealer.this);
                View view1 = LayoutInflater.from(AddDealer.this).inflate(R.layout.confirmexit,exit);
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
                        Intent i= new Intent(AddDealer.this,Signin.class);
                        startActivity(i);
                    }
                });
            }
        });

        adddealer=findViewById(R.id.adddealer);

        adddealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddDealer.this,Dealers.class);
                startActivity(i);
            }
        });
    }
}