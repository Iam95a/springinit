/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

/**
 * @since 2017年4月7日 下午3:37:07
 * @version $Id: PageUtils.java 7 2017-04-07 10:05:23Z lml2017 $
 * @author SuShuai
 *
 */
public final class PageUtils {
	// 获取分页起始编码
	public static int getPageNum(int page, int pageSize) {
		return page <= 1 ? 0 : (page - 1) * pageSize;
	}

}
