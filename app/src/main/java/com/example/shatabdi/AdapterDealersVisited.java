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

public class AdapterDealersVisited extends RecyclerView.Adapter<AdapterDealersVisited.ViewHolder> {
    Context context;
    List<ModelDealers> modelList;
    String sdate,tdate,sname;

    public AdapterDealersVisited(Context context, List<ModelDealers> modelList,String sname, String sdate, String tdate) {
        this.context = context;
        this.modelList = modelList;
        this.sname = sname;
        this.sdate = sdate;
        this.tdate = tdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dealers_visited_list,null);
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
        holder.conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, ViewConversation.class);
                j.putExtra("sname",sname);
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                j.putExtra("dealer",modelList.get(holder.getAdapterPosition()).getDealer());
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
        TextView shopname,dealername,phone,city,area,date;
        AppCompatButton conversation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname= itemView.findViewById(R.id.dealer);
            dealername= itemView.findViewById(R.id.dealer_name);
            phone= itemView.findViewById(R.id.phone);
            city=itemView.findViewById(R.id.city);
            area=itemView.findViewById(R.id.area);
            date=itemView.findViewById(R.id.date);
            conversation=itemView.findViewById(R.id.convo);

        }
    }
}
