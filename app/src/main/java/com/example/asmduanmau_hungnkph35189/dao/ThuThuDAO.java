package com.example.asmduanmau_hungnkph35189.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmduanmau_hungnkph35189.database.DBHelper;
import com.example.asmduanmau_hungnkph35189.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public ThuThuDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        this.context = context;
    }

    public static final String TABLE_NAME_Thu_Thu  = DBHelper.TABLE_NAME_THU_THU;

    public long insertThuThu (ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.maTT);
        values.put("hoTen", thuThu.hoTen);
        values.put("matKhau", thuThu.matKhau);
        return sqLiteDatabase.insert(TABLE_NAME_Thu_Thu, null, values);
    };

    public long updateThuThu (ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.maTT);
        values.put("hoTen", thuThu.hoTen);
        values.put("matKhau", thuThu.matKhau);
        return sqLiteDatabase.update(TABLE_NAME_Thu_Thu, values, "maTT=?", new String[]{String.valueOf(thuThu.maTT)});
    };

    public int deleteThuThu (String id) {
        return sqLiteDatabase.delete(TABLE_NAME_Thu_Thu, "maTT=?", new String[]{id});
    }

    public List<ThuThu> getAllThuThu () {
        String sql = "SELECT * FROM " + TABLE_NAME_Thu_Thu;
        return getData(sql);
    }

    public ThuThu getIdTT (String id) {
        String sql = "SELECT * FROM " + TABLE_NAME_Thu_Thu + " WHERE maTT=?";
        List<ThuThu> listTT = getData(sql, id);
        return listTT.get(0);
    }

    public List<ThuThu> getData (String sql, String...selectionArgs) {
        List<ThuThu> listTT = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThuThu thuThu = new ThuThu();
            thuThu.maTT = cursor.getString(cursor.getColumnIndex("maTT"));
            thuThu.hoTen = cursor.getString(cursor.getColumnIndex("hoTen"));
            thuThu.matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));
            listTT.add(thuThu);
        }
        return listTT;
    }

    public int checkLogin(String id, String pass) {
        String sql = "SELECT * FROM " + TABLE_NAME_Thu_Thu + " WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, id, pass);
        if(list.size() == 0) {
            return  -1;
        }
        return 1;
    }

}
