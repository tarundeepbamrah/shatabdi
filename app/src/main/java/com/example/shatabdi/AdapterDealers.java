package com.example.shatabdi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDealers extends RecyclerView.Adapter<AdapterDealers.ViewHolder> {
    Context context;
    List<ModelDealers> modelList;
    public AdapterDealers(Context context, List<ModelDealers> modelList) {
        this.context = context;
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.dealer_view,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.shopname.setText(String.valueOf(modelList.get(position).getShopName()));
        holder.dealername.setText(modelList.get(position).getDealerName());
        holder.phone.setText(modelList.get(position).getPhone());
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, Conversation.class);
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
        TextView shopname,dealername,phone;
        LinearLayout bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname= itemView.findViewById(R.id.shopname);
            dealername= itemView.findViewById(R.id.dealername);
            phone= itemView.findViewById(R.id.phone);
            bg=itemView.findViewById(R.id.bg);
        }
    }
}
