package com.example.asmduanmau_hungnkph35189.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.adapter.LoaiSachAdapter;
import com.example.asmduanmau_hungnkph35189.dao.LoaiSachDAO;
import com.example.asmduanmau_hungnkph35189.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {
    LayoutInflater inflater;
    View viewDialogAddLoaiSach;
    FloatingActionButton floatingActionButton;
    Context mContext;

    ArrayList<LoaiSach> arrLoaiSach = new ArrayList<>();
    RecyclerView rcyLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSachAdapter loaiSachAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        inflater = getLayoutInflater();
        floatingActionButton = view.findViewById(R.id.float_btn_add_loai_sach);
        viewDialogAddLoaiSach = inflater.inflate(R.layout.dialog_add_loai_sach, null);

        rcyLoaiSach= view.findViewById(R.id.rcv_loai_sach);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcyLoaiSach.setLayoutManager(layoutManager);

        loaiSachDAO = new LoaiSachDAO(mContext);
        arrLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAllLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(mContext, arrLoaiSach);
        rcyLoaiSach.setAdapter(loaiSachAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDialogAddLoaiSach.getParent() != null) {
                    ((ViewGroup)viewDialogAddLoaiSach.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(viewDialogAddLoaiSach);
                EditText ed_name_loai_sach;
                Button btn_add_loai_sach, btn_cancel_add_loai_sach;
                TextInputLayout layout_ed_name_loai_sach;
                layout_ed_name_loai_sach = viewDialogAddLoaiSach.findViewById(R.id.input_name_loai_sach);
                ed_name_loai_sach = viewDialogAddLoaiSach.findViewById(R.id.ed_name_loai_sach);
                btn_add_loai_sach = viewDialogAddLoaiSach.findViewById(R.id.btn_dialog_add_loai_sach);
                btn_cancel_add_loai_sach = viewDialogAddLoaiSach.findViewById(R.id.btn_dialog_cancle_add_loai_sach);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_cancel_add_loai_sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ed_name_loai_sach.setText("");
                        alertDialog.dismiss();
                    }
                });
                btn_add_loai_sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = 1;
                        if(ed_name_loai_sach.getText().toString().isEmpty()) {
                            layout_ed_name_loai_sach.setError("Tên loại sách đang trống!");
                            check = -1;
                        }else if(ed_name_loai_sach.getText().toString().length() < 3) {
                            layout_ed_name_loai_sach.setError("Tên loại sách phải dài hơn 3 kí tự!");
                            check = -1;
                        }else if(ed_name_loai_sach.getText().toString().length()> 16) {
                            layout_ed_name_loai_sach.setError("Tên loại sách không được dài quá 16 kí tự!");
                            check = -1;
                        }else {
                            layout_ed_name_loai_sach.setError("");
                        }

                        if(check > 0) {
                            LoaiSach loaiSach = new LoaiSach();
                            loaiSach.tenloai = ed_name_loai_sach.getText().toString();
                            loaiSachDAO.insertLoaiSach(loaiSach);
                            arrLoaiSach = new ArrayList<>();
                            arrLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAllLoaiSach();
                            loaiSachAdapter = new LoaiSachAdapter(mContext, arrLoaiSach);
                            rcyLoaiSach.setAdapter(loaiSachAdapter);
                            alertDialog.dismiss();
                            Snackbar.make(view, "Thêm loại sách thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.primary_color))
                                    .show();
                            ed_name_loai_sach.setText("");
                            layout_ed_name_loai_sach.setError("");
                        }
                    }
                });
            }
        });
    }
}
