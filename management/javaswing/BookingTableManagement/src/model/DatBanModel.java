/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author vuanl
 */
public class DatBanModel {
    private int iddatban;
    private int idtaikhoan;
    private int idnhahang;
    private String ngayden;
    private String gioden;
    private int soluongnguoilon;
    private int soluongtreem;
    private String ghichu;
    private String tongtien;

    public DatBanModel() {
    }

    public int getIddatban() {
        return iddatban;
    }

    public void setIddatban(int iddatban) {
        this.iddatban = iddatban;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getNgayden() {
        return ngayden;
    }

    public void setNgayden(String ngayden) {
        this.ngayden = ngayden;
    }

    public String getGioden() {
        return gioden;
    }

    public void setGioden(String gioden) {
        this.gioden = gioden;
    }

    public int getSoluongnguoilon() {
        return soluongnguoilon;
    }

    public void setSoluongnguoilon(int soluongnguoilon) {
        this.soluongnguoilon = soluongnguoilon;
    }

    public int getSoluongtreem() {
        return soluongtreem;
    }

    public void setSoluongtreem(int soluongtreem) {
        this.soluongtreem = soluongtreem;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }
    
    
}
