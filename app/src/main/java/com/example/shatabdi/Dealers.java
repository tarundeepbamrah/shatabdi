package com.example.shatabdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class Dealers extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterDealers adapter;

    ModelDealers model1 = new ModelDealers("Tarundeep","Parminder","7617613888");
    ModelDealers model2 = new ModelDealers("Devendra","Satvinder","7617613888");
    ModelDealers model3 = new ModelDealers("Ayush","Gagan","7617613888");

    List<ModelDealers> modellist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealers);

        modellist.add(model1);
        modellist.add(model2);
        modellist.add(model3);
        modellist.add(model1);
        modellist.add(model2);
        initialization();
        setadapter(modellist);

    }

    private void initialization(){
        recyclerView = findViewById(R.id.recycler_view);
    }


    private void setadapter(List<ModelDealers> model){
        adapter= new AdapterDealers(Dealers.this,model);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Dealers.this );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}