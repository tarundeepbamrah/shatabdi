package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHistory adapter;
    ApiInterface apiInterface;
    AlertDialog dialog;
    TextView nodealersvisited;
    AppCompatButton search;
    EditText searchdealer,selectdate;
    String dealerresult,finaldate,name,phone,position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_history);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView=findViewById(R.id.recycler_view);
        selectdate=findViewById(R.id.selectdate);
        nodealersvisited=findViewById(R.id.nodealersvisited);
        search=findViewById(R.id.search);
        searchdealer=findViewById(R.id.searchdealer);
        name=getIntent().getExtras().getString("name");
        phone=getIntent().getExtras().getString("phone");
        position=getIntent().getExtras().getString("position");

        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);


        AlertDialog.Builder builder= new AlertDialog.Builder(History.this);
        View view1 = LayoutInflater.from(History.this).inflate(R.layout.loadingdialog,null);
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
        },10000);

        getresult(name);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateCalendar();
            }
            private void updateCalendar() {
                String Format="yyyy/MM/dd";
                SimpleDateFormat sdf=new SimpleDateFormat(Format, Locale.US);
                //String
                selectdate.setText(sdf.format(calendar.getTime()));
                finaldate=selectdate.getText().toString();
            }
        };
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(History.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectdate.getText().toString().equals("")&&searchdealer.getText().toString().equals("")) {
                    getresult(name);
                }
                else if(!selectdate.getText().toString().equals("")&&searchdealer.getText().toString().equals("")) {
                    dialog.show();
                    searchresultdate(name,selectdate.getText().toString());
                }
                else if(selectdate.getText().toString().equals("")&&!searchdealer.getText().toString().equals("")) {
                    dealerresult=searchdealer.getText().toString();
                    dialog.show();
                    searchresultdealer(name,dealerresult);
                }
                else if(!selectdate.getText().toString().equals("")&&!searchdealer.getText().toString().equals("")) {
                    dealerresult=searchdealer.getText().toString();
                    dialog.show();
                    searchresultboth(name,selectdate.getText().toString(),dealerresult);
                }
            }
        });

    }
    private void setadapter(List<ModelDealers> model) {
        adapter = new AdapterHistory(History.this,model,name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(History.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String name) {
        apiInterface.history(name).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData());
                        }
                        else{
                            nodealersvisited.setText("NO DEALERS FOUND");
                            recyclerView.setAdapter(null);
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

    private void searchresultdate(String name,String date) {
        apiInterface.historywithdate(name,date).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData());
                        }
                        else{
                            //Toast.makeText(SalesmanView.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            nodealersvisited.setText("NO DEALERS FOUND");
                            recyclerView.setAdapter(null);
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
    private void searchresultdealer(String name,String dealer) {
        apiInterface.historywithdealer(name,dealer).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData());
                        }
                        else{
                            //Toast.makeText(SalesmanView.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            nodealersvisited.setText("NO DEALERS FOUND");
                            recyclerView.setAdapter(null);
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

    private void searchresultboth(String name,String date,String dealer) {
        apiInterface.historywithboth(name,date,dealer).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData());
                        }
                        else{
                            //Toast.makeText(SalesmanView.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            nodealersvisited.setText("NO DEALERS FOUND");
                            recyclerView.setAdapter(null);
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
        Intent i = new Intent(History.this, SalesmanDashboard.class);
        i.putExtra("name",name);
        i.putExtra("phone",phone);
        i.putExtra("position",position);
        startActivity(i);
    }

}