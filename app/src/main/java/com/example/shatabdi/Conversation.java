package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Conversation extends AppCompatActivity {

    AppCompatButton sendreport;
    TextView logout;
    AppCompatButton cancel;
    EditText confirmconversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton cancel,yes;
                LinearLayout exit=findViewById(R.id.exit);
                androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(Conversation.this);
                View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.confirmexit,exit);
                cancel=view1.findViewById(R.id.cancel);
                yes=view1.findViewById(R.id.yes);
                builder.setView(view1);
                androidx.appcompat.app.AlertDialog dialog=builder.create();

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
                        Intent i= new Intent(Conversation.this,Signin.class);
                        startActivity(i);
                    }
                });
            }
        });

        sendreport= findViewById(R.id.sendreport);
        sendreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Conversation.this);
                View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.conversationdialog,null);
                cancel=view1.findViewById(R.id.cancel);
                confirmconversation=view1.findViewById(R.id.confirmconversation);

                builder.setView(view1);
                AlertDialog dialog=builder.create();

                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog.setCancelable(false);
                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}