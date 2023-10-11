package com.example.asmduanmau_hungnkph35189.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.adapter.PhieuMuonAdapter;
import com.example.asmduanmau_hungnkph35189.adapter.SachSpinnerAdapter;
import com.example.asmduanmau_hungnkph35189.adapter.ThanhVienSpinnerAdapter;
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

public class PhieuMuonFragment extends Fragment {
    LayoutInflater inflater;
    FloatingActionButton floatingActionButton;
    Context mContext;
    View viewDialogAddPhieuMuon;
    ArrayList<PhieuMuon> arrPm = new ArrayList<>();
    ArrayList<Sach> arrSach = new ArrayList<>();
    ArrayList<ThanhVien> arrThanhVien = new ArrayList<>();
    RecyclerView rcyPm;
    PhieuMuonDAO phieuMuonDAO;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    Spinner spinner_sach;
    Spinner spinner_thanh_vien;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    CheckBox chk_status;
    int maSach;
    int maTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
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
        floatingActionButton = view.findViewById(R.id.float_btn_add_phieu_muon);
        viewDialogAddPhieuMuon = inflater.inflate(R.layout.dialog_add_phieu_muon, null);

        rcyPm= view.findViewById(R.id.rcv_phieu_muon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcyPm.setLayoutManager(layoutManager);

        phieuMuonDAO = new PhieuMuonDAO(mContext);
        sachDAO = new SachDAO(mContext);
        thanhVienDAO = new ThanhVienDAO(mContext);

        arrPm = (ArrayList<PhieuMuon>) phieuMuonDAO.getAllPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(mContext, arrPm);
        rcyPm.setAdapter(phieuMuonAdapter);


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewDialogAddPhieuMuon.getParent() != null) {
                        ((ViewGroup)viewDialogAddPhieuMuon.getParent()).removeAllViews();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(viewDialogAddPhieuMuon);
                    EditText ed_ngay_thue, ed_tien_thue;
                    Button btn_add_phieu_muon, btn_cancel_add_phieu_muon;
                    TextInputLayout inputNgayThuePM = viewDialogAddPhieuMuon.findViewById(R.id.input_ngay_thue_pm);
                    chk_status = viewDialogAddPhieuMuon.findViewById(R.id.chk_status);
                    chk_status.setChecked(false);
                    spinner_sach = viewDialogAddPhieuMuon.findViewById(R.id.spn_sach_frag_phieu_muon);
                    spinner_thanh_vien = viewDialogAddPhieuMuon.findViewById(R.id.spn_thanh_vien);
                    ed_ngay_thue = viewDialogAddPhieuMuon.findViewById(R.id.ed_ngay_thue_frag_pm);
                    ed_tien_thue = viewDialogAddPhieuMuon.findViewById(R.id.ed_tien_thue_frag_pm);
                    btn_add_phieu_muon = viewDialogAddPhieuMuon.findViewById(R.id.btn_dialog_add_phieu_muon);
                    btn_cancel_add_phieu_muon = viewDialogAddPhieuMuon.findViewById(R.id.btn_dialog_cancle_add_phieu_muon);
                    inputNgayThuePM.setError("");

                    arrThanhVien= (ArrayList<ThanhVien>) thanhVienDAO.getAllThanhVien();
                    arrSach = (ArrayList<Sach>) sachDAO.getAllSach();
                    int check = 1;
                    if(arrSach.size() == 0 && arrThanhVien.size() == 0) {
                        check = -1;
                        Snackbar snackbar = Snackbar
                                .make(view, "Chưa có sách và thành viên!", Snackbar.LENGTH_LONG)
                                .setAction("Đóng", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();
                    }else if(arrSach.size() == 0) {
                        check = -1;
                        Snackbar snackbar = Snackbar
                                .make(view, "Chưa có sách trong thư viện!", Snackbar.LENGTH_LONG)
                                .setAction("Đóng", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();
                    }else if(arrThanhVien.size() == 0) {
                        check = -1;
                        Snackbar snackbar = Snackbar
                                .make(view, "Chưa có thành viên đăng kí!", Snackbar.LENGTH_LONG)
                                .setAction("Đóng", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();
                    }

                    if(check> 0) {
                        final AlertDialog[] alertDialog = {builder.create()};
                        alertDialog[0].show();

                        arrThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAllThanhVien();
                        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(mContext, arrThanhVien);
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
                        sachSpinnerAdapter = new SachSpinnerAdapter(mContext, arrSach);
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
                        ed_ngay_thue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Calendar calendar = Calendar.getInstance();
                                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                        ed_ngay_thue.setText(i + "-"+ (i1 + 1) + "-" + i2);
                                    }
                                }, calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH));
                                datePickerDialog.show();
                            }
                        });
                        btn_cancel_add_phieu_muon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog[0].dismiss();
                            }
                        });
                        btn_add_phieu_muon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int check = 1;
                                PhieuMuon phieuMuon = new PhieuMuon();
                                phieuMuon.maTV = maTV;
                                phieuMuon.maSach = maSach;
                                phieuMuon.ngay = ed_ngay_thue.getText().toString();
                                phieuMuon.tienThue = sach[0].giaThue;
                                if(chk_status.isChecked()) {
                                    phieuMuon.traSach = 1;
                                } else {
                                    phieuMuon.traSach = 0;
                                }
                                if(ed_ngay_thue.getText().toString().isEmpty()) {
                                    inputNgayThuePM.setError("Ngày thuê đang trống!");
                                    check = -1;
                                }
                                if(check > 0) {
                                    phieuMuonDAO.insertPhieuMuon(phieuMuon);
                                    arrPm = new ArrayList<>();
                                    arrPm = (ArrayList<PhieuMuon>) phieuMuonDAO.getAllPhieuMuon();
                                    phieuMuonAdapter = new PhieuMuonAdapter(mContext, arrPm);
                                    rcyPm.setAdapter(phieuMuonAdapter);
                                    alertDialog[0].dismiss();
                                    Snackbar.make(view, "Thêm phiếu mượn thành công!", Snackbar.LENGTH_LONG)
                                            .setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.primary_color))
                                            .show();
                                    inputNgayThuePM.setError("");
                                    ed_ngay_thue.setText("");
                                }
                            }
                        });
                    }
                }

            });
    }
}
