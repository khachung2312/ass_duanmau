package com.example.asmduanmau_hungnkph35189.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;


public class ThongKeViewHodel extends RecyclerView.ViewHolder {
    TextView tv_name_sach, numberSach;
    public ThongKeViewHodel(@NonNull View itemView) {
        super(itemView);
        tv_name_sach = itemView.findViewById(R.id.tv_name_sach_frag_top);
        numberSach = itemView.findViewById(R.id.tv_number_sach_frag_top);
    }
}
