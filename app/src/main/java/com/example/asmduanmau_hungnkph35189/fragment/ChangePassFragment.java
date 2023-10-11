package com.example.asmduanmau_hungnkph35189.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.asmduanmau_hungnkph35189.R;
import com.example.asmduanmau_hungnkph35189.dao.ThuThuDAO;
import com.example.asmduanmau_hungnkph35189.model.ThuThu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePassFragment extends Fragment {
    EditText ed_old_pass, ed_password, ed_repassword;
    Button btn_change;
    ThuThuDAO thuThuDAO;
    TextView show_err;
    TextInputLayout input_old_pass, input_pass, input_repass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        ed_old_pass = view.findViewById(R.id.ed_old_password_change);
        ed_password = view.findViewById(R.id.ed_password_change);
        ed_repassword = view.findViewById(R.id.ed_repassword_change);
        btn_change = view.findViewById(R.id.btn_change_pass);
        show_err = view.findViewById(R.id.check_trung_changpass);
        input_old_pass = view.findViewById(R.id.input_old_pass);
        input_pass = view.findViewById(R.id.input_pass_changpass);
        input_repass = view.findViewById(R.id.input_repass_changpass);
        thuThuDAO = new ThuThuDAO(getActivity());

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME", "");
                if(validate() > 0) {
                    ThuThu thuThu = thuThuDAO.getIdTT(user);
                    thuThu.matKhau = ed_password.getText().toString();
                    thuThuDAO.updateThuThu(thuThu);
                    if(thuThuDAO.updateThuThu(thuThu) > 0) {
                        Snackbar.make(view, "Thay đổi mật khẩu  thành công!", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.primary_color))
                                .show();
                        ed_old_pass.setText("");
                        ed_password.setText("");
                        ed_repassword.setText("");
                    } else {
                        Snackbar.make(view, "Thay đổi mật khẩu  thất bại!", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.primary_color))
                                .show();
                    }
                }
            }
        });
    }
    public int validate() {

        int check = 1;
        if(ed_old_pass.getText().length() == 0) {
            input_old_pass.setError("Mật khẩu cũ trống!");
            check = -1;
        }else {
            input_old_pass.setError("");
        }
        if(ed_password.getText().length() == 0) {
            input_pass.setError("Mật khẩu mới trống!");;
            check = -1;
        }else if(ed_password.getText().length() < 3) {
            input_pass.setError("Mật khẩu mới phải lớn hơn 3 kí tự!");
            check = -1;
        }else if(ed_password.getText().length() > 16) {
            input_pass.setError("Mật khẩu mới phải nhỏ hơn 16 kí tự!");
            check = -1;
        }else {
            input_pass.setError("");
        }
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String passOld = preferences.getString("PASSWORD", "");
        String pass = ed_password.getText().toString();
        String rePass = ed_repassword.getText().toString();
        if(!passOld.equals(ed_old_pass.getText().toString())) {
            input_old_pass.setError("Mật khẩu cũ sai!");
            check = -1;
        }
        if(ed_repassword.getText().length() == 0) {
            input_repass.setError("Mật khẩu nhập lại trống!");
            check = -1;
        }else if(!pass.equals(rePass)) {
            input_repass.setError("Mật khẩu không trùng khớp!");
            check = -1;
        }else {
            input_repass.setError("");
        }

        return check;
    }
}
