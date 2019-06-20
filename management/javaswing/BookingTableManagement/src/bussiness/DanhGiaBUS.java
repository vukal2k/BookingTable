/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiDanhGia;
import commond.ApiHelper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DanhGiaViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class DanhGiaBUS {
    public static ArrayList getAll(int idnhahang){
        String danhGia = ApiHelper.getData(ApiDanhGia.GetAll+"?idnhahang="+idnhahang);
        
        JSONArray jsonArray = new JSONArray(danhGia);
        JSONObject jsonObject;
        ArrayList lítDanhGia = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            lítDanhGia.add(gson.fromJson(jsonObject.toString(), DanhGiaViewModel.class));
        }
        return lítDanhGia;
    }
    
    public static ArrayList timKiem(int idNhaHang, String searchKey){
        try {
            String danhGiaJson = ApiHelper.getData(ApiDanhGia.TimKiem+"?idnhahang="+idNhaHang+"&searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
            
            JSONArray jsonArray = new JSONArray(danhGiaJson);
            JSONObject jsonObject;
            ArrayList listDanhGia = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                listDanhGia.add(gson.fromJson(jsonObject.toString(), DanhGiaViewModel.class));
            }
            return listDanhGia;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DanhGiaBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }
    
    public static String Xoa(int idDanhGia){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idDanhGia+"");
        
        String response = ApiHelper.postData(ApiDanhGia.Xoa, params);
        if(response.contains("success")){
            return "Xóa đánh giá thành công";
        }
        else{
            return "Xóa đánh giá thất bại";
        }
    }
}
