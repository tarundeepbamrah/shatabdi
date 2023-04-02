package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SalesmanDashboard extends AppCompatActivity {

    String[] city = {"Delhi","Noida","Meerut"};
    String[] area = {"Gandhi Nagar","Gill Road"};

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView2;
    ArrayAdapter<String> adapteritem;
    ArrayAdapter<String> adapteritem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_dashboard);

        autoCompleteTextView=findViewById(R.id.auto_complete_txt_city);
        adapteritem= new ArrayAdapter<String>(this,R.layout.list_item,city);
        autoCompleteTextView.setAdapter(adapteritem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city=adapterView.getItemAtPosition(i).toString();
            }
        });

        autoCompleteTextView2=findViewById(R.id.auto_complete_txt_area);
        adapteritem2= new ArrayAdapter<String>(this,R.layout.list_item,area);
        autoCompleteTextView2.setAdapter(adapteritem2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String area=adapterView.getItemAtPosition(i).toString();
            }
        });

    }
}