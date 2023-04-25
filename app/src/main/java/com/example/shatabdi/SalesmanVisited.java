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

public class SalesmanVisited extends AppCompatActivity {
    String dealer,sdate,tdate;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    AdapterSalesmanVisited adapter;
    TextView dealername,totalsalesman;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_salesman_visited);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dealer=getIntent().getExtras().getString("dealer");
        sdate=getIntent().getExtras().getString("sdate");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.rviewsmanvisited);
        dealername=findViewById(R.id.dealer);
        totalsalesman=findViewById(R.id.nosv);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SalesmanVisited.this, DealerView.class);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(SalesmanVisited.this);
        View view1 = LayoutInflater.from(SalesmanVisited.this).inflate(R.layout.loadingdialog,null);
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

        getresult(dealer,sdate,tdate);
    }
    private void setadapter(List<ModelSalesmanVisited> model, String sdate, String tdate) {
        dealername.setText(dealer);
        totalsalesman.setText(String.valueOf(model.size()));
        adapter=new AdapterSalesmanVisited(SalesmanVisited.this,model,dealer,sdate,tdate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String dealer,String sdate,String tdate) {
        apiInterface.readSalesmanVisited(dealer,sdate,tdate).enqueue(new Callback<GetSalesmanVisitedResponse>() {
            @Override
            public void onResponse(Call<GetSalesmanVisitedResponse> call, Response<GetSalesmanVisitedResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            setadapter(response.body().getData(),sdate,tdate);

                        }
                        else{
                            Toast.makeText(SalesmanVisited.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetSalesmanVisitedResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SalesmanVisited.this, DealerView.class);
        startActivity(i);
    }
}