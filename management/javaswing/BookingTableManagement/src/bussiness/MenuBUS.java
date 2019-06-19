/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussiness;

import com.google.gson.Gson;
import commond.ApiHelper;
import commond.ApiMenu;
import commond.FileItemToPhp;
import commond.MultipartUtility;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import model.MenuModel;
import model.MenuViewModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author vuanl
 */
public class MenuBUS {
    private static final String message_failed="menu thất bại";
    private static final String message_success="menu thành công";
    
    public static ArrayList getAll(int idNhaHang){
        String menuJson = ApiHelper.getData(ApiMenu.GetAll+"?idnhahang="+idNhaHang);
        
        JSONArray jsonArray = new JSONArray(menuJson);
        JSONObject jsonObject;
        ArrayList listMenu = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson(); 
            listMenu.add(gson.fromJson(jsonObject.toString(), MenuViewModel.class));
        }
        return listMenu;
    }
    
    public static String Them(MenuModel menu, FileItemToPhp fileHinhAnh){
        try {
            if(fileHinhAnh==null){
                return "Chọn ảnh trong máy bạn để upload";
            }
            if(!((fileHinhAnh.getFileName().contains("png")||fileHinhAnh.getFileName().contains("jpg"))
               &&fileHinhAnh.getFileName().trim().equals("")==false)){
                return "File hình ảnh phải là định dạng jpg hoặc png";
            }
            //upload file
            MultipartUtility multipart = new MultipartUtility();
            String resultUpload =multipart.addFilePart(new File(fileHinhAnh.getFilePath()));
            
            if(resultUpload.trim().contains("Success")){
                menu.setHinhanh("img/"+fileHinhAnh.getFileName());
                @SuppressWarnings("Convert2Diamond")
                    Map<String, String> params = new LinkedHashMap<>();
                params.put("viewModel", new Gson().toJson(menu));


                String response = ApiHelper.postData(ApiMenu.Them, params);
                if(response.contains("success")){
                    return "Thêm "+message_success;
                }
                else{
                    return "Thêm "+message_failed;
                }
            }
            else{
                return "Không thể upload được file ảnh";
            }
        } catch (IOException ex) {
            return "Không thể upload được file ảnh";
        }
    }
    
    public static String Xoa(int idMenu){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("viewModel", idMenu+"");
        
        String response = ApiHelper.postData(ApiMenu.Xoa, params);
        if(response.contains("success")){
            return "Xóa "+message_success;
        }
        else{
            return "Xóa "+message_failed;
        }
    }   
}
