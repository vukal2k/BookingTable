/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.FormMain;

/**
 *
 * @author vuanl
 */
public class KhuVucViewModel {
    private int idkhuvuc;
    private int idthanhpho;
    private String tenkhuvuc;
    private String tenthanhpho;

    public int getIdkhuvuc() {
        return idkhuvuc;
    }

    public String getTenthanhpho() {
        return tenthanhpho;
    }

    public void setTenthanhpho(String tenthanhpho) {
        this.tenthanhpho = tenthanhpho;
    }

    public void setIdkhuvuc(int idkhuvuc) {
        this.idkhuvuc = idkhuvuc;
    }

    public int getIdthanhpho() {
        return idthanhpho;
    }

    public void setIdthanhpho(int idthanhpho) {
        this.idthanhpho = idthanhpho;
    }

    public String getTenkhuvuc() {
        return tenkhuvuc;
    }

    public void setTenkhuvuc(String tenkhuvuc) {
        this.tenkhuvuc = tenkhuvuc;
    }

    public KhuVucViewModel() {
    }
}
