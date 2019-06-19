/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiDatBan;
import commond.ApiHelper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatBanModel;
import model.DatBanViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class DatBanBUS {
    private static final String message_failed="đơn đặt bàn thất bại";
    private static final String message_success="đơn đặt bàn thành công";
    
    public static ArrayList getAll(int idNhaHang){
        String datBanJson = ApiHelper.getData(ApiDatBan.GetAll+"?idnhahang="+idNhaHang);
        
        JSONArray jsonArray = new JSONArray(datBanJson);
        JSONObject jsonObject;
        ArrayList listDatBan = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listDatBan.add(gson.fromJson(jsonObject.toString(), DatBanViewModel.class));
        }
        return listDatBan;
    }
    
    public static ArrayList timKiem(int idNhaHang, String searchKey){
        try {
            String datBanJson = ApiHelper.getData(ApiDatBan.TimKiem+"?idnhahang="+URLEncoder.encode(idNhaHang+"", "UTF-8")+
                                                                    "&searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
            
            JSONArray jsonArray = new JSONArray(datBanJson);
            JSONObject jsonObject;
            ArrayList listDatBan = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                listDatBan.add(gson.fromJson(jsonObject.toString(), DatBanViewModel.class));
            }
            return listDatBan;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatBanBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList();
    }
    
    public static String Them(DatBanModel datBan){
        if(datBan.getIdtaikhoan()==0){
            return "Yêu cầu chọn khách hàng";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", new Gson().toJson(datBan));
        
        String response = ApiHelper.postData(ApiDatBan.Them, params);
        if(response.contains("success")){
            return "Thêm "+message_success;
        }
        else if(response.contains("duplicated")){
            return "Thời gian này đã có người đặt";
        }
        else{
            return "Thêm "+message_failed;
        }
    }
    
    public static String Sua(DatBanModel datBan){
        if(datBan.getIdtaikhoan()==0){
            return "Yêu cầu chọn khách hàng";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", new Gson().toJson(datBan));
        
        String response = ApiHelper.postData(ApiDatBan.Sua, params);
        if(response.contains("success")){
            return "Sửa "+message_success;
        }
        else if(response.contains("duplicated")){
            return "Thời gian này đã có người đặt";
        }
        else{
            return "Sửa "+message_failed;
        }
    }
    
    public static String Xoa(int idDatBan){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idDatBan+"");
        
        String response = ApiHelper.postData(ApiDatBan.Xoa, params);
        if(response.contains("success")){
            return "Xóa "+message_success;
        }
        else{
            return "Xóa "+message_failed;
        }
    }
}
