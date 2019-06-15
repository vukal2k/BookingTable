/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiHelper;
import commond.ApiTaiKhoan;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FormMain.TaiKhoanModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class TaiKhoanBUS {
    private static String message_failed="tài khoản thất bại";
    private static String message_success="tài khoản thành công";
    
    public static ArrayList getAll(){
        String taiKhoanJson = ApiHelper.getData(ApiTaiKhoan.GetAll);
        
        JSONArray jsonArray = new JSONArray(taiKhoanJson);
        JSONObject jsonObject;
        ArrayList listTaiKhoan = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listTaiKhoan.add(gson.fromJson(jsonObject.toString(), TaiKhoanModel.class));
        }
        return listTaiKhoan;
    }
    
    public static ArrayList timKiem(String searchKey){
        try {
            String taiKhoanJson = ApiHelper.getData(ApiTaiKhoan.TimKiem+"?searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
            
            JSONArray jsonArray = new JSONArray(taiKhoanJson);
            JSONObject jsonObject;
            ArrayList listTaiKhoan = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                listTaiKhoan.add(gson.fromJson(jsonObject.toString(), TaiKhoanModel.class));
            }
            return listTaiKhoan;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TaiKhoanBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }
    
    public static String Them(TaiKhoanModel taiKhoan){
        if(taiKhoan.getUsername().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getUsername())){
            return "Yêu cầu nhập username";
        }
        if(taiKhoan.getPassword().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getPassword())){
            return "Yêu cầu nhập password";
        }
        if(taiKhoan.getTenkhachhang().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getTenkhachhang())){
            return "Yêu cầu nhập tên khách hàng";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(taiKhoan));
        
        String response = ApiHelper.postData(ApiTaiKhoan.Them, params);
        if(response.contains("success")){
            return "Thêm "+message_success;
        }
        else{
            return "Thêm "+message_failed;
        }
    }
    
    public static String Sua(TaiKhoanModel taiKhoan){
        if(taiKhoan.getUsername().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getUsername())){
            return "Yêu cầu nhập username";
        }
        if(taiKhoan.getPassword().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getPassword())){
            return "Yêu cầu nhập password";
        }
        if(taiKhoan.getTenkhachhang().trim().equals("")||!ApiHelper.validateSqlInjection(taiKhoan.getTenkhachhang())){
            return "Yêu cầu nhập tên khách hàng";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(taiKhoan));
        
        String response = ApiHelper.postData(ApiTaiKhoan.Sua, params);
        if(response.contains("success")){
            return "Sửa "+message_success;
        }
        else{
            return "Sửa "+message_failed;
        }
    }
    
    public static String Xoa(int idTaiKhoan){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idTaiKhoan+"");
        
        String response = ApiHelper.postData(ApiTaiKhoan.Xoa, params);
        if(response.contains("success")){
            return "Xóa "+message_success;
        }
        else{
            return "Xóa "+message_failed;
        }
    }
}
