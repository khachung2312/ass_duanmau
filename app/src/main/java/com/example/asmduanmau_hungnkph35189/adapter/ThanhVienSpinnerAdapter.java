package com.example.asmduanmau_hungnkph35189.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ArrayList<ThanhVien> arrThanhVien;
    TextView tv_name_thanh_vien;

    public ThanhVienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> arrThanhVien) {
        super(context, 0, arrThanhVien);
        this.arrThanhVien = arrThanhVien;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.thanh_vien_item_spinner, null);
        }
        final  ThanhVien thanhVien = arrThanhVien.get(position);
        if(thanhVien != null) {
            tv_name_thanh_vien = view.findViewById(R.id.tv_name_thanh_vien_spinner_frag_pm);
            tv_name_thanh_vien.setText(thanhVien.hoTen);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.thanh_vien_item_spinner_drop, null);
        }
        final  ThanhVien thanhVien = arrThanhVien.get(position);
        if(thanhVien != null) {
            tv_name_thanh_vien = view.findViewById(R.id.tv_name_thanh_vien_spinner_frag_pm_drop);
            tv_name_thanh_vien.setText(thanhVien.hoTen);
        }
        return view;
    }
}
