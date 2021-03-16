package com.application.a4_school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Home;
import com.application.a4_school.Models.Schedule;
import com.application.a4_school.R;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {
    private List<Home> list;
    private Context context;

    public HomeListAdapter(List<Home> list, Context context){
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
        holder.shJudul.setText(list.get(position).getJudul());
        holder.shRoom.setText(list.get(position).getDetail());
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.img.setBackgroundDrawable(ContextCompat.getDrawable(context, list.get(position).getBghome()) );
        } else {
            holder.img.setBackground(ContextCompat.getDrawable(context, list.get(position).getBghome()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView shJudul, shRoom;
        ImageView img;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            shJudul = itemView.findViewById(R.id.nama_mapel);
            shRoom = itemView.findViewById(R.id.ruang_jam);
            img = itemView.findViewById(R.id.img_home);
            
        }
    }
}
