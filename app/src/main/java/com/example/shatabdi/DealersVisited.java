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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DealersVisited extends AppCompatActivity {
    String sname,sdate,tdate;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    AdapterDealersVisited adapter;
    TextView salesmanname,totaldealers;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_dealers_visited);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        sname=getIntent().getExtras().getString("sname");
        sdate=getIntent().getExtras().getString("sdate");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.rviewdealervisited);
        salesmanname=findViewById(R.id.sname);
        totaldealers=findViewById(R.id.nodv);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DealersVisited.this, SalesmanView.class);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(DealersVisited.this);
        View view1 = LayoutInflater.from(DealersVisited.this).inflate(R.layout.loadingdialog,null);
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
    private void setadapter(List<ModelDealers> model, String sdate, String tdate) {
        salesmanname.setText(sname);
        totaldealers.setText(String.valueOf(model.size()));
        adapter=new AdapterDealersVisited(DealersVisited.this,model,sname,sdate,tdate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String sname,String sdate,String tdate) {
        apiInterface.readDealersVisited(sname,sdate,tdate).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            setadapter(response.body().getData(),sdate,tdate);

                        }
                        else{
                            Toast.makeText(DealersVisited.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(DealersVisited.this, SalesmanView.class);
        startActivity(i);
    }
}