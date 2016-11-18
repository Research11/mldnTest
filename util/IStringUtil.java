/**
 * 
 */
package org.ibas.credit.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 字符串处理类
 * 
 * @author linxuebing
 * @version v1.0 2014年12月24日
 */
public class IStringUtil {
    /**
     * 将obj成String
     * 
     * @author linxuebing
     * @param obj
     * @return obj == null则返回 null
     */
    public static String stringOf(Object obj) {

        return (obj == null) ? null : obj.toString();
    }

    /**
     * 按照身份证号码计算年龄
     * 
     * @param id
     * @return age
     *         - int
     */
    public static int calcAge(String id) {
        int age = 0;

        String tmp = "";

        String curYear = IDateUtil.getYMD(new Date()).substring(0, 4);

        if (isEmpty(id) || id.length() < 15) {

            return age;

        } else {
            if (id.length() < 18) {
                tmp = "19" + id.substring(6, 8);
            } else {
                tmp = id.substring(6, 10);
            }

            age = Integer.valueOf(curYear) - Integer.valueOf(tmp);

        }

        return age;

    }

    /**
     * 2个数相加返回结果
     * 
     * @author linxuebing
     * @param a
     * @param b
     * @return String 类型的结果
     */
    public static String add(Object a, Object b) {
        String res = null;
        try {
            res = stringOf(new BigDecimal(a.toString()).add(new BigDecimal(b.toString())));
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 判断对象是否为空
     * 
     * @author linxuebing
     * @param obj
     * @return true 空
     *         false 非空
     */
    public static boolean isEmpty(Object obj) {
        return null == obj || "".equals(obj.toString().trim());
    }

    /**
     * 判断字符串是否有值
     * 
     * @param obj
     * @return ture 有值
     *         false 无值
     */
    public static boolean unEmpty(Object obj) {
        return !isEmpty(obj);
    }
    /**
     * 将list转换为可以sql查询的字符串
     * 
     * @param List<String>
     * @return String
     */
    public static String listToSqlString(List<String> valueList) {
    	String sqlList = null;
    	if(valueList != null && valueList.size() > 0){
    		sqlList = "(";
    		for(String value : valueList){
    			value = "'"+ value+"',";
    			sqlList += value;
    		}
    		sqlList = sqlList.substring(0, sqlList.length()-1) + ")";
    	}
    	
    	return sqlList;
    }

    /**
     * 将对象转化成Double类型
     * 
     * @param obj
     * @return
     */
    public static Double doubleOf(Object obj){
        Double d = null;
        try {
            d = Double.valueOf(obj.toString());
        } catch (Exception e) {

        }
        return d;
    }
    /**
     * 判断字符数组是否包含给定字符
     * @param strings  字符数组
     * @param containStr 被包含字符串
     * @return
     */
    public static boolean containsStr(String[] strings,String containStr){
    	boolean flag = false;
    	if (strings != null && strings.length >0) {
    		for (int i = 0; i < strings.length; i++) {
    			if(containStr.equals(strings[i])){
    				flag = true;
    			}
    		}
		}
    	return flag;
    }
    public static void main(String[] args) {
        //System.out.println(calcAge("410327810518141"));
//        System.out.println(containsStr(Constants.UN_USER_PARAM,"G_LOAN/INCOME1"));
        //    	//System.out.println("410327198105181418".substring(8,10));
    }
}
