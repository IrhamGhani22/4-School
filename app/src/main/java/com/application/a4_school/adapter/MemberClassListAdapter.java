package com.application.a4_school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.a4_school.Models.Members;
import com.application.a4_school.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberClassListAdapter extends RecyclerView.Adapter<MemberClassListAdapter.MyViewHolder> {
    private List<Members> list;
    private Context context;

    public MemberClassListAdapter(List<Members> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_memberclass_btmsheet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameMembers.setText(list.get(position).getName());
        holder.nisMembers.setText(list.get(position).getNis());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgMemberList;
        TextView nameMembers, nisMembers;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMemberList   = itemView.findViewById(R.id.member_image);
            nameMembers     = itemView.findViewById(R.id.memberclass_name);
            nisMembers      = itemView.findViewById(R.id.memberclass_nis);
        }
    }
}
