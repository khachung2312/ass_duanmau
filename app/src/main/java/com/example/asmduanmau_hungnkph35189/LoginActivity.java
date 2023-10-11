package com.example.asmduanmau_hungnkph35189;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asmduanmau_hungnkph35189.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    Button  btn_login;
    CheckBox checkBox;
    Intent intent;
    ThuThuDAO thuThuDAO;
    EditText ed_password, ed_username;
    TextView notifiaccout;
    TextInputLayout input_username, input_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        ed_password = findViewById(R.id.ed_password);
        ed_username = findViewById(R.id.ed_username);
        checkBox = findViewById(R.id.check_remember_pass);
        input_username = findViewById(R.id.input_username);
        notifiaccout = findViewById(R.id.notifi_accout);
        input_pass = findViewById(R.id.input_password);
        thuThuDAO = new ThuThuDAO(this);
        checkBox.setChecked(false);
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        ed_username.setText(preferences.getString("USERNAME", ""));
        ed_password.setText(preferences.getString("PASSWORD", ""));
        checkBox.setChecked(preferences.getBoolean("REMEMBER", false));


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    public void checkLogin () {
        String strUser = ed_username.getText().toString();
        String strPass = ed_password.getText().toString();
        int check = 1;
       if(strUser.isEmpty() && strPass.isEmpty()) {
           input_username.setError("Tên đăng nhập đang trống!");
           input_pass.setError("Mật khẩu đang trống!");
           notifiaccout.setText("");
           check = -1;
       }else {
           if(strUser.isEmpty()) {
               input_username.setError("Tên đăng nhập đang trống!");
               input_pass.setError("");
               notifiaccout.setText("");
               check = -1;
           }
           if(strPass.isEmpty()) {
               input_pass.setError("Mật khẩu đang trống!");
               input_username.setError("");
               notifiaccout.setText("");
               check = -1;
           }
       }
        if(check > 0) {
            if(thuThuDAO.checkLogin(strUser, strPass) > 0) {
                rememberUser(strUser, strPass, checkBox.isChecked());
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                notifiaccout.setText("");
                input_username.setError("");
                input_pass.setError("");
//                finish();
            } else {
                input_username.setError("");
                input_pass.setError("");
                notifiaccout.setText("Thông tin tài khoản hoặc mật khẩu không chính xác!");
                notifiaccout.setTextColor(Color.RED);
            }

        }
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!status) {
            editor.clear();
        } else {
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }
}