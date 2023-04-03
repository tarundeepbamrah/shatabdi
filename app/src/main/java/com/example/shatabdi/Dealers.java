package com.example.shatabdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

public class Dealers extends AppCompatActivity {

    AppCompatButton addbutton;
    TextView logout;
    RecyclerView recyclerView;
    AdapterDealers adapter;

    ModelDealers model1 = new ModelDealers("Tarundeep","Parminder","7617613888");
    ModelDealers model2 = new ModelDealers("Devendra","Satvinder","7617613888");
    ModelDealers model3 = new ModelDealers("Ayush","Gagan","7617613888");

    List<ModelDealers> modellist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealers);

        addbutton=findViewById(R.id.addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dealers.this,AddDealer.class);
                startActivity(i);
                
            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton cancel,yes;
                LinearLayout exit=findViewById(R.id.exit);
                AlertDialog.Builder builder=new AlertDialog.Builder(Dealers.this);
                View view1 = LayoutInflater.from(Dealers.this).inflate(R.layout.confirmexit,exit);
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
                        Intent i= new Intent(Dealers.this,Signin.class);
                        startActivity(i);
                    }
                });
            }
        });

        modellist.add(model1);
        modellist.add(model2);
        modellist.add(model3);
        modellist.add(model1);
        modellist.add(model2);
        initialization();
        setadapter(modellist);


    }

    private void initialization(){
        recyclerView = findViewById(R.id.recycler_view);
    }


    private void setadapter(List<ModelDealers> model){
        adapter= new AdapterDealers(Dealers.this,model);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Dealers.this );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed(){

        Intent i=new Intent(Dealers.this,SalesmanDashboard.class);
        startActivity(i);
    }
}