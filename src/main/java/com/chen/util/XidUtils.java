/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.util.regex.Pattern;

/**
 * @since 2017年3月29日 上午9:56:41
 * @version $Id: XidUtils.java 8 2017-04-10 00:56:09Z lml2017 $
 * @author SuShuai
 *
 */
public class XidUtils {
	private static final Pattern PATTERN_MOBILE_VALID = Pattern.compile("^1[34578]\\d{9}$");
	private static final Pattern PATTERN_PWD = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,17}$");

	/**
	 * 判断手机号码合法
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (null != mobile && mobile.length() >= 11) {
			return PATTERN_MOBILE_VALID.matcher(mobile).matches();
		}
		return false;
	}

	/**
	 * 判断密码合法（以字母开头，长度在6~18之间，只能包含字母、数字和下划线）
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean validPwd(String pwd) {
		if (null != pwd) {
			return PATTERN_PWD.matcher(pwd).matches();
		}
		return false;
	}

	/**
	 * 手机号码脱敏加密
	 * 
	 * @param mobile
	 * @return
	 */
	public static String encryptMobile(String mobile) {
		if (NullUtil.isNotNull(mobile)) {
			if (XidUtils.isMobile(mobile)) {
				mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
			}
		}
		return mobile;

	}

}
