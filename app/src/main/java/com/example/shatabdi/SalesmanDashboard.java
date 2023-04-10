package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SalesmanDashboard extends AppCompatActivity {

    AppCompatButton finddealers;
    String city,area,name,phone,position,selectcity;
    ApiInterface apiInterface;
    TextView logout,username;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    List<String> ListCity=  new ArrayList<String>();
    List<String> ListArea=  new ArrayList<String>();
    ArrayAdapter<String> adapteritem,adapteritem2;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_salesman_dashboard);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        finddealers=findViewById(R.id.finddealers);
        logout=findViewById(R.id.logout);
        autoCompleteTextView=findViewById(R.id.auto_complete_txt_city);
        autoCompleteTextView2=findViewById(R.id.auto_complete_txt_area);
        username=findViewById(R.id.username);
        name=getIntent().getExtras().getString("name");
        phone=getIntent().getExtras().getString("phone");
        position=getIntent().getExtras().getString("position");
        username.setText(name);

        AlertDialog.Builder builder= new AlertDialog.Builder(SalesmanDashboard.this);
        View view1 = LayoutInflater.from(SalesmanDashboard.this).inflate(R.layout.loadingdialog,null);
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

        initialization();
        getresult();

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

        finddealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city=autoCompleteTextView.getText().toString();
                area=autoCompleteTextView2.getText().toString();
                if (city.equals("")||area.equals("")){
                    Toast.makeText(SalesmanDashboard.this, "Select City and Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i= new Intent(SalesmanDashboard.this,Dealers.class);
                    getWindow().getAttributes().windowAnimations=R.style.animation;
                    i.putExtra("city",city);
                    i.putExtra("area",area);
                    i.putExtra("name",name);
                    i.putExtra("phone",phone);
                    i.putExtra("position",position);
                    startActivity(i);
                }

            }
        });

    }
    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    private void getresult() {
        apiInterface.getCityData().enqueue(new Callback<GetStringResponse>() {
            @Override
            public void onResponse(Call<GetStringResponse> call, Response<GetStringResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){

                            for(int i=0;i<response.body().getData().size();i++){
                                ListCity.add(response.body().getData().get(i).getCity());
                            }

                            adapteritem= new ArrayAdapter<String>(SalesmanDashboard.this,R.layout.list_item,ListCity);
                            autoCompleteTextView.setAdapter(adapteritem);
                            dialog.dismiss();
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectcity=adapterView.getItemAtPosition(i).toString();
                                    ListArea.clear();
                                    dialog.show();
                                    autoCompleteTextView2.setText("");
                                    apiInterface.getAreaData(selectcity).enqueue(new Callback<GetAreaResponse>() {
                                        @Override
                                        public void onResponse(Call<GetAreaResponse> call, Response<GetAreaResponse> response) {
                                            try{
                                                if(response!=null){
                                                    if(response.body().getStatus().equals("1")){

                                                        for(int i=0;i<response.body().getData().size();i++){
                                                            ListArea.add(response.body().getData().get(i).getArea());
                                                        }

                                                        dialog.dismiss();
                                                        adapteritem2= new ArrayAdapter<String>(SalesmanDashboard.this,R.layout.list_item,ListArea);
                                                        autoCompleteTextView2.setAdapter(adapteritem2);
                                                        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                String area=adapterView.getItemAtPosition(i).toString();
                                                            }
                                                        });
                                                    }
                                                    else{
                                                        Toast.makeText(SalesmanDashboard.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            catch(Exception e){
                                                Log.e("Exception",e.getLocalizedMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GetAreaResponse> call, Throwable t) {
                                            Log.e("Failure",t.getLocalizedMessage());
                                        }
                                    });
                                }
                            });

                        }
                        else{
                            Toast.makeText(SalesmanDashboard.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetStringResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

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