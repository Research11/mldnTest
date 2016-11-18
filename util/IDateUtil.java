/**
 * 
 */
package org.ibas.credit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 * 
 * @author linxuebing
 * @version v1.0 2014年12月24日
 */
public class IDateUtil {

    /**
     * 将日期类型转化成
     * 
     * @param date
     * @param format
     *            日期格式 如 yyyy-MM-dd HH:mm:ss
     *            默认 yyyy-MM-dd HH:mm:ss
     * 
     * @return （date ==null || format 格式错误） 则返回null
     */
    public static String stringDate(Date date, String format) {
        String res = null;
        try {
            format = (format == null || "".equals(format)) ? "yyyy-MM-dd HH:mm:ss" : format;
            res = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 日期类型转化成 yyyy-MM-dd
     * 
     * @param date
     * @return date ==null 则返回null
     */
    public static String getYMD(Date date) {
        return stringDate(date, "yyyy-MM-dd");
    }

    public static void main(String[] args) {
        //System.out.println(stringDate(new Date(), ""));
    }
}
