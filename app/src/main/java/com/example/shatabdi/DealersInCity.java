package com.example.shatabdi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DealersInCity extends AppCompatActivity {
    String sname,date,city,area;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    AdapterDealersinCity adapter;
    TextView salesmanname,totaldealers;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_dealers_in_city);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sname=getIntent().getExtras().getString("sname");
        date=getIntent().getExtras().getString("date");
        city=getIntent().getExtras().getString("city");
        area=getIntent().getExtras().getString("area");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.rviewdealervisited);
        salesmanname=findViewById(R.id.sname);
        totaldealers=findViewById(R.id.nodv);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DealersInCity.this, LocationReport.class);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(DealersInCity.this);
        View view1 = LayoutInflater.from(DealersInCity.this).inflate(R.layout.loadingdialog,null);
        builder.setView(view1);
        dialog=builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },3000);

        getresult(sname,city,area,date);
    }
    private void setadapter(List<ModelDealers> model,String sname) {
        salesmanname.setText(sname);
        totaldealers.setText(String.valueOf(model.size()));
        adapter=new AdapterDealersinCity(DealersInCity.this,model,sname,date,city,area);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String sname,String city,String area,String date) {
        apiInterface.readDealersInCity(sname,city,area,date).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            setadapter(response.body().getData(),sname);

                        }
                        else{
                            Toast.makeText(DealersInCity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetResponse2> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(DealersInCity.this, LocationReport.class);
        startActivity(i);
    }
}