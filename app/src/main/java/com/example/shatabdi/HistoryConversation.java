package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryConversation extends AppCompatActivity {
    String name,dealer,date;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    TextView dealername,convodate;
    AdapterHistoryConversation adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_history_conversation);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        name=getIntent().getExtras().getString("name");
        dealer=getIntent().getExtras().getString("dealer");
        date=getIntent().getExtras().getString("date");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.convorecyclerview);
        dealername=findViewById(R.id.dname);
        convodate=findViewById(R.id.date);
        dealername.setText(dealer);
        convodate.setText(date);

        AlertDialog.Builder builder= new AlertDialog.Builder(HistoryConversation.this);
        View view1 = LayoutInflater.from(HistoryConversation.this).inflate(R.layout.loadingdialog,null);
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

        getresult(name,dealer,date);
    }
    private void setadapter(List<ModelConversation> model) {
        dealername.setText(dealer);
        convodate.setText(date);
        adapter=new AdapterHistoryConversation(HistoryConversation.this,model);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void getresult(String name,String dealer,String date) {
        apiInterface.readConversation(name,dealer,date).enqueue(new Callback<GetConversationResponse>() {
            @Override
            public void onResponse(Call<GetConversationResponse> call, Response<GetConversationResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            setadapter(response.body().getData());

                        }
                        else{
                            Toast.makeText(HistoryConversation.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(HistoryConversation.this, History.class);
        i.putExtra("dealer",dealer);
        i.putExtra("date",date);
        i.putExtra("name",name);
        startActivity(i);
    }
}