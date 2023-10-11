package com.example.asmduanmau_hungnkph35189.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.dao.LoaiSachDAO;
import com.example.asmduanmau_hungnkph35189.dao.SachDAO;
import com.example.asmduanmau_hungnkph35189.model.LoaiSach;
import com.example.asmduanmau_hungnkph35189.model.Sach;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachViewHolder> {
    ArrayList<LoaiSach> arrLoaiSach = new ArrayList<>();
    LoaiSachDAO loaiSachDAO;
    Context context;

    LayoutInflater inflater;
    View viewDeleteLoaiSach, viewUpdateLoaiSach;
    SachDAO sachDAO;
    ArrayList<Sach> arrSach = new ArrayList<>();

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> arrLoaiSach) {
        this.context = context;
        this.arrLoaiSach = arrLoaiSach;
    }
    View viewAlert;
    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewItem = inflater.inflate(R.layout.custom_loai_sach, parent, false);
        LoaiSachViewHolder loaiSachViewHolder = new LoaiSachViewHolder(viewItem);
        viewAlert = parent;
        return loaiSachViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        loaiSachDAO = new LoaiSachDAO(context);
        LoaiSach loaiSach = arrLoaiSach.get(position);
        holder.tv_name_loai_sach.setText(loaiSach.tenloai);

        inflater = LayoutInflater.from(context);
        viewDeleteLoaiSach = inflater.inflate(R.layout.dialog_delete_loai_sach, null);
        viewUpdateLoaiSach = inflater.inflate(R.layout.dialog_update_loai_sach, null);

        holder.img_delete_loai_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDeleteLoaiSach.getParent() != null) {
                    ((ViewGroup)viewDeleteLoaiSach.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewDeleteLoaiSach);

                Button btn_delete_loai_sach, btn_cancel_delete_loai_sach;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_delete_loai_sach = viewDeleteLoaiSach.findViewById(R.id.btn_dialog_delete_loai_sach);
                btn_cancel_delete_loai_sach = viewDeleteLoaiSach.findViewById(R.id.btn_dialog_cancel_delete_loai_sach);
                btn_cancel_delete_loai_sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_delete_loai_sach.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        sachDAO = new SachDAO(context);
                        arrSach = (ArrayList<Sach>) sachDAO.getAllSach();
                        for(int i = 0; i < arrSach.size(); i++) {
                           if(arrSach.get(i).maLoai == loaiSach.maloai) {
                               check = -1;
                           }
                        }
                        if(check > 0) {
                            loaiSachDAO.deleteLoaiSach(loaiSach.maloai + "");
                            arrLoaiSach.remove(loaiSach);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Xoá loại sách thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                                    .show();
                        }else {
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Không thể xoá do loại sách đang tồn tại ở màn sách!", Snackbar.LENGTH_LONG)
                                    .setAction("Đóng", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    })
                                    .show();
                        }

                    }
                });
            }
        });
        holder.item_Loai_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewUpdateLoaiSach.getParent() != null) {
                    ((ViewGroup)viewUpdateLoaiSach.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewUpdateLoaiSach);

                EditText ed_update_name_loai_sach;
                Button btn_update_loai_sach, btn_cancel_update;
                TextInputLayout layout_ed_loai_sach;
                layout_ed_loai_sach = viewUpdateLoaiSach.findViewById(R.id.input_update_name_loai_sach);
                ed_update_name_loai_sach = viewUpdateLoaiSach.findViewById(R.id.ed_update_name_loai_sach);
                btn_update_loai_sach = viewUpdateLoaiSach.findViewById(R.id.btn_dialog_update_loai_sach);
                btn_cancel_update = viewUpdateLoaiSach.findViewById(R.id.btn_dialog_cancle_update_loai_sach);
                layout_ed_loai_sach.setError("");
                ed_update_name_loai_sach.setText(loaiSach.tenloai);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                btn_update_loai_sach.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        if(ed_update_name_loai_sach.getText().toString().isEmpty()) {
                            layout_ed_loai_sach.setError("Tên loại sách đang trống!");
                            check = -1;
                        }else if(ed_update_name_loai_sach.getText().toString().length() < 3) {
                            layout_ed_loai_sach.setError("Tên loại sách phải dài hơn 3 kí tự!");
                            check = -1;
                        }else if(ed_update_name_loai_sach.getText().toString().length()> 16) {
                            layout_ed_loai_sach.setError("Tên loại sách không được dài quá 16 kí tự!");
                            check = -1;
                        }else {
                            layout_ed_loai_sach.setError("");
                        }
                        if (check> 0) {
                            LoaiSach loaiSach1 = new LoaiSach();
                            loaiSach1.maloai = loaiSach.maloai;
                            loaiSach1.tenloai = ed_update_name_loai_sach.getText().toString();
                            arrLoaiSach.set(position, loaiSach1);
                            loaiSachDAO.updateLoaiSach(loaiSach1);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Sửa loại sách thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                                    .show();
                            ed_update_name_loai_sach.setText("");
                            layout_ed_loai_sach.setError("");
                        }
                    }
                });
                btn_cancel_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrLoaiSach.size();
    }
}
