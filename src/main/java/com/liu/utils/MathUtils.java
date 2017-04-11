package com.liu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
/**
 * 运算类工具类
 * @author liuzhihong
 * @date 2017年4月11日  下午2:10:39
 */
public class MathUtils {

	public static final int SCALE = 2;
	public static final int PERCENTAGE_SCALE = 4;
	
	/**
	 * 按四舍五入法获得2位小数的商
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static double getQuotient4down5up(Number dividend,Number divisor) {
		double quotient = 0.00;
		if (dividend != null && divisor != null && divisor.doubleValue() != 0.0){
			quotient = new BigDecimal(dividend.doubleValue())
				.divide(new BigDecimal(divisor.doubleValue()),SCALE,BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		}
		return quotient;
	}
	
	/**
     * 按四舍五入法获得指定位小数的商
     * @param dividend
     * @param divisor
     * @return
     */
    public static double getQuotient4down5up(Number dividend,Number divisor,int specificScale) {
        double quotient = 0.00;
        if (divisor.doubleValue() != 0.0 && dividend != null){
            quotient = new BigDecimal(dividend.doubleValue())
                .divide(new BigDecimal(divisor.doubleValue()),specificScale,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        }
        return quotient;
    }
	
	/**
	 * 按四舍五入法获得2位小数的商，并转化为百分数，且保留两位小数
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static String getPercentage4down5up(Number dividend,Number divisor) {
		double quotient = 0.00;
		if (divisor.doubleValue() != 0.0){
			quotient = new BigDecimal(dividend.doubleValue())
				.divide(new BigDecimal(divisor.doubleValue()),PERCENTAGE_SCALE,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
				.doubleValue() ;
		}
		return formatDouble(quotient,2) + "%";
	}
		
	public static String getPercentageFromDecimal4down5up(double decimal) {
		double approximation = new java.math.BigDecimal(Double.toString(decimal)).multiply(new BigDecimal(100)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
		return formatDouble(approximation,2) + "%";
	}
	
	public static String getPercentageFromDecimal4down5up(double decimal,int decimalCount) {
		double approximation = new java.math.BigDecimal(Double.toString(decimal)).multiply(new BigDecimal(100)).setScale(decimalCount,BigDecimal.ROUND_HALF_UP).doubleValue();
		return formatDouble(approximation,decimalCount) + "%";
	}
	
	/**
	 * 
	 * @param decimal
	 * @return 保留 ${decimalCount}位小数
	 */
	public static String formatDouble(double decimal,int decimalCount) {
		StringBuilder sb = new StringBuilder("#####0.");
		for (int i = 0; i < decimalCount; i++) {
			sb.append("0");
		}
		DecimalFormat df  = new DecimalFormat(sb.toString());   
		df.format(new BigDecimal(0.00));
        return df.format(decimal);
	}
	
	/**
	 * 
	 * @param decimal
	 * @return 保留两位小数
	 */
	public static String formatDouble00(BigDecimal decimal) {
		DecimalFormat df  = new DecimalFormat("#####0.00");   
        return df.format(decimal);
	}
	
	public static BigDecimal sum(BigDecimal augend1,BigDecimal augend2) {
		if (augend1 != null && augend2 != null) {
			return augend1.add(augend2);   
		} else {
			return augend1 != null ? augend1:augend2;
		}
	}
	
	public static long sum(Long augend1,Long augend2) {
		if (augend1 != null && augend2 != null) {
			return augend1.longValue() + augend2.longValue();   
		} else {
			return augend1 != null ? augend1.longValue():augend2.longValue();
		}
	}
}
