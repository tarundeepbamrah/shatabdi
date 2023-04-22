package com.example.shatabdi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewLocation extends AppCompatActivity {

    WebView webView;
    String dealer,date,conversation,sdate,tdate,sname;
    ApiInterface apiInterface;
    AlertDialog dialog;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_view_location);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        conversation=getIntent().getExtras().getString("conversation");
        dealer=getIntent().getExtras().getString("dealer");
        sname=getIntent().getExtras().getString("sname");
        sdate=getIntent().getExtras().getString("sdate");
        date=getIntent().getExtras().getString("date");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewLocation.this, ViewConversation.class);
                i.putExtra("sdate",sdate);
                i.putExtra("tdate",tdate);
                i.putExtra("dealer",dealer);
                i.putExtra("date",date);
                i.putExtra("sname",sname);
                startActivity(i);
            }
        });

        webView = findViewById(R.id.map);

        AlertDialog.Builder builder= new AlertDialog.Builder(ViewLocation.this);
        View view1 = LayoutInflater.from(ViewLocation.this).inflate(R.layout.loadingdialog,null);
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

        getresult(dealer,date,conversation);
    }
    private void getresult(String dealer,String date,String conversation) {
        apiInterface.readLocation(dealer,date,conversation).enqueue(new Callback<GetLocationResponse>() {
            @Override
            public void onResponse(Call<GetLocationResponse> call, Response<GetLocationResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){

                            webView.loadUrl(response.body().getData().get(0).getLocation());
                            webView.getSettings().setJavaScriptEnabled(true);
                            //webView.setWebViewClient(new WebViewClient());
                            webView.setWebViewClient(new WebViewClient() {
                                public void onPageFinished(WebView view, String url) {
                                    dialog.dismiss();
                                }
                            });

                        }
                        else{
                            Toast.makeText(ViewLocation.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetLocationResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ViewLocation.this, ViewConversation.class);
        i.putExtra("sdate",sdate);
        i.putExtra("tdate",tdate);
        i.putExtra("dealer",dealer);
        i.putExtra("date",date);
        i.putExtra("sname",sname);
        startActivity(i);
    }
}