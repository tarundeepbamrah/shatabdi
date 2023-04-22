package com.example.shatabdi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationReport extends AppCompatActivity {
    String selectcity,city,area,sqld1,sqld2;
    MaterialButton mPickDateButton;
    ApiInterface apiInterface;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    List<String> ListCity=  new ArrayList<String>();
    List<String> ListArea=  new ArrayList<String>();
    ArrayAdapter<String> adapteritem,adapteritem2;
    AlertDialog dialog;
    RecyclerView recyclerView;
    AdapterVisitedCity adapter;
    TextView nodealersvisited;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_location_report);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        autoCompleteTextView=findViewById(R.id.auto_complete_txt_city);
        autoCompleteTextView2=findViewById(R.id.auto_complete_txt_area);
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
        mPickDateButton=findViewById(R.id.selectdate);
        recyclerView=findViewById(R.id.recycler_view);
        nodealersvisited=findViewById(R.id.nodealersvisited);
        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReport.this, ManagerDashboard.class);
                startActivity(i);
            }
        });

        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                ListCity.clear();
                ListArea.clear();
                autoCompleteTextView.setText("");
                autoCompleteTextView2.setText("");
                nodealersvisited.setText("");
                //materialDatePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker= MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();
                //materialDateBuilder.setTitleText("SELECT  A DATE");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        String d1=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
                        sqld1=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.first));
                        String d2=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));
                        sqld2=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.second));
                        mPickDateButton.setText(d1+" to "+d2);

                        AlertDialog.Builder builder= new AlertDialog.Builder(LocationReport.this);
                        View view1 = LayoutInflater.from(LocationReport.this).inflate(R.layout.loadingdialog,null);
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
                        getresult();
                        //getresult(sqld1,sqld2);

                    }
                });
                materialDatePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });


    }
    private void getresult() {

        apiInterface.getCityData().enqueue(new Callback<GetStringResponse>() {
            @Override
            public void onResponse(Call<GetStringResponse> call, Response<GetStringResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){

                            for(int i=0;i<response.body().getData().size();i++){
                                ListCity.add(response.body().getData().get(i).getCity());
                            }

                            adapteritem= new ArrayAdapter<String>(LocationReport.this,R.layout.list_item,ListCity);
                            autoCompleteTextView.setAdapter(adapteritem);
                            dialog.dismiss();
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectcity=adapterView.getItemAtPosition(i).toString();
                                    ListArea.clear();
                                    nodealersvisited.setText("");
                                    recyclerView.setAdapter(null);
                                    dialog.show();
                                    autoCompleteTextView2.setText("");
                                    apiInterface.getAreaData(selectcity).enqueue(new Callback<GetAreaResponse>() {
                                        @Override
                                        public void onResponse(Call<GetAreaResponse> call, Response<GetAreaResponse> response) {
                                            try{
                                                if(response!=null){
                                                    if(response.body().getStatus().equals("1")){

                                                        for(int i=0;i<response.body().getData().size();i++){
                                                            ListArea.add(response.body().getData().get(i).getArea());
                                                        }

                                                        dialog.dismiss();
                                                        adapteritem2= new ArrayAdapter<String>(LocationReport.this,R.layout.list_item,ListArea);
                                                        autoCompleteTextView2.setAdapter(adapteritem2);
                                                        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                area=adapterView.getItemAtPosition(i).toString();
                                                                city=autoCompleteTextView.getText().toString();
                                                                area=autoCompleteTextView2.getText().toString();
                                                                if (city.equals("")||area.equals("")||mPickDateButton.getText().toString().equals("Select Date Range")){
                                                                    Toast.makeText(LocationReport.this, "Select all Details", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else{
                                                                    getresult1(city,area,sqld1,sqld2);
                                                                    //do work here
                                                                }
                                                            }
                                                        });
                                                    }
                                                    else{
                                                        Toast.makeText(LocationReport.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            catch(Exception e){
                                                Log.e("Exception",e.getLocalizedMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GetAreaResponse> call, Throwable t) {
                                            Log.e("Failure",t.getLocalizedMessage());
                                        }
                                    });
                                }
                            });

                        }
                        else{
                            Toast.makeText(LocationReport.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetStringResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });


    }
    private void setadapter(List<ModelSalesmanVisited> model, String sdate, String tdate,String city,String area) {
        adapter=new AdapterVisitedCity(LocationReport.this,model,sdate,tdate,city,area);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult1(String city,String area,String sdate,String tdate) {
        apiInterface.readCityVisited(city,area,sdate,tdate).enqueue(new Callback<GetSalesmanVisitedResponse>() {
            @Override
            public void onResponse(Call<GetSalesmanVisitedResponse> call, Response<GetSalesmanVisitedResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            dialog.dismiss();
                            nodealersvisited.setText("Total Salesman : "+response.body().getData().size());
                            setadapter(response.body().getData(),sdate,tdate,city,area);

                        }
                        else{
                            nodealersvisited.setText("NO SALESMAN FOUND");
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
            public void onFailure(Call<GetSalesmanVisitedResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(LocationReport.this, ManagerDashboard.class);
        startActivity(i);
    }

}