/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commond;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vuanl
 */
public class ConvertHelper {
    public static String DateToVNString(String input) throws ParseException{
        DateFormat decode = new SimpleDateFormat("dd/MM/yyyy"); //date sẽ nhận đầu vào string có ddinhjd ạng ntn để tạo đối tượng Date
        DateFormat encode = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTemp=encode.parse(input);
        return decode.format(dateTemp);
    }
}
