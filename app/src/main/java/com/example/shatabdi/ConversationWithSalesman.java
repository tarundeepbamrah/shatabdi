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

public class ConversationWithSalesman extends AppCompatActivity {
    String sname,date,dealer,sdate,tdate;
    ApiInterface apiInterface;
    AlertDialog dialog;
    RecyclerView recyclerView;
    TextView salesmanname,convodate;
    AdapterConversationWithSalesman adapter;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_conversation_with_salesman);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        dealer=getIntent().getExtras().getString("dealer");
        sname=getIntent().getExtras().getString("sname");
        date=getIntent().getExtras().getString("date");
        sdate=getIntent().getExtras().getString("sdate");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        recyclerView=findViewById(R.id.convorecyclerview);
        salesmanname=findViewById(R.id.sname);
        convodate=findViewById(R.id.date);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConversationWithSalesman.this, SalesmanVisited.class);
                i.putExtra("sdate",sdate);
                i.putExtra("tdate",tdate);
                i.putExtra("dealer",dealer);
                startActivity(i);
            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(ConversationWithSalesman.this);
        View view1 = LayoutInflater.from(ConversationWithSalesman.this).inflate(R.layout.loadingdialog,null);
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
    private void setadapter(List<ModelConversation> model, String sname, String date,String dealer) {
        salesmanname.setText(sname);
        convodate.setText(date);
        adapter=new AdapterConversationWithSalesman(ConversationWithSalesman.this,model,sname,date,dealer,sdate,tdate);
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
                            setadapter(response.body().getData(),sname,date,dealer);

                        }
                        else{
                            Toast.makeText(ConversationWithSalesman.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(ConversationWithSalesman.this, SalesmanVisited.class);
        i.putExtra("sdate",sdate);
        i.putExtra("tdate",tdate);
        i.putExtra("dealer",dealer);
        startActivity(i);
    }
}