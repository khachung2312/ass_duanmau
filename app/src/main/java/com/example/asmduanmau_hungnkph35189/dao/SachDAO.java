package com.example.asmduanmau_hungnkph35189.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.asmduanmau_hungnkph35189.database.DBHelper;
import com.example.asmduanmau_hungnkph35189.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public static  final  String TABLE_NAME_SACH = DBHelper.TABLE_NAME_SACH;
    public SachDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        this.context = context;
    }

    public long insertSach (Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.tenSach);
        values.put("giaThue", sach.giaThue);
        values.put("maLoai", sach.maLoai);
        values.put("namXuatBan", sach.namXuatBan);

        return sqLiteDatabase.insert(TABLE_NAME_SACH, null, values);
    }

    public int updateSach (Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.tenSach);
        values.put("giaThue", sach.giaThue);
        values.put("maLoai", sach.maLoai);
        values.put("namXuatBan", sach.namXuatBan);

        return sqLiteDatabase.update(TABLE_NAME_SACH, values, "maSach=?", new String[]{String.valueOf(sach.maSach)});
    }

    public int deleteSach (String id) {
        return sqLiteDatabase.delete(TABLE_NAME_SACH, "maSach=?", new String[]{id});
    }

    public List<Sach> getAllSach() {
        String sql = "SELECT * FROM " + TABLE_NAME_SACH;
        return getData(sql);
    }

    public Sach getIdSach(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME_SACH + " WHERE maSach=?";
        List<Sach> listSach = getData(sql, id);
        return listSach.get(0);
    }


    private List<Sach> getData(String sql, String...selectionArgs) {
        List<Sach> listSach = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.maSach = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach")));
            sach.tenSach = cursor.getString(cursor.getColumnIndex("tenSach"));
            sach.giaThue = Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue")));
            sach.maLoai = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai")));
            sach.namXuatBan = Integer.parseInt(cursor.getString(cursor.getColumnIndex("namXuatBan")));
            listSach.add(sach);
        }
        return listSach;
    }

}
