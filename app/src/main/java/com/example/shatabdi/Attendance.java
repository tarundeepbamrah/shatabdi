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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Attendance extends AppCompatActivity {
    String sname,sdate,tdate;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    AdapterAttendance adapter;
    TextView salesmanname;
    AppCompatButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_attendance);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sname=getIntent().getExtras().getString("sname");
        sdate=getIntent().getExtras().getString("sdate");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.attendrecyclerview);
        salesmanname=findViewById(R.id.sname);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Attendance.this, SalesmanView.class);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(Attendance.this);
        View view1 = LayoutInflater.from(Attendance.this).inflate(R.layout.loadingdialog,null);
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

        getresult(sname,sdate,tdate);
    }
    private static List<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
        try {
            Date d1= new SimpleDateFormat("yyyy-MM-dd").parse(dateString1);
            Date d2= new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);

            long t1= d1.getTime();
            long t2= d2.getTime();

            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

            if(t1<t2){
                for(long i=t1;i<=t2;i+=86400000){
                    dates.add(df.format(i));
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dates;
    }

    private void setadapter(List<ModelAttendance> model,List<String> dates) {
        salesmanname.setText(sname);
        adapter=new AdapterAttendance(Attendance.this,model,dates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String sname,String sdate,String tdate) {
        apiInterface.readAttendance(sdate,tdate,sname).enqueue(new Callback<GetAttendanceResponse>() {
            @Override
            public void onResponse(Call<GetAttendanceResponse> call, Response<GetAttendanceResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            List<String> dates= getDates(sdate,tdate);
                            setadapter(response.body().getData(),dates);
                            dialog.dismiss();

                        }
                        else{
                            Toast.makeText(Attendance.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetAttendanceResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Attendance.this, SalesmanView.class);
        startActivity(i);
    }
}