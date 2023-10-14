package com.example.asmduanmau_hungnkph35189.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.dao.PhieuMuonDAO;
import com.example.asmduanmau_hungnkph35189.dao.ThanhVienDAO;
import com.example.asmduanmau_hungnkph35189.fragment.ThanhVienFragment;
import com.example.asmduanmau_hungnkph35189.model.PhieuMuon;
import com.example.asmduanmau_hungnkph35189.model.ThanhVien;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienViewHolder> {
    ArrayList<ThanhVien> arrThanhVien = new ArrayList<>();
    ThanhVienDAO thanhVienDAO;
    Context context;

    LayoutInflater inflater;
    View viewDeleteThanhVien, viewUpdateThanhVien;

    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> arrPM = new ArrayList<>();
    ThanhVienFragment thanhVienFragment;
    View viewAlert;


    public ThanhVienAdapter(Context context,ThanhVienFragment thanhVienFragment, ArrayList<ThanhVien> arrThanhVien) {
        this.context = context;
        this.arrThanhVien = arrThanhVien;
        this.thanhVienFragment = thanhVienFragment;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewItem = inflater.inflate(R.layout.custom_thanh_vien, parent, false);
        ThanhVienViewHolder thanhVienViewHolder = new ThanhVienViewHolder(viewItem);
        viewAlert = parent;
        return thanhVienViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        thanhVienFragment = new ThanhVienFragment();
        thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien thanhVien = arrThanhVien.get(position);
        holder.tv_name_tv.setText(thanhVien.hoTen);
        holder.tv_bith_date.setText(thanhVien.namSinh);
        holder.tv_cccd_tv.setText(String.valueOf(thanhVien.cccd));

        inflater = LayoutInflater.from(context);
        viewDeleteThanhVien = inflater.inflate(R.layout.dialog_delete_tv, null);
        viewUpdateThanhVien = inflater.inflate(R.layout.dialog_update_tv, null);

        holder.img_delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDeleteThanhVien.getParent() != null) {
                    ((ViewGroup)viewDeleteThanhVien.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewDeleteThanhVien);

                Button btn_delete_tv, btn_cancel_delete_tv;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_delete_tv = viewDeleteThanhVien.findViewById(R.id.btn_dialog_delete_tv);
                btn_cancel_delete_tv = viewDeleteThanhVien.findViewById(R.id.btn_dialog_cancel_delete_tv);
                btn_cancel_delete_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_delete_tv.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        phieuMuonDAO = new PhieuMuonDAO(context);
                        arrPM = (ArrayList<PhieuMuon>) phieuMuonDAO.getAllPhieuMuon();
                        for(int i = 0; i < arrPM.size(); i++) {
                           if(arrPM.get(i).maTV == thanhVien.maTV) {
                               check = -1;
                           }
                        }
                        if(check > 0) {
                            thanhVienDAO.deleteThanhVien(thanhVien.maTV + "");
                            arrThanhVien.remove(thanhVien);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Xoá thành viên thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                                    .show();
                        }else {
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Không thể xoá do thành viên đang tồn tại ở màn phiếu mượn!", Snackbar.LENGTH_LONG)
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
        holder.item_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewUpdateThanhVien.getParent() != null) {
                    ((ViewGroup)viewUpdateThanhVien.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewUpdateThanhVien);

                EditText ed_update_name_tv, ed_update_bith_date, ed_update_cccd_tv;
                Button btn_update_tv, btn_cancel_update;
                TextInputLayout layout_ed_bith, input_name_tv, input_cccd_tv;
                input_name_tv = viewUpdateThanhVien.findViewById(R.id.input_name_tv_update);
                layout_ed_bith = viewUpdateThanhVien.findViewById(R.id.input_update_bith_date_tv);
                input_cccd_tv = viewUpdateThanhVien.findViewById(R.id.input_cccd_tv_update);
                ed_update_name_tv = viewUpdateThanhVien.findViewById(R.id.ed_update_name_tv);
                ed_update_bith_date = viewUpdateThanhVien.findViewById(R.id.ed_update_bith_date_tv);
                ed_update_cccd_tv = viewUpdateThanhVien.findViewById(R.id.ed_update_cccd_tv);
                btn_update_tv = viewUpdateThanhVien.findViewById(R.id.btn_dialog_update_tv);
                btn_cancel_update = viewUpdateThanhVien.findViewById(R.id.btn_dialog_cancle_update_tv);
                ed_update_name_tv.setText(thanhVien.hoTen);
                ed_update_bith_date.setText(thanhVien.namSinh);
                ed_update_cccd_tv.setText(String.valueOf(thanhVien.cccd));

                layout_ed_bith.setError("");
                input_name_tv.setError("");
                input_cccd_tv.setError("");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ed_update_bith_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                ed_update_bith_date.setText(i2 + "/"+ (i1 + 1) + "/" + i);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
                btn_update_tv.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        if(ed_update_name_tv.getText().toString().isEmpty()) {
                            input_name_tv.setError("Tên thành viên đang trống!");
                            check = -1;
                        }else if(ed_update_name_tv.getText().toString().length() < 4) {
                            input_name_tv.setError("Tên thành viên phải dài hơn 3 kí tự!");
                            check = -1;
                        }else if(ed_update_name_tv.getText().toString().length()> 16) {
                            input_name_tv.setError("Tên thành viên không được dài quá 16 kí tự!");
                            check = -1;
                        }else {
                            input_name_tv.setError("");
                        }
                        if(ed_update_bith_date.getText().toString().isEmpty()) {
                            layout_ed_bith.setError("Ngày sinh đang trống!");
                            check = -1;
                        }else {
                            layout_ed_bith.setError("");
                        }

                        if(ed_update_cccd_tv.getText().toString().isEmpty()) {
                            input_cccd_tv.setError("Vui lòng nhập CCCD");
                            check = -1;
                        } else if(ed_update_cccd_tv.getText().toString().length() != 8) {
                            input_cccd_tv.setError("CCCD định dạng phải là 8 kí tự");
                            check = -1;
                        }else {
                            input_cccd_tv.setError("");
                        }
                        if(check > 0) {
                            ThanhVien thanhVien1 = new ThanhVien();
                            thanhVien1.maTV = thanhVien.maTV;
                            thanhVien1.hoTen = ed_update_name_tv.getText().toString();
                            thanhVien1.namSinh = ed_update_bith_date.getText().toString();
                            thanhVien1.cccd = Integer.parseInt(ed_update_cccd_tv.getText().toString());
                            arrThanhVien.set(position, thanhVien1);
                            thanhVienDAO.updateThanhVien(thanhVien1);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            layout_ed_bith.setError("");
                            input_name_tv.setError("");
                            input_cccd_tv.setError("");
                            Snackbar.make(viewAlert, "Sửa thông tin thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                             .show();

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
        return arrThanhVien.size();
    }
}
