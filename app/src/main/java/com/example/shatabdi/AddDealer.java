package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddDealer extends AppCompatActivity {
    AppCompatButton adddealer;
    ApiInterface apiInterface;
    TextView logout;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_add_dealer);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        logout=findViewById(R.id.logout);
        adddealer=findViewById(R.id.adddealer);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddDealer.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.app.AlertDialog dialog1;
                        android.app.AlertDialog.Builder builder1= new AlertDialog.Builder(AddDealer.this);
                        View view1 = LayoutInflater.from(AddDealer.this).inflate(R.layout.loadingdialog,null);
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
                                Intent i= new Intent(AddDealer.this,Signin.class);
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
        adddealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialization();
                AlertDialog.Builder builder= new AlertDialog.Builder(AddDealer.this);
                View view1 = LayoutInflater.from(AddDealer.this).inflate(R.layout.loadingdialog,null);
                builder.setView(view1);
                dialog=builder.create();
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog.setCancelable(false);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                dialog.getWindow().setLayout(600,400);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },10000);
                getresult("Delhi","Noida");
            }
        });
    }
    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    private void getresult(String city,String area)
    {
        apiInterface.getData(city,area).enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            int i;
                            for(i=0;i<response.body().getData().size();i++){
                                if(response.body().getData().get(i).getDealer().equals("nbnb")&&response.body().getData().get(i).getDealer_name().equals("Vidya Furniture")&&response.body().getData().get(i).getPhone().equals("897654322")){
                                    Toast.makeText(AddDealer.this, "Dealer already exist", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    return;
                                }
                            }
                            if(i==response.body().getData().size()){
                                getresultadddealer("Delhi","Noida","ayush","Vidya Furniture","897654322");
                            }
                        }
                        else{
                            Toast.makeText(AddDealer.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    private void getresultadddealer(String city,String area,String dealer,String dealer_name,String phone)
    {
        apiInterface.insertData(city,area,dealer,dealer_name,phone).enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(AddDealer.this,"Dealer Added", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent i=new Intent(AddDealer.this,Dealers.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(AddDealer.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(AddDealer.this,Dealers.class);
        startActivity(i);
    }
}