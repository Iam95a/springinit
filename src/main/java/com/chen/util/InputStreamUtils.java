/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

/**
 * @since 2017年4月18日 下午6:14:30
 * @version $Id: InputStreamUtils.java 18 2017-04-20 06:22:19Z lml2017 $
 * @author SuShuai
 *
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamUtils {

	final static int BUFFER_SIZE = 4096;

	// 将InputStream转换成String
	public static String InputStreamTOString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		byte[] data = new byte[BUFFER_SIZE];

		int count = -1;

		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}

		data = null;

		return new String(outStream.toByteArray(), "ISO-8859-1");

	}

	// 将InputStream转换成某种字符编码的String
	public static String InputStreamTOString(InputStream in, String encoding) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		byte[] data = new byte[BUFFER_SIZE];

		int count = -1;

		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}

		data = null;

		return new String(outStream.toByteArray(), "ISO-8859-1");

	}

	// 将String转换成InputStream
	public static InputStream StringTOInputStream(String in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("ISO-8859-1"));

		return is;

	}

	// 将InputStream转换成byte数组
	public static byte[] InputStreamTOByte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		byte[] data = new byte[BUFFER_SIZE];

		int count = -1;

		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}

		data = null;

		return outStream.toByteArray();

	}

	// 将byte数组转换成InputStream
	public static InputStream byteTOInputStream(byte[] in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(in);

		return is;

	}

	// 将byte数组转换成String
	public static String byteTOString(byte[] in) throws Exception {

		InputStream is = byteTOInputStream(in);

		return InputStreamTOString(is);

	}

	public long inputstreamtofile(InputStream ins, File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
		return file.length();
	}

}
