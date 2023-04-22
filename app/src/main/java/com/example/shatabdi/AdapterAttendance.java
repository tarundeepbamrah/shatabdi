package com.example.shatabdi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.ViewHolder> {
    Context context;
    List<ModelAttendance> modelList;
    List<String> dates;

    public AdapterAttendance(Context context, List<ModelAttendance> modelList,List<String> dates) {
        this.context = context;
        this.modelList = modelList;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendance_list_view,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i;
        for(i=0;i<modelList.size();i++){
            if(dates.get(position).equals(modelList.get(i).getAttendance())){
                holder.date.setText(dates.get(position));
                holder.attendance.setText("P");
                holder.bg.setBackgroundColor(Color.parseColor("#992F7E2F"));
                return;
            }
        }
        if(i==modelList.size()){
            holder.date.setText(dates.get(position));
            holder.attendance.setText("A");
            holder.bg.setBackgroundColor(Color.parseColor("#99FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,attendance;
        LinearLayout bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.date);
            attendance=itemView.findViewById(R.id.attendance);
            bg=itemView.findViewById(R.id.bg);
        }
    }
}
