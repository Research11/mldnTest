package org.ibas.credit.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;

/**
 * 
 * 类名称： CalcUtil
 * 类描述：指标计算工具类
 * 创建人：TangDu 创建时间：2012-6-6
 * 版本 ：1.4
 * 
 * 修改信息：
 * --------------------------------------------------------------------------
 * 编号 修改原因 时间 版本 修改人 备注
 * 1 修改divide方法 2012/6/9 1.1 唐杜 Integer/Integer没有小数位
 * 2 增加时间 计算纬度问题 2012/6/15 1.2 唐杜
 * 3 修改calcFreq 2012/6/18 1.3 唐杜 除数为0的
 * 4 指标计算，修改方法 2012/6/26 1.4 唐杜
 * --------------------------------------------------------------------------
 */
public class CalcUtil {

    private final static Logger log = Logger.getLogger(CalcUtil.class);

    /**
     * 比较两个日期，是否d2在d1之后-年单位
     * 
     * @param d1
     * @param d2
     * @return true - d2 >= d1 (d2 不在d1之前)
     *         false - d2 < d1 (d2 在d1之前)
     */
    public static boolean compare2DateWithoutDay(Date d1, Date d2) {

        try {

            if (d1 != null && d2 != null) {
                // 只计算到月 不计算日期
                Calendar c1 = Calendar.getInstance();
                c1.setTime(d1);
                c1.set(Calendar.DATE, 1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(d2);
                c2.set(Calendar.DATE, 1);

                return !c2.before(c1);
            }

        } catch (Exception e) {
            log.error("[calcTwoDateCompareTo] - date calcTwoDateCompareTo failure! Error is:", e);
        }

        return false;
    }

    /**
     * 计算两个日期，相差的年数
     * 
     * @param d1
     * @param d2
     * @return d1- d2
     * 
     */
    public static int subDateWithDay(Date d1, Date d2) {

        try {

            if (d1 != null && d2 != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(d1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(d2);

                return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR));
            }

        } catch (Exception e) {
            log.error("[subDateWithDay] - date subDateWithDay failure! Error is:", e);
        }

        return 0;
    }

    /**
     * 比较两个日期，是否d2在d1之后-月单位
     * 
     * @param d1
     * @param d2
     * @return true - d2 >= d1 (d2 不在d1之前)
     *         false - d2 < d1 (d2 在d1之前)
     */
    public static boolean compareRangeM(Date d1, Date d2) {
        try {

            if (d1 != null && d2 != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(d1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(d2);

                return !c2.before(c1);
            }

        } catch (Exception e) {
            log.error("[compareRangeM] - date compareRangeM failure! Error is:", e);
        }

        return false;
    }

    /**
     * 比较两个日期，是否d2在d1之后-不计算当月
     * 
     * @param d1
     * @param d2
     * @return true - d2 >= d1 (d2 不在d1之前)
     *         false - d2 < d1 (d2 在d1之前)
     */
    public static boolean compareRangeY(Date d1, Date d2) {
        try {

            if (d1 != null && d2 != null) {
                return d1.compareTo(d2) == 0 || d1.before(d2);
            }

        } catch (Exception e) {
            log.error("[compareRangeM] - date compareRangeM failure! Error is:", e);
        }

        return false;
    }

    /**
     * 将double 四舍五入 取2位
     * 
     * @param val
     * @return
     */
    public static Double getRoundDouble(Double val) {
        try {
            if (val != null) {
                return (new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        } catch (Exception e) {
            log.error("[getRoundDouble]-date getRoundDouble failure", e);
        }
        return 0.00;
    }

    /**
     * 将double 四舍五入-百分比 取4位
     * 
     * @param val
     * @return
     */
    public static Double getRoundDoubleRate(Double val) {
        try {
            if (val != null && !Double.isNaN(val)) {
                return (new BigDecimal(val).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        } catch (Exception e) {
            log.error("[getDateOverMonth]-date getRoundDouble failure", e);
        }
        return 0.0000;
    }

    /**
     * 加
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double add(Number d1, Number d2) {
        Double result = 0.0;
        try {

            if (d1 == null) {
                d1 = 0.0;
            }
            if (d2 == null) {
                d2 = 0.0;
            }

            result = getRoundDouble(d1.doubleValue() + d2.doubleValue());

        } catch (Exception e) {
            log.error("[add]-  add failure", e);
        }
        return result;
    }

    /**
     * 根据比较类型取最值,"L"代表取最大值，其他最小值
     * 
     * @param d1
     * @param d2
     * @param compareType
     * @return
     */
    public static Double compareNumber(Number d1, Number d2, String compareType) {
        Double result = 0.0;
        try {
            Double max = 0.0;
            Double min = 0.0;

            if (d1 != null && d2 != null) {
                if (d1.doubleValue() > d2.doubleValue()) {
                    max = d1.doubleValue();
                    min = d2.doubleValue();
                } else {
                    max = d2.doubleValue();
                    min = d1.doubleValue();
                }
            }

            if ("L".equals(compareType)) {
                result = max;
            } else {
                result = min;
            }

        } catch (Exception e) {
            log.error("[compare]-  compare failure", e);
        }
        return result;
    }

    /**
     * 加
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Integer add(Integer d1, Integer d2) {
        Integer result = 0;
        try {

            if (d1 == null) {
                d1 = 0;
            }
            if (d2 == null) {
                d2 = 0;
            }

            result = d1 + d2;

        } catch (Exception e) {
            log.error("[add]-  add failure", e);
        }
        return result;
    }

    /**
     * 减
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double subtract(Double d1, Double d2) {
        Double result = 0.0;
        try {

            if (d1 == null) {
                d1 = 0.0;
            }
            if (d2 == null) {
                d2 = 0.0;
            }

            result = getRoundDouble(d1 - d2);

        } catch (Exception e) {
            log.error("[subtract]-  subtract failure", e);
        }
        return result;
    }

    /**
     * 乘
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double multiply(Double d1, Double d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                result = getRoundDouble(d1 * d2);
            }
        } catch (Exception e) {
            log.error("[multiply]-  multiply failure", e);
        }
        return result;
    }

    /**
     * 除
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double divide(Double d1, Double d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                if (d2 != 0) {//除数不为0
                    result = getRoundDouble(d1 / d2);
                }
            }
        } catch (Exception e) {
            log.error("[divide]-  divide failure", e);
        }
        return result;
    }

    /**
     * 除
     * 保留4位
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double divideFour(Double d1, Double d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                if (d2 != 0) {//除数不为0
                    result = getRoundDoubleRate(d1 / d2);
                }
            }
        } catch (Exception e) {
            log.error("[divide]-  divide failure", e);
        }
        return result;
    }

    /**
     * 除
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double divide(Double d1, Integer d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                if (d2 != 0) {//除数不为0
                    result = getRoundDouble(Double.valueOf(d1) / Double.valueOf(d2));
                }
            }
        } catch (Exception e) {
            log.error("[divide]-  divide failure", e);
        }
        return result;
    }

    /**
     * 除
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double divide(Double d1, Short d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                if (d2 != 0) {//除数不为0
                    result = getRoundDouble(Double.valueOf(d1) / Double.valueOf(d2));
                }
            }
        } catch (Exception e) {
            log.error("[divide]-  divide failure", e);
        }
        return result;
    }

    /**
     * 除
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double divide(Integer d1, Integer d2) {
        Double result = 0.0;
        try {
            if (d1 != null && d2 != null) {
                if (d2 != 0) {//除数不为0
                    result = getRoundDouble(Double.valueOf(d1) / Double.valueOf(d2));
                }
            }
        } catch (Exception e) {
            log.error("[divide]-  divide failure", e);
        }
        return result;
    }

    /**
     * 计算比率,保留四位
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double calcFreq(Number d1, Number d2) {
        Double result = 0.0000D;
        try {
            if (d1 != null && d2 != null && d2.doubleValue() != 0) {
                return getRoundDoubleRate(d1.doubleValue() / d2.doubleValue());
            }
        } catch (Exception e) {
            log.error("[calcRate]- calc rate failure", e);
        }
        return result;
    }

    /**
     * 对于有些指标计算，当分母给为0或空时，返回值为-1，此值为标志值不参与预警和计算。
     * 计算比率,保留四位
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double calcFreqSpec(Number d1, Number d2) {
        Double result = -1D;
        try {
            if (d1 != null && d2 != null && d2.doubleValue() != 0) {
                return getRoundDoubleRate(d1.doubleValue() / d2.doubleValue());
            }
        } catch (Exception e) {
            log.error("[calcRate]- calc rate failure", e);
        }
        return result;
    }

    /**
     * 对于有些指标计算，当分母给为0或空时，返回值为999，此值为标志值参与预警，
     * 但不参与模型计算。
     * 计算比率,保留四位
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Double calcFreqSpec2(Number d1, Number d2) {
        Double result = -1D;
        try {
            if (d1 != null && d2 != null && d2.doubleValue() != 0) {
                return getRoundDoubleRate(d1.doubleValue() / d2.doubleValue());
            }
        } catch (Exception e) {
            log.error("[calcRate]- calc rate failure", e);
        }
        return result;
    }

    /**
     * 
     * 取两个日期间最小 值
     * 
     * @param d1
     * @param d2
     * @return Date - null，当两个参数都为null时；
     *         - Date，两个日期的最大值。
     */
    public static Date getEarlierDate(Date d1, Date d2) {

        if (d1 == null)
            return d2;

        if (d2 == null)
            return d1;

        if (d1.after(d2)) {
            return d2;
        } else {
            return d1;
        }

    }

    /**
     * 
     * 取三个日期间最小 值
     * 
     * @param d1
     * @param d2
     * @param d3
     * @return Date - null，当三个参数都为null时；
     *         - Date，三个日期的最小值。
     */
    public static Date getEarliestDate(Date d1, Date d2, Date d3) {

        return getEarlierDate(getEarlierDate(d1, d2), d3);
    }

    /**
     * 信贷传过来的汇率是字符 按如 CNY@6.83|
     * 解析成map
     * 
     * @param exchRateStr
     * @return
     */
    public static Map<String, Double> getExchRateMap(String exchRateStr) {
        Map<String, Double> map = new HashMap<String, Double>();
        if (exchRateStr != null && !"".equals(exchRateStr) && exchRateStr.indexOf("@") > -1 && exchRateStr.indexOf("|") > -1) {
            String rate[] = exchRateStr.split("\\|");
            for (String r : rate) {
                String exch_[] = r.split("@");
                if (exch_.length > 1) {
                    map.put(exch_[0], Double.valueOf(exch_[1]));
                }
            }
        }
        return map;
    }

    /**
     * 取得map中对应code的概率
     * 
     * @param rateKey
     * @param map
     * @return double
     */
    public static Double getRateToMap(String rateKey, Map<String, Double> map) {
        if (map != null && rateKey != null && !"".equals(rateKey)) {

            for (Entry<String, Double> en : map.entrySet()) {
                if (en.getKey().equals(rateKey)) {
                    return en.getValue();
                }
            }
        }
        return 1.0D;
    }

    /**
     * 日期加减方法
     * 
     * @param date
     * @param i
     * @return
     */
    public static Date addMonth(Date date, int i) {

        if (date != null) {

            Calendar cld = Calendar.getInstance();
            cld.setTime(date);
            cld.add(Calendar.MONTH, i);

            return cld.getTime();
        }

        return null;
    }
    
    /**
     * 采用JS中的Eval计算字符串公式的值
     * @param str 计算工资
     * @param param 参数值
     * @return
     */
    public static Double jsEvelCalStr(String str,String param){
    	ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("js");
		try {
			String calStr = str.replace("excess", param);
			Double result = (Double)se.eval(calStr);
			log.info("CalcUtil中的jsEvelCalStr返回值result为"+result);
			return result;
		} catch (ScriptException e) {
			e.printStackTrace();
			log.info("CalcUtil中的jsEvelCalStr返回值result为0");
			return 0D;
		}
    }

}
