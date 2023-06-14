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

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    Context context;
    List<ModelDealers> modelList;
    String name;

    public AdapterHistory(Context context, List<ModelDealers> modelList,String name) {
        this.context = context;
        this.modelList = modelList;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dealerhistory,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shopname.setText(modelList.get(position).getDealer());
        holder.dealername.setText(modelList.get(position).getDealer_name());
        holder.phone.setText(modelList.get(position).getPhone());
        holder.city.setText(modelList.get(position).getCity());
        holder.area.setText(modelList.get(position).getArea());
        holder.date.setText(modelList.get(position).getDate());
        holder.convo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, HistoryConversation.class);
                j.putExtra("dealer",modelList.get(holder.getAdapterPosition()).getDealer());
                j.putExtra("date",modelList.get(holder.getAdapterPosition()).getDate());
                j.putExtra("name",name);
                context.startActivity(j);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopname,dealername,phone,city,area,date;
        AppCompatButton convo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname= itemView.findViewById(R.id.dealer);
            dealername= itemView.findViewById(R.id.dealer_name);
            phone= itemView.findViewById(R.id.phone);
            city=itemView.findViewById(R.id.city);
            area=itemView.findViewById(R.id.area);
            convo=itemView.findViewById(R.id.convo);
            date=itemView.findViewById(R.id.date);
        }
    }
}
