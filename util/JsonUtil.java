package org.ibas.credit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.ibas.credit.Constants;
import org.ibas.credit.customer.entity.Customer;
import org.ibas.credit.customer.entity.CustomerPersonForm;

/**
 * @author Vander Wang
 *
 */
public class JsonUtil {

    //将JsonString转换成List
    public List<? extends Object> changeJsonStringToList(String JsonString, Class<? extends Object> clazz) throws InstantiationException, IllegalAccessException {
        if (JsonString != null && !"".equals(JsonString)) {
            //如果时间字段为空则截去
            for (String date_data : Constants.DATE_DATA) {
                JsonString = JsonString.replaceAll("\"" + date_data + "\":\"\",", "");
                JsonString = JsonString.replaceAll(",\"" + date_data + "\":\"\"}", "}");
                JsonString = JsonString.replaceAll("\"" + date_data + "\":\"\"", "");
            }
            List<Object> olist = new ArrayList<Object>();
            JSONArray array = JSONArray.fromObject(JsonString);
            Object[] obj = new Object[array.size()];
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String[] dateFormats = new String[] { "yyyy-MM-dd" };
                JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
                obj[i] = JSONObject.toBean(jsonObject, clazz);
                Object b = clazz.newInstance();
                b = obj[i];
                olist.add(b);
            }
            return olist;
        } else {
            return null;
        }

    }
    
    //将List转换成JsonString
    public String changeListToJsonStr(List<? extends Object> listObjs) {
        String resultStr = "[]";
        if(listObjs != null && listObjs.size() > 0){
        	StringBuffer buffStr = new StringBuffer("[");
        	for (int i = 0; i < listObjs.size(); i++) {
//        		buffStr.append("{");
        		Object obj = listObjs.get(i);
        		String objStr = JSONObject.fromObject(obj).toString();
        		buffStr.append(objStr);
        		if(i == listObjs.size() -1){
        			buffStr.append("]");
        		}else{
        			buffStr.append(",");
        		}
        	}
        	
        	resultStr = buffStr.toString();
        }

        return resultStr;
    }

    //将List转换成JsonString
    public String changeListToJsonString(List<? extends Object> list) {

        if (list.size() >= 0) {
            return JSONArray.fromObject(list).toString();
        } else {
            return "";
        }
    }
    
    /**
     * 将简单jsonString 转化为map格式
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String,Object> convertJsonStringToMap(String jsonStr){
    	Map<String, Object> resultMap = null;
    	if (StringUtils.isNotBlank(jsonStr)) {
    		JSONObject jsonObject = JSONObject.fromObject(jsonStr);  
    		
    		Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);  
    		
    		for(Entry<String,Object> entry : mapJson.entrySet()){  
    			Object entryValue = entry.getValue();  
    			JSONObject objValue = JSONObject.fromObject(entryValue);  
    			resultMap = JSONObject.fromObject(objValue);  
    		} 	
		}
        
    	return resultMap;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
//    	String i ="{'data':{'policyHolderName':'张素芳'}}";
//
//    	System.out.println(convertJsonStringToMap(i));
    	Customer c = new Customer();
    	c.setDocumentCode("1231");
    	c.setCustomerName("2131");
    	CustomerPersonForm cpf = new CustomerPersonForm();
    	cpf.setCustomer(c);
    	System.out.println(JSONArray.fromObject(c).toString());
    }

}
