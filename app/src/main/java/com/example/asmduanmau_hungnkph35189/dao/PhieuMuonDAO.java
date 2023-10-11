package com.example.asmduanmau_hungnkph35189.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmduanmau_hungnkph35189.database.DBHelper;
import com.example.asmduanmau_hungnkph35189.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public static  final  String TABLE_NAME_PHIEU_MUON = DBHelper.TABLE_NAME_PHIEU_MUON;
    public PhieuMuonDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        this.context = context;
    }


    public long insertPhieuMuon (PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTV", phieuMuon.maTV);
        values.put("maSach", phieuMuon.maSach);
        values.put("tienThue", phieuMuon.tienThue);
        values.put("traSach", phieuMuon.traSach);
        values.put("ngay", String.valueOf(phieuMuon.ngay));

        return sqLiteDatabase.insert(TABLE_NAME_PHIEU_MUON, null, values);
    }

    public long updatePhieuMuon (PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTV", phieuMuon.maTV);
        values.put("maSach", phieuMuon.maSach);
        values.put("tienThue", phieuMuon.tienThue);
        values.put("traSach", phieuMuon.traSach);
        values.put("ngay", String.valueOf(phieuMuon.ngay));

        return sqLiteDatabase.update(TABLE_NAME_PHIEU_MUON, values, "maPM=?", new String[]{String.valueOf(phieuMuon.maPM)});
    }

    public int deletePhieuMuon (String id) {
        return  sqLiteDatabase.delete(TABLE_NAME_PHIEU_MUON, "maPM=?", new String[]{id});
    }

    public List<PhieuMuon> getAllPhieuMuon() {
        String sql = "SELECT * FROM " + TABLE_NAME_PHIEU_MUON;
        return getData(sql);
    }


    public PhieuMuon getIdPhieuMuon(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME_PHIEU_MUON + " WHERE maPM=?";
        List<PhieuMuon> listPhieuMuon = getData(sql, id);
        return listPhieuMuon.get(0);
    }


    private  List<PhieuMuon> getData(String sql, String...selectionArgs) {
        List<PhieuMuon> listPhieuMuon = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.maPM = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM")));
            phieuMuon.maTV = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV")));
            phieuMuon.maSach = Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach")));
            phieuMuon.tienThue = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue")));
            phieuMuon.traSach = Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach")));
            phieuMuon.ngay = cursor.getString(cursor.getColumnIndex("ngay"));
            listPhieuMuon.add(phieuMuon);
        }
        return listPhieuMuon;
    }

}
