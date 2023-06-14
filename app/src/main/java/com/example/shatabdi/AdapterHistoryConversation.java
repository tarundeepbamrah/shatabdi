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

public class AdapterHistoryConversation extends RecyclerView.Adapter<AdapterHistoryConversation.ViewHolder>{
    Context context;
    List<ModelConversation> modelList;

    public AdapterHistoryConversation(Context context, List<ModelConversation> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.historyconvo_list,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.conversation.setText(modelList.get(position).getConversation());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView conversation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            conversation= itemView.findViewById(R.id.conversation);
        }
    }
}
