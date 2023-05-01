package com.example.shatabdi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DealerView extends AppCompatActivity {
    MaterialButton mPickDateButton;
    RecyclerView recyclerView;
    AdapterDealerView adapter;
    ApiInterface apiInterface;
    AlertDialog dialog;
    TextView nodealersvisited;
    AppCompatButton back,search;
    EditText searchdealer;
    String dealerresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_dealer_view);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView=findViewById(R.id.recycler_view);
        mPickDateButton=findViewById(R.id.selectdate);
        nodealersvisited=findViewById(R.id.nodealersvisited);
        back= findViewById(R.id.back);
        search=findViewById(R.id.search);
        searchdealer=findViewById(R.id.searchdealer);
        search.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DealerView.this, ManagerDashboard.class);
                startActivity(i);
            }
        });

        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);

        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker= MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        String d1=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
                        String sqld1=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.first));
                        String d2=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));
                        String sqld2=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.second));
                        mPickDateButton.setText(d1+" to "+d2);
                        //Toast.makeText(SalesmanView.this, sqld1+sqld2, Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder= new AlertDialog.Builder(DealerView.this);
                        View view1 = LayoutInflater.from(DealerView.this).inflate(R.layout.loadingdialog,null);
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

                        getresult(sqld1,sqld2);
                        search.setEnabled(true);
                        search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dealerresult=searchdealer.getText().toString();
                                if(!dealerresult.equals("")){
                                    dialog.show();
                                    searchresult(dealerresult,sqld1,sqld2);
                                }
                            }
                        });

                    }
                });
                materialDatePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });
    }
    private void setadapter(List<ModelDealers> model, String sdate, String tdate) {
        adapter = new AdapterDealerView(DealerView.this,model,sdate,tdate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DealerView.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void getresult(String sdate,String tdate) {
        apiInterface.readDealerReport(sdate,tdate).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            //adapter.notifyDataSetChanged();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData(),sdate,tdate);
                        }
                        else{
                            //Toast.makeText(SalesmanView.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            nodealersvisited.setText("NO DEALER FOUND IN THIS DATE RANGE");
                            recyclerView.setAdapter(null);
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

    private void searchresult(String dealer,String sdate,String tdate) {
        apiInterface.readDealerSearchReport(dealer,sdate,tdate).enqueue(new Callback<GetResponse2>() {
            @Override
            public void onResponse(Call<GetResponse2> call, Response<GetResponse2> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            //adapter.notifyDataSetChanged();
                            nodealersvisited.setText("");
                            setadapter(response.body().getData(),sdate,tdate);
                        }
                        else{
                            //Toast.makeText(SalesmanView.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            nodealersvisited.setText("NO DEALER FOUND IN THIS DATE RANGE");
                            recyclerView.setAdapter(null);
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
        Intent i = new Intent(DealerView.this, ManagerDashboard.class);
        startActivity(i);
    }

}