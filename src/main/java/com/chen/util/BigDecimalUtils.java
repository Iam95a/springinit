package com.chen.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 4;

	public static Double add(Double... param) {
		BigDecimal value = new BigDecimal("0");
		for (Double d : param) {
			if (null != d) {
				value = value.add(new BigDecimal(Double.toString(d
						.doubleValue())));
			}
		}
		return value.doubleValue();
	}

	public static Double sub(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static Double mul(Number... param) {
		BigDecimal value = new BigDecimal("1");
		for (Number d : param) {
			if (null == d) {
				return 0d;
			}
			value = value.multiply(new BigDecimal(Double.toString(d
					.doubleValue())));
		}
		return value.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double div(Double dividend, Double divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double div_down(Double dividend, Double divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE, BigDecimal.ROUND_DOWN);
	}

	public static Double div_down_two(Double dividend, Double divisor) {
		if (null == dividend) {
			return 0d;
		}
		return div(dividend, divisor, 2, BigDecimal.ROUND_DOWN);
	}

	public static Double div_down_two_up(Double dividend, Double divisor) {
		return div(dividend, divisor, 2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double div(Double dividend, Double divisor, Integer scale,
			Integer type) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, type).doubleValue();
	}
}
