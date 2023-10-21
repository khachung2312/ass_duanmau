package com.example.asmduanmau_hungnkph35189.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.adapter.ThanhVienAdapter;
import com.example.asmduanmau_hungnkph35189.dao.ThanhVienDAO;
import com.example.asmduanmau_hungnkph35189.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class ThanhVienFragment extends Fragment {
    LayoutInflater inflater;
    View viewDialogAddThanhVien;
    FloatingActionButton floatingActionButton;
    Context mContext;

    ArrayList<ThanhVien> arrThanhVien = new ArrayList<>();
    RecyclerView rcyThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter thanhVienAdapter;
    ThanhVienFragment thanhVienFragment;
    SearchView searchTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
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
        floatingActionButton = view.findViewById(R.id.float_btn_add_thanh_vien);
        viewDialogAddThanhVien = inflater.inflate(R.layout.dialog_add_thanh_vien, null);
        searchTV = view.findViewById(R.id.search_tv);
        rcyThanhVien= view.findViewById(R.id.rcv_thanh_vien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcyThanhVien.setLayoutManager(layoutManager);

        thanhVienDAO = new ThanhVienDAO(mContext);
        arrThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAllThanhVien();
        thanhVienAdapter = new ThanhVienAdapter(mContext,this,  arrThanhVien);
        rcyThanhVien.setAdapter(thanhVienAdapter);


        searchTV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ThanhVien> filteredList = (ArrayList<ThanhVien>) thanhVienDAO.searchThanhVienByName(newText);
                thanhVienAdapter.setFilter(filteredList);
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewDialogAddThanhVien.getParent() != null) {
                    ((ViewGroup)viewDialogAddThanhVien.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(viewDialogAddThanhVien);
                EditText ed_name_tv, ed_bitd_date, ed_cccd_tv;
                Button btn_add_tv, btn_cancel_add;
                TextInputLayout layout_ed_bith, layout_name_tv, layout_cccd_tv;
                layout_ed_bith = viewDialogAddThanhVien.findViewById(R.id.input_bith_date_tv);
                layout_name_tv = viewDialogAddThanhVien.findViewById(R.id.input_name_tv);
                layout_cccd_tv = viewDialogAddThanhVien.findViewById(R.id.input_cccd_tv);
                ed_name_tv = viewDialogAddThanhVien.findViewById(R.id.ed_name_tv);
                ed_bitd_date = viewDialogAddThanhVien.findViewById(R.id.ed_bith_date_tv);
                ed_cccd_tv = viewDialogAddThanhVien.findViewById(R.id.ed_cccd_tv);
                btn_add_tv = viewDialogAddThanhVien.findViewById(R.id.btn_dialog_add_tv);
                btn_cancel_add = viewDialogAddThanhVien.findViewById(R.id.btn_dialog_cancle_add_tv);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ed_bitd_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                ed_bitd_date.setText(i2 + "/"+ (i1 + 1) + "/" + i);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
                btn_cancel_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed_name_tv.setText("");
                        ed_bitd_date.setText("");
                        ed_cccd_tv.setText("");
                        alertDialog.dismiss();
                    }
                });
                btn_add_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int check = 1;
                        if(ed_name_tv.getText().toString().isEmpty()) {
                            layout_name_tv.setError("Tên thành viên đang trống!");
                            check = -1;
                        }else if(ed_name_tv.getText().toString().length() < 3) {
                            layout_name_tv.setError("Tên thành viên phải dài hơn 3 kí tự!");
                            check = -1;
                        }else if(ed_name_tv.getText().toString().length()> 16) {
                            layout_name_tv.setError("Tên thành viên không được dài quá 16 kí tự!");
                            check = -1;
                        }else {
                            layout_name_tv.setError("");
                        }
                        if(ed_bitd_date.getText().toString().isEmpty()) {
                            layout_ed_bith.setError("Ngày sinh đang trống!");
                            check = -1;
                        }else {
                            layout_ed_bith.setError("");
                        }

                        if(ed_cccd_tv.getText().toString().isEmpty()) {
                            layout_cccd_tv.setError("Vui lòng nhập CCCD");
                            check = -1;
                        } else if(ed_cccd_tv.getText().toString().length() != 8) {
                            layout_cccd_tv.setError("CCCD định dạng phải là 8 kí tự");
                            check = -1;
                        } else {
                            layout_cccd_tv.setError("");
                        }

                        if (check > 0) {
                            ThanhVien thanhVien = new ThanhVien();
                            thanhVien.hoTen = ed_name_tv.getText().toString();
                            thanhVien.namSinh = ed_bitd_date.getText().toString();
                            thanhVien.cccd = Integer.parseInt(ed_cccd_tv.getText().toString());
                          thanhVienDAO.insertThanhVien(thanhVien);
                          arrThanhVien = new ArrayList<>();
                          arrThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAllThanhVien();
                          thanhVienAdapter = new ThanhVienAdapter(mContext,thanhVienFragment,  arrThanhVien);
                          rcyThanhVien.setAdapter(thanhVienAdapter);
                          Snackbar.make(view, "Thêm thành viên thành công!", Snackbar.LENGTH_LONG)
                                  .setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.primary_color))
                                  .show();
                          alertDialog.dismiss();
                          ed_bitd_date.setText("");
                          ed_name_tv.setText("");
                          ed_cccd_tv.setText("");
                          layout_ed_bith.setError("");
                          layout_name_tv.setError("");
                          layout_cccd_tv.setError("");

                      }
                    }
                });
            }
        });
    }
}
