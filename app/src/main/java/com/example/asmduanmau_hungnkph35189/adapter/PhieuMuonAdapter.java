package com.example.asmduanmau_hungnkph35189.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.dao.PhieuMuonDAO;
import com.example.asmduanmau_hungnkph35189.dao.SachDAO;
import com.example.asmduanmau_hungnkph35189.dao.ThanhVienDAO;
import com.example.asmduanmau_hungnkph35189.model.PhieuMuon;
import com.example.asmduanmau_hungnkph35189.model.Sach;
import com.example.asmduanmau_hungnkph35189.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonViewHolder>{
    LayoutInflater inflater;
    View viewDialogAddPM;
    FloatingActionButton floatingActionButton;
    Context context;
    View viewDeletePM, viewUpdatePM;
    ArrayList<PhieuMuon> arrPm = new ArrayList<>();
    ArrayList<Sach> arrSach = new ArrayList<>();
    ArrayList<ThanhVien> arrThanhVien = new ArrayList<>();
    PhieuMuonDAO phieuMuonDAO;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    int maSach;
    int maTV;
    View viewAlert;

    public PhieuMuonAdapter(Context context,  ArrayList<PhieuMuon> arrPM) {
        this.context = context;
        this.arrPm = arrPM;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewItem = inflater.inflate(R.layout.custom_phieu_muon, parent, false);
        PhieuMuonViewHolder phieuMuonViewHolder = new PhieuMuonViewHolder(viewItem);
        viewAlert = parent;
        return phieuMuonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        phieuMuonDAO = new PhieuMuonDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        sachDAO = new SachDAO(context);
        PhieuMuon phieuMuon = arrPm.get(position);
        if(phieuMuon != null) {
            ThanhVien thanhVien= thanhVienDAO.getIdTV(String.valueOf(phieuMuon.maTV));
            Sach sach= sachDAO.getIdSach(String.valueOf(phieuMuon.maSach));
            holder.tv_name_tv.setText(thanhVien.hoTen);
            holder.tv_name_sach.setText(sach.tenSach);
            holder.tv_tien_thue.setText(sach.giaThue + " vnđ");
            if(phieuMuon.traSach == 1) {
                holder.tv_status.setText("Đã trả sách.");
                holder.tv_status.setTextColor(Color.BLUE);
            }else {
                holder.tv_status.setText(" Chưa trả sách.");
                holder.tv_status.setTextColor(Color.RED);
            }
            holder.tv_ngay_thue.setText("Ngày thuê: " + phieuMuon.ngay);
            inflater = LayoutInflater.from(context);
        }
        viewDeletePM = inflater.inflate(R.layout.dialog_delete_phieu_muon, null);
        viewUpdatePM = inflater.inflate(R.layout.dialog_update_phieu_muon, null);

        holder.img_delete_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDeletePM.getParent() != null) {
                    ((ViewGroup)viewDeletePM.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewDeletePM);

                Button btn_delete_pm, btn_cancel_delete_pm;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_delete_pm = viewDeletePM.findViewById(R.id.btn_dialog_delete_pm);
                btn_cancel_delete_pm = viewDeletePM.findViewById(R.id.btn_dialog_cancel_delete_pm);
                btn_cancel_delete_pm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_delete_pm.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        long kq = phieuMuonDAO.deletePhieuMuon(phieuMuon.maPM + "");
                        arrPm.remove(phieuMuon);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                        Snackbar.make(viewAlert, "Xoá phiếu mượn thành công!", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(R.color.primary_color)
                                .show();
                    }
                });
            }
        });

        holder.item_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewUpdatePM.getParent() != null) {
                    ((ViewGroup)viewUpdatePM.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewUpdatePM);

                Spinner spinner_sach;
                Spinner spinner_thanh_vien;
                CheckBox chk_status;
                EditText ed_ngay_thue, ed_tien_thue;
                Button btn_update_phieu_muon, btn_cancel_update_phieu_muon;
                TextInputLayout input_date_muon;
                input_date_muon = viewUpdatePM.findViewById(R.id.input_date_pm);
                chk_status = viewUpdatePM.findViewById(R.id.chk_status_update_pm);
                spinner_sach = viewUpdatePM.findViewById(R.id.spn_sach_frag_phieu_muon_update_pm);
                spinner_thanh_vien = viewUpdatePM.findViewById(R.id.spn_thanh_vien_update_pm);
                ed_ngay_thue = viewUpdatePM.findViewById(R.id.ed_ngay_thue_frag_pm_update_pm);
                ed_tien_thue = viewUpdatePM.findViewById(R.id.ed_tien_thue_frag_pm_update_pm);
                btn_update_phieu_muon = viewUpdatePM.findViewById(R.id.btn_dialog_update_phieu_muon);
                btn_cancel_update_phieu_muon = viewUpdatePM.findViewById(R.id.btn_dialog_cancle_update_phieu_muon);
                input_date_muon.setError("");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ed_ngay_thue.setText(phieuMuon.ngay);
                arrPm = (ArrayList<PhieuMuon>) phieuMuonDAO.getAllPhieuMuon();
                arrThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAllThanhVien();
                thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, arrThanhVien);
                spinner_thanh_vien.setAdapter(thanhVienSpinnerAdapter);
                spinner_thanh_vien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maTV = arrThanhVien.get(i).maTV;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                final Sach[] sach = {new Sach()};
                arrSach = (ArrayList<Sach>) sachDAO.getAllSach();
                sachSpinnerAdapter = new SachSpinnerAdapter(context, arrSach);
                spinner_sach.setAdapter(sachSpinnerAdapter);
                spinner_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maSach = arrSach.get(i).maSach;
                        sach[0] = sachDAO.getIdSach(maSach + "");
                        ed_tien_thue.setText(sach[0].giaThue + " VND");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                ThanhVien thanhVien= thanhVienDAO.getIdTV(String.valueOf(phieuMuon.maTV));
                Sach sach1= sachDAO.getIdSach(String.valueOf(phieuMuon.maSach));
                int positionSach = 0;
                for (int i = 0; i < arrSach.size(); i++) {
                    if(sach1.maSach == (arrSach.get(i).maSach)) {
                        positionSach = i;
                        spinner_sach.setSelection(positionSach);
                    }
                }
                int positionThanhVien = 0;
                for (int i = 0; i < arrThanhVien.size(); i++) {
                    if(thanhVien.maTV == (arrThanhVien.get(i).maTV)) {
                        positionThanhVien = i;
                        spinner_thanh_vien.setSelection(positionThanhVien);
                    }
                }

                ed_ngay_thue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                if(i2 > 9) {
                                    ed_ngay_thue.setText(i + "-"+ (i1 + 1) + "-" + i2);
                                }else {
                                    ed_ngay_thue.setText(i + "-"+ (i1 + 1) + "-0" + i2);
                                }

                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
                btn_cancel_update_phieu_muon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btn_update_phieu_muon.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        int check = 1;
                        PhieuMuon phieuMuon1 = new PhieuMuon();
                        phieuMuon1.maPM = phieuMuon.maPM;
                        phieuMuon1.maTV = maTV;
                        phieuMuon1.maSach = maSach;
                        phieuMuon1.ngay = ed_ngay_thue.getText().toString();
                        phieuMuon1.tienThue = sach[0].giaThue;
                        if(chk_status.isChecked()) {
                            phieuMuon1.traSach = 1;
                        } else {
                            phieuMuon1.traSach = 0;
                        }
                        if(ed_ngay_thue.getText().toString().isEmpty()) {
                            input_date_muon.setError("Ngày thuê đang trống!");
                            check = -1;
                        }
                        if(check> 0) {
                            long kq = phieuMuonDAO.updatePhieuMuon(phieuMuon1);
                            arrPm.set(position, phieuMuon1);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Snackbar.make(viewAlert, "Sửa phiếu mượn thành công!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(R.color.primary_color)
                                    .show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrPm.size();
    }
}
