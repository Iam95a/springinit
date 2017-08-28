/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @since 2017年3月29日 下午2:49:26
 * @version $Id: HttpUtills.java 2 2017-04-05 05:43:59Z lml2017 $
 * @author SuShuai
 *
 */
public class HttpUtills {
	private static final String[] HEADERS_TO_TRY_CLIENT_IP = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP" };
	private static final Pattern MULTI_IP_SEPARATOR = Pattern.compile("[,;/ ]");

	public static String getClientIP(HttpServletRequest request, boolean firstOnly) {
		// TODO RFC 7239
		for (String header : HEADERS_TO_TRY_CLIENT_IP) {
			String ips = request.getHeader(header);
			if (null != ips && ips.length() > 0 && !"unknown".equalsIgnoreCase(ips)) {
				if (firstOnly) {
					// X-Forwarded-For: client, proxy1, proxy2
					for (String ip : MULTI_IP_SEPARATOR.split(ips)) {
						if (ip.length() > 0) {
							return ip;
						}
					}
				} else {
					StringBuilder sb = new StringBuilder(ips);
					sb.append(", ").append(request.getRemoteAddr());
					return sb.toString();
				}
			}
		}
		return request.getRemoteAddr();
	}
}
