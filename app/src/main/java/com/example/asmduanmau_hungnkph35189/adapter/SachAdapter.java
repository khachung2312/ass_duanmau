package com.example.asmduanmau_hungnkph35189.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.dao.LoaiSachDAO;
import com.example.asmduanmau_hungnkph35189.dao.PhieuMuonDAO;
import com.example.asmduanmau_hungnkph35189.dao.SachDAO;
import com.example.asmduanmau_hungnkph35189.model.LoaiSach;
import com.example.asmduanmau_hungnkph35189.model.PhieuMuon;
import com.example.asmduanmau_hungnkph35189.model.Sach;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachViewholder> {
    ArrayList<Sach> arrSach = new ArrayList<>();
    SachDAO sachDAO;
    Context context;
    Spinner spinner_update_loai_sach;
    LayoutInflater inflater;
    View viewDeleteSach, viewUpdateSach;
    ArrayList<LoaiSach> arrLoaiSach = new ArrayList<>();
    LoaiSachSpinnerAdapter loaiSachAdapter;
    int maLoaiSach;
    LoaiSachDAO loaiSachDAO;
    String nameLoaiSach;
    View viewAlert;
    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> arrPM = new ArrayList<>();

    public SachAdapter(Context context, ArrayList<Sach> arrSach) {
        this.context = context;
        this.arrSach = arrSach;
    }
    @NonNull
    @Override
    public SachViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewItem = inflater.inflate(R.layout.custom_sach, parent, false);
        SachViewholder sachViewholder = new SachViewholder(viewItem);
        viewAlert = parent;
        return sachViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewholder holder, int position) {
        sachDAO = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);
        Sach sach = arrSach.get(position);
        if(sach != null) {
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach= loaiSachDAO.getIdLoaiSach(String.valueOf(sach.maLoai));
            holder.tv_name_sach.setText(sach.tenSach);
            holder.tv_gia_thue.setText(sach.giaThue + " VND");
            holder.tv_loai_sach.setText(loaiSach.tenloai);
            holder.tv_nam_xuat_ban.setText(sach.namXuatBan +"");
            inflater = LayoutInflater.from(context);
        }
        viewDeleteSach = inflater.inflate(R.layout.dialog_delete_sach, null);
        viewUpdateSach = inflater.inflate(R.layout.dialog_update_sach, null);

        holder.img_delete_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDeleteSach.getParent() != null) {
                    ((ViewGroup)viewDeleteSach.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewDeleteSach);

                Button btn_delete_sach, btn_cancel_delete_sach;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_delete_sach = viewDeleteSach.findViewById(R.id.btn_dialog_delete_sach);
                btn_cancel_delete_sach = viewDeleteSach.findViewById(R.id.btn_dialog_cancel_delete_sach);
                btn_cancel_delete_sach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_delete_sach.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        phieuMuonDAO = new PhieuMuonDAO(context);
                        arrPM = (ArrayList<PhieuMuon>) phieuMuonDAO.getAllPhieuMuon();
                        for(int i = 0; i < arrPM.size(); i++) {
                           if(arrPM.get(i).maSach == sach.maSach) {
                               check = -1;
                           }
                        }
                        if(check>0) {
                            sachDAO.deleteSach(sach.maSach + "");
                            arrSach.remove(sach);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Xoá  sách thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                                    .show();
                        }else {
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Không thể xoá do  sách đang tồn tại ở màn phiếu mượn!", Snackbar.LENGTH_LONG)
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
        holder.item_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewUpdateSach.getParent() != null) {
                    ((ViewGroup)viewUpdateSach.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewUpdateSach);

                EditText ed_update_name_sach, ed_update_gia_thue, ed_update_pub_year;
                Button btn_update_sach, btn_cancel_update_sach;
                TextInputLayout input_name_sach, input_price_sach, input_pub_year;
                input_name_sach = viewUpdateSach.findViewById(R.id.input_name_sach_update);
                input_price_sach = viewUpdateSach.findViewById(R.id.input_price_sach_update);
                input_pub_year = viewUpdateSach.findViewById(R.id.input_update_pub_year);
                spinner_update_loai_sach = viewUpdateSach.findViewById(R.id.spn_update_sach_frag_sach);
                ed_update_name_sach = viewUpdateSach.findViewById(R.id.ed_update_name_sach_frag_sach);
                ed_update_gia_thue = viewUpdateSach.findViewById(R.id.ed_update_gia_thue_sach_frag_sach);
                ed_update_pub_year = viewUpdateSach.findViewById(R.id.ed_update_pub_year);
                btn_update_sach = viewUpdateSach.findViewById(R.id.btn_dialog_update_sach);
                btn_cancel_update_sach = viewUpdateSach.findViewById(R.id.btn_dialog_cancle_update_sach);
                input_name_sach.setError("");
                input_price_sach.setError("");
                input_pub_year.setError("");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                arrLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAllLoaiSach();
                loaiSachAdapter = new LoaiSachSpinnerAdapter(context, arrLoaiSach);
                spinner_update_loai_sach.setAdapter(loaiSachAdapter);
                spinner_update_loai_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maLoaiSach = arrLoaiSach.get(i).maloai;
                        nameLoaiSach = arrLoaiSach.get(i).tenloai;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                LoaiSach loaiSach= loaiSachDAO.getIdLoaiSach(String.valueOf(sach.maLoai));
                int positionLoaiSach = 0;
                for (int i = 0; i < arrLoaiSach.size(); i++) {
                    if(loaiSach.maloai == (arrLoaiSach.get(i).maloai)) {
                        positionLoaiSach = i;
                        spinner_update_loai_sach.setSelection(positionLoaiSach);
                    }
                }
                ed_update_gia_thue.setText(sach.giaThue + "");
                ed_update_name_sach.setText(sach.tenSach);
                ed_update_pub_year.setText(sach.namXuatBan + "");

                btn_update_sach.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        if (ed_update_name_sach.getText().toString().isEmpty()) {
                            input_name_sach.setError("Tên sách đang trống!");
                            check = -1;
                        }else if(ed_update_name_sach.getText().length()<3) {
                            input_name_sach.setError("Tên sách không được nhỏ hơn 3 kí tự!");
                            check = -1;
                        }else if(ed_update_name_sach.getText().length()> 16) {
                            input_name_sach.setError("Tên sách không được dài hơn 16 kí tự");
                            check = -1;
                        }else {
                            input_name_sach.setError("");
                        }
                        if (ed_update_gia_thue.getText().toString().isEmpty()) {
                            input_price_sach.setError("Giá sách đang trống!");
                            check = -1;
                        }else if(ed_update_gia_thue.getText().length()<4 ||ed_update_gia_thue.getText().length()> 16) {
                            input_price_sach.setError("Giá sách không hợp lệ!");
                            check = -1;
                        }else {
                            input_price_sach.setError("");
                        }

                        if(ed_update_pub_year.getText().toString().isEmpty()) {
                            input_pub_year.setError("Năm xuất bản trống!");
                            check = -1;
                        } else if(ed_update_pub_year.getText().toString().length() > 4) {
                            input_pub_year.setError("Năm sinh không được dài hơn 4 kí tự");
                        } else {
                            input_pub_year.setError("");
                        }

                       if(check > 0) {
                           Sach sach1 = new Sach();
                           sach1.maSach = sach.maSach;
                           sach1.tenSach = ed_update_name_sach.getText().toString();
                           sach1.giaThue = Integer.parseInt(ed_update_gia_thue.getText().toString());
                           sach1.maLoai = maLoaiSach;
                           sach1.namXuatBan = Integer.parseInt(ed_update_pub_year.getText().toString());
                           arrSach.set(position, sach1);
                           sachDAO.updateSach(sach1);
                           notifyDataSetChanged();
                           alertDialog.dismiss();
                           Snackbar.make(viewAlert, "Sửa  sách thành công!", Snackbar.LENGTH_LONG)
                                   .setBackgroundTint(R.color.primary_color)
                                   .show();
                           ed_update_gia_thue.setText("");
                           ed_update_name_sach.setText("");
                           ed_update_pub_year.setText("");
                           input_name_sach.setError("");
                           input_price_sach.setError("");
                           input_pub_year.setError("");

                       }
                    }
                });
                btn_cancel_update_sach.setOnClickListener(new View.OnClickListener() {
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
        return arrSach.size();
    }
}
