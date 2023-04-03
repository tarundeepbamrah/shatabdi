package com.example.shatabdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;


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
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dealers.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i= new Intent(Dealers.this,Signin.class);
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