package com.example.shatabdi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSalesmanReport extends RecyclerView.Adapter<AdapterSalesmanReport.ViewHolder>{

    Context context;
    List<ModelSalesmanReport> modelList;
    String sdate,tdate;

    public AdapterSalesmanReport(Context context, List<ModelSalesmanReport> modelList, String sdate, String tdate) {
        this.context = context;
        this.modelList = modelList;
        this.sdate=sdate;
        this.tdate=tdate;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.salesmanreportlist,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(String.valueOf(modelList.get(position).getSalesman_name()));
        holder.salesman_phone.setText(modelList.get(position).getSalesman_phone());
        holder.background.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {
        //Intent j = new Intent(context, Conversation.class);
        //context.startActivity(j);
        }
        });
        holder.dealersvisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, DealersVisited.class);
                //j.putExtra("name",name);
                j.putExtra("sname",modelList.get(position).getSalesman_name());
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                context.startActivity(j);
            }
        });
        holder.attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, Attendance.class);
                j.putExtra("sname",modelList.get(position).getSalesman_name());
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                context.startActivity(j);
            }
        });
    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,salesman_phone;
        AppCompatButton dealersvisited,attendance;
        LinearLayout background;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            salesman_phone= itemView.findViewById(R.id.phone);
            dealersvisited=itemView.findViewById(R.id.dealersvisited);
            attendance=itemView.findViewById(R.id.attendance);
            background=itemView.findViewById(R.id.background);
        }
    }

}
