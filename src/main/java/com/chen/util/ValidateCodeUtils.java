package com.chen.util;

import java.util.Random;

/**
 * 验证码工具类
 * 
 * @author andong
 * 
 */
public class ValidateCodeUtils {

	private static Random random = new Random();

	public static String generalValidateCode() {
		return random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9);
	}

	/**
	 * 生成验证码
	 * 
	 * @param count
	 * @return
	 */
	public static String generateSMSCaptcha(int count, int charHas) {
		String smsCaptcha = "";
		String sumSymbols = "";
		if (charHas <= 0) {// 数字
			sumSymbols = "012345678901234567890123456789";
		} else if (charHas <= 1) {// 数字+大写
			sumSymbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		} else {// 数字+大小写
			sumSymbols = "0123456789ABCDefg0123456789hijkLMN0123456789OPQrst0123456789uvwXYZ0123456789abcdEFG0123456789HIJKlmn0123456789opqRST0123456789UVWxyz";
		}
		int charSize = sumSymbols.length();
		int random = (int) (Math.random() * 100);
		int length = (count >= 1 ? count : 6 + (random % 3));
		for (int i = 1; i <= length; i++) {
			random = (int) (Math.random() * charSize);
			smsCaptcha += sumSymbols.charAt(random);
		}
		return smsCaptcha;
	}

}
