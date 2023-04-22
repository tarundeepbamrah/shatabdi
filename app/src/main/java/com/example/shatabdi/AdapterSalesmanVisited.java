package com.example.shatabdi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSalesmanVisited extends RecyclerView.Adapter<AdapterSalesmanVisited.ViewHolder> {
    Context context;
    List<ModelSalesmanVisited> modelList;
    String dealer,sdate,tdate;

    public AdapterSalesmanVisited(Context context, List<ModelSalesmanVisited> modelList,String dealer, String sdate, String tdate) {
        this.context = context;
        this.modelList = modelList;
        this.dealer = dealer;
        this.sdate = sdate;
        this.tdate = tdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.salesman_visited_list,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.salesmanname.setText(modelList.get(position).getSalesman_name());
        holder.salesmanphone.setText(modelList.get(position).getSalesman_phone());
        holder.date.setText(modelList.get(position).getDate());
        holder.conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, ConversationWithSalesman.class);
                j.putExtra("dealer",dealer);
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                j.putExtra("sname",modelList.get(holder.getAdapterPosition()).getSalesman_name());
                j.putExtra("date",modelList.get(holder.getAdapterPosition()).getDate());
                context.startActivity(j);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView salesmanname,salesmanphone,date;
        AppCompatButton conversation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salesmanname= itemView.findViewById(R.id.salesmanname);
            salesmanphone= itemView.findViewById(R.id.salesmanphone);
            date=itemView.findViewById(R.id.date);
            conversation=itemView.findViewById(R.id.convo);

        }
    }
}
