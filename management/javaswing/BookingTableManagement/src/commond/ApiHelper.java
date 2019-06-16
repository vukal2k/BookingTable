/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commond;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author vuanl
 */
public class ApiHelper {
    private ApiHelper instance = new ApiHelper();
    public ApiHelper(){
        
    }

    public ApiHelper getInstance() {
        return instance;
    }
    
    public static String getData(String address) {
        try {
            // goi yeu cau theo pp get
            URI uri = new URI(address);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // nhan du lieu
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            StringBuilder response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();
        } catch (Exception e) {
            return "Err:" + e.toString();
        }
    }
 
    public static String postData(String address, Map<String, String> params) {
        try {
            // ket noi voi server
            URI uri = new URI(address);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            // tao cac tham so se goi len server
            //Map<String, Object> params = new LinkedHashMap<String, Object>();
            //System.out.print("Input id:");
            //Scanner sn = new Scanner(System.in);
            //params.put("id", sn.nextLine());
            //System.out.print("Input name:");
            //params.put("name", sn.nextLine());
            // ghep tham so    
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            // ghi du lieu len server (submit)
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            // nhan du lieu
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return response.toString();
 
        } catch (Exception e) {
            return "Err:" + e.toString();
        }
    }
    
    public static boolean validateSqlInjection(String value){
        if(value.contains("--"))
            return false;
        else
            return true;
    }
    
    public static boolean checkImageExsists(String urlString) throws MalformedURLException, ProtocolException, IOException{
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection con =  (HttpURLConnection) new URL(urlString).openConnection();
        con.setRequestMethod("HEAD");
        return (con.getResponseCode() == HttpURLConnection.HTTP_OK && 
                (urlString.contains(".jpg") || urlString.contains(".png")));
    }
}
