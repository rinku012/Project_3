package in.co.rays.proj3.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import in.co.rays.proj3.dto.DropDownListDTO;;

/**
 * 
 * HTML Utility class to produce HTML contents like Dropdown List.
 * 
 * @author Rinku
 *
 */
public class HTMLUtility {

    public static String getList(String name, String selectedVal,HashMap <String, String> map) {

        StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'style=" + "width:100%" + ">");
        sb.append("---Select---");
        
        Set<String> keys = map.keySet();
        String val = null;
       
        for (String key : keys) {
        	val = map.get(key);
        	if (key.trim().equals(selectedVal)) {
        		sb.append("<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }
    

	/**
	 * 
	 * Create HTML SELECT List from List parameter
	 * 
	 * @param name
	 * @param selectedVal
	 * @param list
	 * @return
	 */
	public static String getList(String name, String selectedVal, List list) {

        Collections.sort(list);

        List<DropDownListDTO> dd = (List<DropDownListDTO>) list;

        StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'style=" + "width:100%" + ">");
        
        
        String key ;
        String val ;
        sb.append("---Select---");
        sb.append("<option selected value=''> ----Select---- </option>");
        
        for (DropDownListDTO obj : dd) {
            key = obj.getKey();
            val = obj.getValue();
           
            
            if (key.trim().equals(selectedVal)) {
                sb.append("<option selected value='" + key + "'>" + val
                        + "</option>");
            } else {
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }
    
}

