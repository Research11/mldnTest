package org.ibas.credit.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.ibas.credit.customer.entity.Customer;

public class ObjectUtil {
		
	    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
	    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
	        if (map == null || obj == null) {
	            return;
	        }
	        try {
	            BeanUtils.populate(obj, map);
	        } catch (Exception e) {
	            System.out.println("transMap2Bean2 Error " + e);
	        }
	    }

	    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	    public static void transMap2Bean(Map<String, Object> map, Object obj) {

	        try {
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

	            for (PropertyDescriptor property : propertyDescriptors) {
	                String key = property.getName();

	                if (map.containsKey(key)) {
	                    Object value = map.get(key);
	                    // 得到property对应的setter方法
	                    Method setter = property.getWriteMethod();
	                    setter.invoke(obj, value);
	                }

	            }

	        } catch (Exception e) {
	            System.out.println("transMap2Bean Error " + e);
	        }

	        return;

	    }

	    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	    public static Map<String, Object> transBean2Map(Object obj) {

	        if(obj == null){
	            return null;
	        }        
	        Map<String, Object> map = new HashMap<String, Object>();
	        try {
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	            for (PropertyDescriptor property : propertyDescriptors) {
	                String key = property.getName();

	                // 过滤class属性
	                if (!key.equals("class")) {
	                    // 得到property对应的getter方法
	                    Method getter = property.getReadMethod();
	                    if(getter != null && getter.getName().startsWith("get")){
	                    	Object value = getter.invoke(obj);
	                    	if(value instanceof Date){
	                    		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	                    		map.put(key, df.format(value));
                    		}else{
                    			map.put(key, value);
                    		}
	                    }
	                }

	            }
	        } catch (Exception e) {
	            System.out.println("transBean2Map Error " + e);
	        }

	        return map;

	    }
	    
	    /**
	     * 替换map  key  
	     * @param map
	     * @param replaceStr
	     * @return
	     */
	    @SuppressWarnings("rawtypes")
		public static Map<String,Object> contactKey(Map<String,Object> map,String contactStr){
	    	Map<String,Object>  result = new HashMap<String, Object>();
	    	Iterator it = map.keySet().iterator();
	        while(it.hasNext()){
	        	String key = (String) it.next();
	        	String newkey = contactStr.concat(".".concat(key));
	        	result.put(newkey, map.get(key));
	        }
	        
	    	return result;
	    }
	    
	    public static void main(String[] args) {

	        Customer person = new Customer();
//	        Map<String, Object> mp = new HashMap<String, Object>();
//	        mp.put("customerName", "Mike");
//	        mp.put("address", "北京市");
//	        mp.put("cellPhone", "12313123123131");
//
//	        // 将map转换为bean
//	        transMap2Bean2(mp, person);
//
//	        System.out.println("--- transMap2Bean Map Info: ");
//	        for (Map.Entry<String, Object> entry : mp.entrySet()) {
//	            System.out.println(entry.getKey() + ": " + entry.getValue());
//	        }
//
//	        System.out.println("--- Bean Info: ");
//	        System.out.println("customerName: " + person.getCustomerName());
//	        System.out.println("address: " + person.getAddress());
//	        System.out.println("cellPhone: " + person.getCellPhone());

	        // 将javaBean 转换为map
	        person.setCustomerName("张三");
	        person.setCellPhone("123131312");
	        person.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	        Map<String, Object> map = contactKey(transBean2Map(person),Customer.class.getSimpleName().toLowerCase());

	        Iterator it = map.keySet().iterator();
//	        while(it.hasNext()){
//	        	String key = (String) it.next();
//	        	System.out.println("key:"+key+"value:"+map.get(key));
//	        }
	        String s = JSONArray.fromObject(map).toString();
	        System.out.println(transBean2Map(person));
	        System.out.println(s.substring(1, s.length()-1));

	    }

}
