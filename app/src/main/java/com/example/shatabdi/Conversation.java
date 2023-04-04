package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Conversation extends AppCompatActivity {
    AppCompatButton sendreport,cancel,confirm;
    CheckBox attendence;
    Boolean checkboxvalue=false;
    TextView logout;
    EditText confirmconversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_conversation);
        attendence=findViewById(R.id.attendence);
        sendreport= findViewById(R.id.sendreport);
        sendreport.setEnabled(false);
        sendreport.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.disabled_button_bg));
        sendreport.setTextColor(Color.DKGRAY);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Conversation.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog1;
                        AlertDialog.Builder builder1= new AlertDialog.Builder(Conversation.this);
                        View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.loadingdialog,null);
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
                                Intent i= new Intent(Conversation.this,Signin.class);
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

        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxvalue) {
                    checkboxvalue = true;
                    sendreport.setEnabled(true);
                    sendreport.setBackgroundDrawable(ContextCompat.getDrawable(Conversation.this,R.drawable.button_bg));
                    sendreport.setTextColor(Color.WHITE);
                }
                else {
                    checkboxvalue = false;
                    sendreport.setEnabled(false);
                    sendreport.setBackgroundDrawable(ContextCompat.getDrawable(Conversation.this,R.drawable.disabled_button_bg));
                    sendreport.setTextColor(Color.DKGRAY);
                }
            }
        });


        sendreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(attendence.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Conversation.this);
                    View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.conversationdialog, null);
                    cancel = view1.findViewById(R.id.cancel);
                    confirm = view1.findViewById(R.id.confirm);
                    confirmconversation = view1.findViewById(R.id.confirmconversation);
                    builder.setView(view1);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                    dialog.setCancelable(false);
                    dialog.show();
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog dialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(Conversation.this);
                            View view2 = LayoutInflater.from(Conversation.this).inflate(R.layout.successdialog, null);
                            builder.setView(view2);
                            dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                            dialog.setCancelable(true);
                            dialog.getWindow().setGravity(Gravity.CENTER);
                            dialog.show();
                            dialog.getWindow().setLayout(600, 400);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent i = new Intent(Conversation.this, SalesmanDashboard.class);
                                    startActivity(new Intent(getApplicationContext(), SalesmanDashboard.class));
                                }
                            }, 2000);
                        }
                    });
                }
                else {
                    Toast.makeText(Conversation.this, "Please mark the CheckBox", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(Conversation.this,Dealers.class);
        startActivity(i);
    }
}