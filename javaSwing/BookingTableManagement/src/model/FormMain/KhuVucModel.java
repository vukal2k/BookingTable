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
public class KhuVucModel {
    private int idkhuvuc;
    private int idthanhpho;
    private String tenkhuvuc;

    public int getIdkhuvuc() {
        return idkhuvuc;
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

    public KhuVucModel() {
    }
}
