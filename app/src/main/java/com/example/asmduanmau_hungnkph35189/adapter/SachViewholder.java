package com.example.asmduanmau_hungnkph35189.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;

public class SachViewholder extends RecyclerView.ViewHolder {
    TextView tv_name_sach, tv_gia_thue, tv_loai_sach, tv_nam_xuat_ban;
    ImageView img_delete_sach;
    View item_sach;
    public SachViewholder(@NonNull View itemView) {
        super(itemView);
        tv_name_sach = itemView.findViewById(R.id.tv_name_sach_frag_sach);
        tv_gia_thue = itemView.findViewById(R.id.tv_gia_thue_sach_frag_sach);
        tv_loai_sach = itemView.findViewById(R.id.tv_name_loai_sach_frag_sach);
        tv_nam_xuat_ban = itemView.findViewById(R.id.tv_nam_xuat_ban_frag_sach);
        img_delete_sach = itemView.findViewById(R.id.img_delete_sach);
        item_sach = itemView.findViewById(R.id.item_sach_frag_sach);
    }
}

