package com.example.asmduanmau_hungnkph35189.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;


public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name_tv, tv_name_sach, tv_tien_thue, tv_status, tv_ngay_thue;
    ImageView img_delete_pm;
    View item_pm;
    public PhieuMuonViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name_tv = itemView.findViewById(R.id.tv_name_tv_frag_phieu_muon);
        tv_name_sach = itemView.findViewById(R.id.tv_ten_sach_frag_phieu_muon);
        tv_tien_thue = itemView.findViewById(R.id.tv_tien_thue_phieu_muon);
        tv_status = itemView.findViewById(R.id.tv_status_phieu_muon);
        tv_ngay_thue = itemView.findViewById(R.id.tv_ngay_muon_phieu_muon);
        img_delete_pm = itemView.findViewById(R.id.img_delete_phieu_muon);
        item_pm = itemView.findViewById(R.id.item_pm_frag_phieu_muon);
    }
}
