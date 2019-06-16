/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiHelper;
import commond.ApiKhuVuc;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KhuVucModel;
import model.KhuVucViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class KhuVucBUS {
    private static String message_failed="khu vực thất bại";
    private static String message_success="khu vực thành công";
    
    public static ArrayList getAll(){
        String khuVucJson = ApiHelper.getData(ApiKhuVuc.GetAll);
        
        JSONArray jsonArray = new JSONArray(khuVucJson);
        JSONObject jsonObject;
        ArrayList listKhucVuc = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listKhucVuc.add(gson.fromJson(jsonObject.toString(), KhuVucViewModel.class));
        }
        return listKhucVuc;
    }
    
    public static ArrayList timKiem(String searchKey){
        try {
            String khuVucJson = ApiHelper.getData(ApiKhuVuc.TimKiem+"?searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
            
            JSONArray jsonArray = new JSONArray(khuVucJson);
            JSONObject jsonObject;
            ArrayList listKhucVuc = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                listKhucVuc.add(gson.fromJson(jsonObject.toString(), KhuVucViewModel.class));
            }
            return listKhucVuc;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(KhuVucBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList();
    }
    
    public static String Them(KhuVucModel khuVuc){
        if(khuVuc.getTenkhuvuc().trim().equals("")||!ApiHelper.validateSqlInjection(khuVuc.getTenkhuvuc())){
            return "Yêu cầu nhập tên khu vực";
        }
        if(khuVuc.getIdthanhpho()==0){
            return "Yêu cầu chọn thành phố";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", new Gson().toJson(khuVuc));
        
        String response = ApiHelper.postData(ApiKhuVuc.Them, params);
        if(response.contains("success")){
            return "Thêm "+message_success;
        }
        else{
            return "Thêm "+message_failed;
        }
    }
    
    public static String Sua(KhuVucModel khuVuc){
        if(khuVuc.getTenkhuvuc().trim().equals("")||!ApiHelper.validateSqlInjection(khuVuc.getTenkhuvuc())){
            return "Yêu cầu nhập tên khu vực";
        }
        if(khuVuc.getIdthanhpho()==0){
            return "Yêu cầu chọn thành phố";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", new Gson().toJson(khuVuc));
        
        String response = ApiHelper.postData(ApiKhuVuc.Sua, params);
        if(response.contains("success")){
            return "Sửa "+message_success;
        }
        else{
            return "Sửa "+message_failed;
        }
    }
    
    public static String Xoa(int idKhuVuc){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idKhuVuc+"");
        
        String response = ApiHelper.postData(ApiKhuVuc.Xoa, params);
        if(response.contains("success")){
            return "Xóa "+message_success;
        }
        else{
            return "Xóa "+message_failed;
        }
    }
}
