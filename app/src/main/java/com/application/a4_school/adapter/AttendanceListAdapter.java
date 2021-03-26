package com.application.a4_school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Attendance;
import com.application.a4_school.Models.Home;
import com.application.a4_school.R;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.ListViewHolder> {
    private List<Attendance> list;
    private Context context;

    public AttendanceListAdapter(List<Attendance> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private OnItemClickCallbackHome onItemClickCallbackHome;

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_class_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {
        holder.shtgl.setText(list.get(position).getDate());
        holder.shtype.setText(list.get(position).getType());
        if (list.get(position).getType().equals("Theory")) {
            holder.shcompletedcount.setVisibility(View.INVISIBLE);
        }
        holder.shtittle.setText(list.get(position).getTitle());
        holder.shcompletedcount.setText(String.valueOf(list.get(position).getCompletedcount())+" Completed this task");
        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickCallbackHome {
        void onItemClicked(Attendance attendanceList);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView shtgl, shtype, shtittle, shcompletedcount, btnCheck;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            shtgl = itemView.findViewById(R.id.shtglclass);
            shtype = itemView.findViewById(R.id.shtasktheory);
            shtittle = itemView.findViewById(R.id.shtittleclass);
            shcompletedcount = itemView.findViewById(R.id.shcompletedtaskclass);
            btnCheck = itemView.findViewById(R.id.btn_checktaskclass);
        }
    }
}
