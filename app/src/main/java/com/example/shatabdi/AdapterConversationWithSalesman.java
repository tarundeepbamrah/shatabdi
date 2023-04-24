package com.example.shatabdi;

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

public class AdapterConversationWithSalesman extends RecyclerView.Adapter<AdapterConversationWithSalesman.ViewHolder> {
    Context context;
    List<ModelConversation> modelList;
    String sname,date,dealer,sdate,tdate;

    public AdapterConversationWithSalesman(Context context, List<ModelConversation> modelList,String sname,String date,String dealer,String sdate,String tdate) {
        this.context = context;
        this.modelList = modelList;
        this.sname = sname;
        this.date = date;
        this.sdate = sdate;
        this.tdate = tdate;
        this.dealer = dealer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.conversation_list_view,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.conversation.setText(modelList.get(position).getConversation());
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, ViewSalesmanLocation.class);
                j.putExtra("sname",sname);
                j.putExtra("date",date);
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                j.putExtra("dealer",dealer);
                j.putExtra("conversation",modelList.get(holder.getAdapterPosition()).getConversation());
                context.startActivity(j);
            }
        });
        holder.bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, PhotoViewWithSalesman.class);
                j.putExtra("sname",sname);
                j.putExtra("date",date);
                j.putExtra("sdate",sdate);
                j.putExtra("tdate",tdate);
                j.putExtra("dealer",dealer);
                j.putExtra("conversation",modelList.get(holder.getAdapterPosition()).getConversation());
                context.startActivity(j);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView conversation;
        AppCompatButton location;
        LinearLayout bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            conversation= itemView.findViewById(R.id.conversation);
            location=itemView.findViewById(R.id.locview);
            bg=itemView.findViewById(R.id.bg);
        }
    }
}
