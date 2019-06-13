/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiHelper;
import commond.ApiThanhPho;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import model.FormMain.ThanhPhoModel;
import org.json.JSONArray;
import org.json.JSONObject;


public final class ThanhPhoBUS{
    
    public static ArrayList getAll(){
        String thanhPhoJson = ApiHelper.getData(ApiThanhPho.GetAll);
        
        JSONArray jsonArray = new JSONArray(thanhPhoJson);
        JSONObject jsonObject;
        ArrayList listThanhPho = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listThanhPho.add(gson.fromJson(jsonObject.toString(), ThanhPhoModel.class));
        }
        return listThanhPho;
    }
    
    public static ArrayList timKiem(String searchKey) throws UnsupportedEncodingException{
        String thanhPhoJson = ApiHelper.getData(ApiThanhPho.TimKiem+"?searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
        
        JSONArray jsonArray = new JSONArray(thanhPhoJson);
        JSONObject jsonObject;
        ArrayList listThanhPho = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listThanhPho.add(gson.fromJson(jsonObject.toString(), ThanhPhoModel.class));
        }
        return listThanhPho;
    }
    
    public static String Them(ThanhPhoModel thanhPho){
        if(thanhPho.getTenthanhpho().trim().equals("")||!ApiHelper.validateSqlInjection(thanhPho.getTenthanhpho())){
            return "Yêu cầu nhập tên thành phố";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(thanhPho));
        
        String response = ApiHelper.postData(ApiThanhPho.Them, params);
        if(response.contains("success")){
            return "Thêm thành phố thành công";
        }
        else{
            return "Thêm thành phố thất bại";
        }
    }
    
    public static String Sua(ThanhPhoModel thanhPho){
        if(thanhPho.getTenthanhpho().trim().equals("")||!ApiHelper.validateSqlInjection(thanhPho.getTenthanhpho())){
            return "Yêu cầu nhập tên thành phố";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(thanhPho));
        
        String response = ApiHelper.postData(ApiThanhPho.Sua, params);
        if(response.contains("success")){
            return "Sửa thành phố thành công";
        }
        else{
            return "Sửa thành phố thất bại";
        }
    }
    
    public static String Xoa(int idThanhPho){
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", idThanhPho+"");
        
        String response = ApiHelper.postData(ApiThanhPho.Xoa, params);
        if(response.contains("success")){
            return "Xóa thành phố thành công";
        }
        else{
            return "Xóa thành phố thất bại";
        }
    }
}
