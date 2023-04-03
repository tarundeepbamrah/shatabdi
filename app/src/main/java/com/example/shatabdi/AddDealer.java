package com.example.shatabdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
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
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_add_dealer);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddDealer.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i= new Intent(AddDealer.this,Signin.class);
                        startActivity(i);
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

        adddealer=findViewById(R.id.adddealer);

        adddealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddDealer.this,Dealers.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){

        Intent i=new Intent(AddDealer.this,Dealers.class);
        startActivity(i);
    }
}