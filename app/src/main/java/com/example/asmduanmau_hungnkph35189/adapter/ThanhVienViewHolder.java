package com.example.asmduanmau_hungnkph35189.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;


public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name_tv, tv_bith_date, tv_cccd_tv;
    ImageView img_delete_tv;
    View item_tv;
    public ThanhVienViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name_tv = itemView.findViewById(R.id.tv_name_tv_frag_tv);
        tv_bith_date = itemView.findViewById(R.id.tv_bird_tv_frag_tv);
        tv_cccd_tv = itemView.findViewById(R.id.tv_cccd_tv_frag_tv);
        img_delete_tv = itemView.findViewById(R.id.img_delete_thanh_vien);
        item_tv = itemView.findViewById(R.id.item_tv_frag_tv);
    }
}
