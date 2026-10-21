package com.example.baithuchanh1;

import java.util.ArrayList;

public class SinhVien {
    private String maSV;
    private String hoTen;
    private int namSinh;
    private double diemTB;

    public SinhVien() {
    }

    public SinhVien(String maSV, String hoTen, int namSinh, double diemTB) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.diemTB = diemTB;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public double getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(double diemTB) {
        this.diemTB = diemTB;
    }

    public void nhapThongTin(String maSV, String hoTen, int namSinh, double diemTB) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.diemTB = diemTB;
    }

    public void suaThongTin(String hoTenMoi, int namSinhMoi, double diemTBMoi) {
        this.hoTen = hoTenMoi;
        this.namSinh = namSinhMoi;
        this.diemTB = diemTBMoi;
    }

    public static boolean xoaSinhVien(ArrayList<SinhVien> list, int viTri) {
        if (viTri >= 0 && viTri < list.size()) {
            list.remove(viTri);
            return true;
        }
        return false;
    }

    public static SinhVien timDiemCaoNhat(ArrayList<SinhVien> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        SinhVien svMax = list.get(0);
        for (SinhVien sv : list) {
            if (sv.getDiemTB() > svMax.getDiemTB()) {
                svMax = sv;
            }
        }
        return svMax;
    }

    @Override
    public String toString() {
        return maSV + " - " + hoTen + " (" + namSinh + ") | ĐTB: " + diemTB;
    }
}
