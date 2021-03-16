package com.application.a4_school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Schedule;
import com.application.a4_school.R;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {
    private List<Schedule> list;
    private Context context;

    public HomeListAdapter(List<Schedule> list, Context context){
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeListAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        if (list.get(position).getJam_mulai() != null) {
            holder.shTime.setText(list.get(position).getJam_mulai() + " - " + list.get(position).getJam_selesai());
            holder.shRoom.setText(list.get(position).getRuangan());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView shTime, shRoom;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            shTime = itemView.findViewById(R.id.nama_mapel);
            shRoom = itemView.findViewById(R.id.ruang_jam);
        }
    }
}
