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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDealers extends RecyclerView.Adapter<AdapterDealers.ViewHolder> {
    Context context;
    List<ModelDealer> modelList;
    String name,phone,pos;

    public AdapterDealers(Context context, List<ModelDealer> modelList, String name, String phone, String pos) {
        this.context = context;
        this.modelList = modelList;
        this.name=name;
        this.phone=phone;
        this.pos=pos;
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
            holder.shopname.setText(String.valueOf(modelList.get(position).getDealer()));
            holder.dealername.setText(modelList.get(position).getDealer_name());
            holder.phone.setText(modelList.get(position).getPhone());
            holder.bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent j = new Intent(context, Conversation.class);
                    j.putExtra("id",modelList.get(position).getId());
                    j.putExtra("city",modelList.get(position).getCity());
                    j.putExtra("area",modelList.get(position).getArea());
                    j.putExtra("name",name);
                    j.putExtra("phone",phone);
                    j.putExtra("position",pos);
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
