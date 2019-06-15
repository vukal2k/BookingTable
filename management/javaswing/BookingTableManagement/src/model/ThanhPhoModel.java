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
public class ThanhPhoModel {
    private int idthanhpho;
    private String tenthanhpho;

    public ThanhPhoModel() {
    }

    public ThanhPhoModel(int idthanhpho, String tenthanhpho) {
        this.idthanhpho = idthanhpho;
        this.tenthanhpho = tenthanhpho;
    }

    public int getIdthanhpho() {
        return idthanhpho;
    }

    public void setIdthanhpho(int idthanhpho) {
        this.idthanhpho = idthanhpho;
    }

    public String getTenthanhpho() {
        return tenthanhpho;
    }

    public void setTenthanhpho(String tenthanhpho) {
        this.tenthanhpho = tenthanhpho;
    }
    
}
