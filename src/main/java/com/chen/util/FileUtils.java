/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.io.File;

/**
 * @since 2017年3月31日 下午5:03:49
 * @version $Id: FileUtils.java 2 2017-04-05 05:43:59Z lml2017 $
 * @author SuShuai
 *
 */
public class FileUtils {
	/**
	 * 删除文件、文件夹
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] ff = file.listFiles();
			for (int i = 0; i < ff.length; i++) {
				deleteFile(ff[i].getPath());
			}
		}
		file.delete();
	}

	/**
	 * 判断文件类型
	 * 
	 * @param contentType
	 * @param allowTypes
	 * @return
	 */
	public static boolean isValid(String contentType, String... allowTypes) {
		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.indexOf(type) > -1) {
				return true;
			}
		}
		return false;
	}

}
