package com.app.nabhini.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.nabhini.Modle.Alarms;
import com.app.nabhini.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {

    private Context context;
    private List<Alarms> objects;

    public AlarmAdapter(Context context, List<Alarms> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        Alarms data = objects.get(i);

        myViewHolder.tv_name_task.setText(data.getTitle_alarm());
        myViewHolder.tv_time.setText(data.getTime()+" "+data.getDate());


    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_task, tv_level, tv_date, tv_time;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_task = itemView.findViewById(R.id.tv_name_alarm);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}
