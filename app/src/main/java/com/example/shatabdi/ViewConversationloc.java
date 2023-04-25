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

public class ViewConversationloc extends AppCompatActivity {
    String dealer,date,sname,city,area;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    TextView dealername,convodate;
    AdapterViewConversationloc adapter;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_view_conversation);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sname=getIntent().getExtras().getString("sname");
        city=getIntent().getExtras().getString("city");
        area=getIntent().getExtras().getString("area");
        dealer=getIntent().getExtras().getString("dealer");
        date=getIntent().getExtras().getString("date");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.convorecyclerview);
        dealername=findViewById(R.id.dname);
        convodate=findViewById(R.id.date);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewConversationloc.this, DealersInCity.class);
                i.putExtra("sname",sname);
                i.putExtra("date",date);
                i.putExtra("city",city);
                i.putExtra("area",area);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(ViewConversationloc.this);
        View view1 = LayoutInflater.from(ViewConversationloc.this).inflate(R.layout.loadingdialog,null);
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

        getresult(sname,dealer,date);


    }
    private void setadapter(List<ModelConversation> model,String dealer,String date) {
        dealername.setText(dealer);
        convodate.setText(date);
        adapter=new AdapterViewConversationloc(ViewConversationloc.this,model,dealer,date,sname,city,area);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void getresult(String sname,String dealer,String date) {
        apiInterface.readConversation(sname,dealer,date).enqueue(new Callback<GetConversationResponse>() {
            @Override
            public void onResponse(Call<GetConversationResponse> call, Response<GetConversationResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            setadapter(response.body().getData(),dealer,date);

                        }
                        else{
                            Toast.makeText(ViewConversationloc.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetConversationResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ViewConversationloc.this, DealersInCity.class);
        i.putExtra("sname",sname);
        i.putExtra("date",date);
        i.putExtra("city",city);
        i.putExtra("area",area);
        startActivity(i);
    }
}