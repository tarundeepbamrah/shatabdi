package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Dealers extends AppCompatActivity {
    AppCompatButton addbutton;
    String city,area,name,phone,position;
    TextView logout,nodealers,username;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    AdapterDealers adapter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_dealers);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        addbutton = findViewById(R.id.addbutton);
        nodealers=findViewById(R.id.nodealers);
        logout = findViewById(R.id.logout);
        username=findViewById(R.id.username);
        city=getIntent().getExtras().getString("city");
        area=getIntent().getExtras().getString("area");
        name=getIntent().getExtras().getString("name");
        phone=getIntent().getExtras().getString("phone");
        position=getIntent().getExtras().getString("position");
        username.setText(name);

        AlertDialog.Builder builder= new AlertDialog.Builder(Dealers.this);
        View view1 = LayoutInflater.from(Dealers.this).inflate(R.layout.loadingdialog,null);
        builder.setView(view1);
        dialog=builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        //dialog.getWindow().setLayout(600,400);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },10000);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dealers.this, AddDealer.class);
                i.putExtra("city",city);
                i.putExtra("area",area);
                i.putExtra("name",name);
                i.putExtra("phone",phone);
                i.putExtra("position",position);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dealers.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog1;
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Dealers.this);
                        View view1 = LayoutInflater.from(Dealers.this).inflate(R.layout.loadingdialog, null);
                        builder1.setView(view1);
                        dialog1 = builder1.create();
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.animation;
                        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                        dialog1.setCancelable(false);
                        dialog1.getWindow().setGravity(Gravity.CENTER);
                        dialog1.show();
                        //dialog1.getWindow().setLayout(600, 400);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog1.dismiss();
                                Intent i = new Intent(Dealers.this, Signin.class);
                                startActivity(i);
                            }
                        }, 3000);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                //alert.getWindow().setLayout(600, 400);
                alert.show();
            }
        });

        initialization();
        getresult(city,area);
    }

    private void initialization() {
        recyclerView = findViewById(R.id.recycler_view);
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }


    private void setadapter(List<ModelDealer> model) {
        adapter = new AdapterDealers(Dealers.this, model,name,phone,position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Dealers.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String city,String area)
    {
        apiInterface.getData(city,area).enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            setadapter(response.body().getData());
                            dialog.dismiss();

                        }
                        else{
                            nodealers.setText("No Dealers");
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Dealers.this, SalesmanDashboard.class);
        i.putExtra("name",name);
        i.putExtra("phone",phone);
        i.putExtra("position",position);
        startActivity(i);
    }
}