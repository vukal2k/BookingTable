/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiHelper;
import commond.ApiNhaHang;
import commond.FileItemToPhp;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NhaHangModel;
import model.NhaHangViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class NhaHangBUS {
    private static final String message_failed="nhà hàng thất bại";
    private static final String message_success="nhà hàng thành công";
    
    public static ArrayList getAll(){
        String nhahangJson = ApiHelper.getData(ApiNhaHang.GetAll);
        
        JSONArray jsonArray = new JSONArray(nhahangJson);
        JSONObject jsonObject;
        ArrayList listNhaHang = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listNhaHang.add(gson.fromJson(jsonObject.toString(), NhaHangViewModel.class));
        }
        return listNhaHang;
    }
    
    public static ArrayList timKiem(String searchKey){
        try {
            String nhahangJson = ApiHelper.getData(ApiNhaHang.TimKiem+"?searchkey="+URLEncoder.encode(searchKey, "UTF-8"));
            
            JSONArray jsonArray = new JSONArray(nhahangJson);
            JSONObject jsonObject;
            ArrayList listNhaHang = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                listNhaHang.add(gson.fromJson(jsonObject.toString(), NhaHangViewModel.class));
            }
            return listNhaHang;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NhaHangBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList();
    }
    
    public static String Them(NhaHangModel nhaHang, FileItemToPhp fileHinhAnh){
        if(nhaHang.getTennhahang().trim().equals("")||!ApiHelper.validateSqlInjection(nhaHang.getTennhahang())){
            return "Yêu cầu nhập tên nhà hàng";
        }
        if(nhaHang.getIdnhahang()==0){
            return "Yêu cầu chọn khu vực";
        }
        if(nhaHang.getIdtaikhoan()==0){
            return "Yêu cầu chọn chủ sở hữu";
        }
        if(!(fileHinhAnh.getFileName().contains("jpg")&&fileHinhAnh.getFileName().trim().equals("")==false)){
            return "File hình ảnh phải là định dạng jpg";
        }
        
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(nhaHang));
        params.put("file", new Gson().toJson(fileHinhAnh));
        
        String response = ApiHelper.postData(ApiNhaHang.Them, params);
        if(response.contains("success")){
            return "Thêm "+message_success;
        }
        else{
            return "Thêm "+message_failed;
        }
    }
    
    public static String Sua(NhaHangModel nhaHang, FileItemToPhp fileHinhAnh){
        if(nhaHang.getTennhahang().trim().equals("")||!ApiHelper.validateSqlInjection(nhaHang.getTennhahang())){
            return "Yêu cầu nhập tên nhà hàng";
        }
        if(nhaHang.getIdnhahang()==0){
            return "Yêu cầu chọn khu vực";
        }
        if(nhaHang.getIdtaikhoan()==0){
            return "Yêu cầu chọn chủ sở hữu";
        }
        if(!(fileHinhAnh.getFileName().contains("jpg")&&fileHinhAnh.getFileName().trim().equals("")==false)){
            return "File hình ảnh phải là định dạng jpg";
        }
        
        @SuppressWarnings("Convert2Diamond")
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("viewModel", new Gson().toJson(nhaHang));
        params.put("file", new Gson().toJson(fileHinhAnh));
        
        String response = ApiHelper.postData(ApiNhaHang.Sua, params);
        if(response.contains("success")){
            return "Sửa "+message_success;
        }
        else{
            return "Sửa "+message_failed;
        }
    }
    
    public static String Xoa(int idNhaHang){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idNhaHang+"");
        
        String response = ApiHelper.postData(ApiNhaHang.Xoa, params);
        if(response.contains("success")){
            return "Xóa "+message_success;
        }
        else{
            return "Xóa "+message_failed;
        }
    }   
}
