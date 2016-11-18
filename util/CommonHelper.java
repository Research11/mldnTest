package org.ibas.credit.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class CommonHelper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //            HashSet<String> hm = new HashSet<String>();
        //            int i = 0;
        //            for (;;) {
        //                Date date = new Date();
        //                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //                String swiftNumber = formatter.format(date);
        //                if (swiftNumber.length() < 17) {
        //                    //System.out.println(swiftNumber);
        //                    break;
        //    
        //                }
        //                hm.add(swiftNumber);
        //                if (i > 1000000) {
        //                    break;
        //                }
        //                i++;
        //            }
        //System.out.println(getSwiftNumber("2004", "0", "1"));
        //System.out.println(getSwiftNumber("2004", "0", "1").length());
    }

    /**
     * 生成流水号
     * 规则：系统标示固定（1）+ 险种（4）+ 机构前两位（2）+ 年份（4） + 自增长（10）
     * 
     * @return
     */
    public static String getSwiftNumber(String title, String orgCode, String sequence) {

        //系统标示
        String sysCode = "C";

        //机构代码的前两位
        if (!StringUtils.isEmpty(orgCode)) {
            if (orgCode.length() > 1) {
                orgCode = orgCode.substring(0, 2);
            } else {
                orgCode = "0" + orgCode;
            }
        }

        //获取年份
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String yearStr = formatter.format(date);

        //sequence处理成10位
        String temSeq = sequence;
        if (!StringUtils.isEmpty(sequence)) {
            if (sequence.length() < 10) {
                for (int i = 1; i <= 10 - sequence.length(); i++) {
                    temSeq = "0" + temSeq;
                }
            }
        }

        return sysCode + title + orgCode + yearStr + temSeq;
    }

    /**
     * @Describe 检验组织结构代码是否合法
     *           标准:GB11714-1997
     * @author syp
     * @since 2014-12-14
     */
    public static boolean checkOrganizationCodegb1997(String code) {
        int[] ws = { 3, 7, 9, 10, 5, 8, 4, 2 };
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String regex = "^([0-9A-Z]){8}-[0-9|X]$";
        if (!code.matches(regex)) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
        }
        int c9 = 11 - (sum % 11);
        String sc9 = String.valueOf(c9);
        if (11 == c9) {
            sc9 = "0";
        } else if (10 == c9) {
            sc9 = "X";
        }
        return sc9.equals(String.valueOf(code.charAt(9)));
    }

    /**
     * null 返回0
     * 
     * @param value
     * @return
     */
    public static Double getDoubleValue(Double value) {
        return (value == null ? 0 : value);
    }

    public static Object copy(Object object) throws Exception {
        Class<?> classType = object.getClass();
        Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
        Field fields[] = classType.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});
            Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });
            Object value = getMethod.invoke(object, new Object[] {});
            setMethod.invoke(objectCopy, new Object[] { value });
        }
        return objectCopy;
    }
}
