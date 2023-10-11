package com.example.asmduanmau_hungnkph35189.model;

public class PhieuMuon {
    public  int maPM;
    public int maTV;
    public int maSach;
    public int tienThue;
    public int traSach;
    public String ngay;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM,  int maTV, int maSach, int tienThue, int traSach, String ngay) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngay = ngay;
    }
}
