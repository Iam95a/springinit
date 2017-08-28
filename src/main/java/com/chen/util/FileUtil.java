/**
 * Copyright(C) 2011 Fugle Technology Co. Ltd. All rights reserved.
 * 
 */
package com.chen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @since Jul 19, 2011 11:30:22 PM
 * @version $Id: FileUtil.java 15463 2016-05-28 07:44:34Z WuJianqiang $
 * @author WuJianqiang
 * 
 */
public final class FileUtil {
	protected static final Pattern INVALID_FILE_NAME = Pattern.compile("[\\\\/:*?\"<>|]");
	protected static final int MAX_DIR_NAME_LENGTH = Integer.valueOf(System.getProperty("MaximumDirNameLength", "200")).intValue();
	protected static final int MAX_FILE_NAME_LENGTH = Integer.valueOf(System.getProperty("MaximumFileNameLength", "64")).intValue();
	private static final int DEFAULT_BUFFER_SIZE = 4096;
	private static final int MIN_CODE_POINT = 0;
	private static final int FAST_PATH_MAX = 255;
	private static final int MULTI_BYTE_SIZE = 2;
	private static final char PERIOD = '.';
	private static final char SLASH = '/';
	private static final char BACKSLASH = '\\';

	// Prevent instantiation
	private FileUtil() {
		super();
	}

	/**
	 * 
	 * @param servletContext
	 * @param relativePath
	 *            如果 path 是绝对路径（以'/'开始），则此参数可以忽略（允许null）。
	 * @param path
	 *            不应该有Query部分
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String toServletPath(ServletContext servletContext, String relativePath, String path) throws FileNotFoundException {
		URL url;
		File file;
		if (path.charAt(0) == '/') {
			// 绝对路径，清洗所有的目录分隔符
			file = new File(path);
		} else {
			// 相对路径，清洗所有的目录分隔符
			file = new File(relativePath);
			file = new File(file.getParent(), path);
		}
		path = file.getPath();
		try {
			// 主要目的是清洗所有的“..”和“.”标记
			url = servletContext.getResource(path);
		} catch (MalformedURLException e) {
			throw new UtilException(path, e);
		}
		if (null == url) {
			throw new FileNotFoundException("File does not exist: " + path);
		}
		String servletPath = url.getPath();
		try {
			// 获取资源的基本根目录
			url = servletContext.getResource("/");
		} catch (MalformedURLException e) {
			throw new UtilException("/", e);
		}
		return servletPath.substring(url.getPath().length() - 1);
	}

	/**
	 * 
	 * @param servletContext
	 * @param path
	 * @return
	 */
	public static String getRealPath(ServletContext servletContext, String path) {
		return servletContext.getRealPath(path);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getFile(String fileName) {
		return new File(fileName);
	}

	/**
	 * 
	 * @param servletContext
	 * @param fileName
	 * @return
	 */
	public static File getFile(ServletContext servletContext, String fileName) {
		return getFile(getRealPath(servletContext, fileName));
	}

	/**
	 * 获取文件名，即最后一个路径分隔符（“/”或“\”）后面部分。
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		if (null != path) {
			char ch;
			for (int i = path.length() - 1; i >= 0; --i) {
				ch = path.charAt(i);
				if (ch == SLASH || ch == BACKSLASH) {
					return path.substring(i + 1);
				}
			}
			return path;
		}
		return null;
	}

	/**
	 * 获取文件扩展名，包括点(".")前缀
	 * 
	 * @param fileName
	 * @return 如果没有找到则返回 null
	 */
	public static String getFileExtension(String fileName) {
		if (null != fileName) {
			char ch;
			for (int i = fileName.length() - 1; i >= 0; --i) {
				ch = fileName.charAt(i);
				if (ch == PERIOD) {
					return fileName.substring(i);
				} else if (ch == SLASH || ch == BACKSLASH) {
					break;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		return getFile(fileName).isFile();
	}

	/**
	 * @param suffix
	 *            The suffix string to be used in generating the file's name; may be
	 *            <code>null</code>, in which case the suffix <code>".tmp"</code> will be used
	 * @return
	 * @throws IOException
	 * @see #getTempFile(String)
	 */
	public static File createTempFile(String suffix) throws IOException {
		File tempFile = File.createTempFile("~rap", suffix);
		tempFile.deleteOnExit();
		return tempFile;
	}

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static File createTempFileCopyFrom(URL url) throws IOException {
		File tempFile = createTempFile(getFileExtension(url.getFile()));
		copyFile(url, tempFile);
		return tempFile;
	}

	/**
	 * @param istream
	 * @param suffix
	 *            The suffix string to be used in generating the file's name; may be
	 *            <code>null</code>, in which case the suffix <code>".tmp"</code> will be used
	 * @return
	 * @throws IOException
	 */
	public static File createTempFileCopyFrom(InputStream istream, String suffix) throws IOException {
		File tempFile = createTempFile(suffix);
		copyFile(istream, tempFile);
		return tempFile;
	}

	/**
	 * @param istream
	 * @return
	 * @throws IOException
	 */
	public static File createTempFileCopyFrom(InputStream istream) throws IOException {
		File tempFile = createTempFile(null);
		copyFile(istream, tempFile);
		return tempFile;
	}

	/**
	 * @return 返回临时文件夹，如：C:\Temp\
	 */
	public static String getTempDirectory() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * @param fileName
	 * @return 返回存放在临时文件夹的文件路径，如：C:\Temp\fileName
	 */
	public static String getTempPathName(String fileName) {
		Path path = new Path(getTempDirectory());
		path.append(fileName);
		return path.toString();
	}

	/**
	 * @param fileName
	 * @return 返回存放在临时文件夹的文件对象，如：C:\Temp\fileName
	 * @see #createTempFile(String)
	 */
	public static File getTempFile(String fileName) {
		Path path = new Path(getTempDirectory());
		path.append(fileName);
		return new File(path.toString());
	}

	/**
	 * 
	 * @param servletContext
	 * @param path
	 * @return A <code>long</code> value representing the time the file was last modified, measured
	 *         in milliseconds since the epoch (00:00:00 GMT, January 1, 1970), or <code>0L</code>
	 *         if the file does not exist or if an I/O error occurs
	 */
	public static long getLastModified(ServletContext servletContext, String path) {
		return getFile(servletContext, path).lastModified();
	}

	/**
	 * 
	 * @param servletContext
	 * @param servletPath
	 * @param path
	 * @return
	 */
	public static String buildRevision(ServletContext servletContext, String servletPath, String path) {
		StringBuilder sb = new StringBuilder(path.length() + 32);
		sb.append(path).append("?t=");
		long lastModified = getLastModified(servletContext, servletPath);
		if (0 != lastModified) {
			sb.append(lastModified / 1000);
		} else {
			sb.append(System.currentTimeMillis());
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	public static String buildRevision(HttpServletRequest request, String path) {
		try {
			ServletContext servletContext = request.getSession().getServletContext();
			String servletPath = toServletPath(servletContext, request.getServletPath(), path);
			if (path.charAt(0) == '/') {
				path = URLUtil.aheadContextPath(request.getContextPath(), servletPath);
			}
			return buildRevision(servletContext, servletPath, path);
		} catch (FileNotFoundException e) {
			return path;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param limitSize
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName, int limitSize) throws IOException {
		File file = new File(fileName);
		long len = file.length(); // in bytes
		if (len <= 0 || limitSize > 0 && len > limitSize) {
			return null;
		} else {
			FileInputStream in = new FileInputStream(file);
			try {
				byte bt[] = new byte[(int) len];
				in.read(bt);
				return new String(bt);
			} finally {
				in.close();
			}
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException {
		return readFile(fileName, 0);
	}

	/**
	 * 
	 * @param servletContext
	 * @param fileName
	 * @param limitSize
	 * @return
	 * @throws IOException
	 */
	public static String readFile(ServletContext servletContext, String fileName, int limitSize) throws IOException {
		return readFile(getRealPath(servletContext, fileName), limitSize);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String toFileSystemDirectorySafeName(String name) {
		return toFileSystemSafeName(name, true, MAX_DIR_NAME_LENGTH, MULTI_BYTE_SIZE);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String toFileSystemSafeName(String name) {
		return toFileSystemSafeName(name, false, MAX_FILE_NAME_LENGTH, MULTI_BYTE_SIZE);
	}

	/**
	 * 文件名特殊字符更为宽泛的过滤与规范处理，适合所有操作系统。
	 * 
	 * @param name
	 * @param dirSeparators
	 * @param maxFileLength
	 * @param multiByteSize
	 * @return
	 * @see #toWindowsFileName(String fileName, String replacement)
	 * @see #toWindowsFileName(String fileName)
	 */
	public static String toFileSystemSafeName(String name, boolean dirSeparators, int maxFileLength, int multiByteSize) {
		char ch;
		char last = 0;
		int count = 0;
		int size = name.length();
		StringBuilder sb = new StringBuilder(size * 2);
		for (int i = 0; i < size; i++) {
			ch = name.charAt(i);
			if (multiByteSize > 1 && (ch < MIN_CODE_POINT || ch > FAST_PATH_MAX)) {
				count += multiByteSize;
				last = ch;
			} else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '-' || ch == '#' || ch == '.' && last != 0
					|| dirSeparators && (ch == '/' || ch == '\\')) {
				count += 1;
				last = ch;
			} else if (last != '_') {
				count += 1;
				last = '_';
			} else {
				continue;
			}
			if (count <= maxFileLength) {
				sb.append(last);
			}
			if (count >= maxFileLength) {
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 按Windows操作系统标准，文件名称不能包含如下9个字符，将被全部替换：
	 * <p>
	 * \ / : * ? " < > |
	 * 
	 * @param fileName
	 * @param replacement
	 * @return
	 * @see #toFileSystemSafeName(String name, boolean dirSeparators, int maxFileLength, int
	 *      multiByteSize)
	 */
	public static String toWindowsFileName(String fileName, String replacement) {
		if (null == fileName || fileName.length() <= 0) {
			throw new IllegalArgumentException("fileName");
		} else if (null == replacement) {
			throw new IllegalArgumentException("replace");
		} else {
			String newFileName = INVALID_FILE_NAME.matcher(fileName).replaceAll(replacement);
			if (newFileName.length() > 0) {
				return newFileName;
			}
			throw new IllegalArgumentException("File Name \"" + fileName + "\" results in a empty fileName!");
		}
	}

	/**
	 * 按Windows操作系统标准，文件名称不能包含如下9个字符，将被全部清除：
	 * <p>
	 * \ / : * ? " < > |
	 * 
	 * @param fileName
	 * @return
	 */
	public static String toWindowsFileName(String fileName) {
		return toWindowsFileName(fileName, "");
	}

	/**
	 * 按Windows操作系统标准，文件名称不能包含如下9个字符：
	 * <p>
	 * \ / : * ? " < > |
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isValidWindowsFileName(String fileName) {
		if (null != fileName && fileName.length() > 0 && !INVALID_FILE_NAME.matcher(fileName).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param fileToDelete
	 * @return
	 */
	public static boolean deleteFile(File fileToDelete) {
		if (fileToDelete == null || !fileToDelete.exists()) {
			return true;
		} else {
			boolean result = deleteChildren(fileToDelete);
			result &= fileToDelete.delete();
			return result;
		}
	}

	/**
	 * 
	 * @param parent
	 * @return
	 */
	public static boolean deleteChildren(File parent) {
		if (parent == null || !parent.exists()) {
			return false;
		}
		boolean result = true;
		if (parent.isDirectory()) {
			File files[] = parent.listFiles();
			if (files == null) {
				result = false;
			} else {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.getName().equals(".") || file.getName().equals("..")) {
						continue;
					}
					if (file.isDirectory()) {
						result &= deleteFile(file);
					} else {
						result &= file.delete();
					}
				}

			}
		}
		return result;
	}

	/**
	 * 
	 * @param src
	 * @param targetDirectory
	 * @throws IOException
	 */
	public static void moveFile(File src, File targetDirectory) throws IOException {
		if (!src.renameTo(new File(targetDirectory, src.getName()))) {
			throw new IOException((new StringBuilder()).append("Failed to move ").append(src).append(" to ").append(targetDirectory).toString());
		} else {
			return;
		}
	}

	/**
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileInputStream srcStream = new FileInputStream(srcFile);
		try {
			FileOutputStream destStream = new FileOutputStream(destFile);
			copyStream(srcStream, destStream, true);
		} catch (FileNotFoundException e) {
			try {
				srcStream.close();
			} catch (IOException ignore) {
			}
			throw e;
		}
	}

	/**
	 * 
	 * @param srcUrl
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(URL srcUrl, File destFile) throws IOException {
		InputStream srcStream = srcUrl.openStream();
		try {
			FileOutputStream destStream = new FileOutputStream(destFile);
			copyStream(srcStream, destStream, true);
		} catch (FileNotFoundException e) {
			try {
				srcStream.close();
			} catch (IOException ignore) {
			}
			throw e;
		}
	}

	/**
	 * 
	 * @param istream
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(InputStream istream, File destFile) throws IOException {
		try {
			FileOutputStream destStream = new FileOutputStream(destFile);
			copyStream(istream, destStream, true);
		} catch (FileNotFoundException e) {
			try {
				istream.close();
			} catch (IOException ignore) {
			}
			throw e;
		}
	}

	/**
	 * 
	 * @param istream
	 * @param ostream
	 * @param autoClose
	 * @throws IOException
	 */
	public static void copyStream(InputStream istream, OutputStream ostream, boolean autoClose) throws IOException {
		boolean success = false;
		try {
			byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];

			for (int len = istream.read(buffer); len >= 0; len = istream.read(buffer)) {
				ostream.write(buffer, 0, len);
			}
			success = true;
		} finally {
			if (autoClose) {
				IOException first = null;
				try {
					istream.close();
				} catch (IOException ioe) {
					if (null == first) {
						first = ioe;
					}
				}
				try {
					ostream.close();
				} catch (IOException ioe) {
					if (null == first) {
						first = ioe;
					}
				}
				if (success && null != first) {
					throw first;
				}
			}
		}
	}

	/**
	 * 
	 * @param istream
	 * @param ostream
	 * @throws IOException
	 */
	public static void copyStream(InputStream istream, OutputStream ostream) throws IOException {
		copyStream(istream, ostream, true);
	}

	/**
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void mkdirs(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new IOException((new StringBuilder()).append("Failed to create directory '").append(dir)
						.append("', regular file already existed with that name").toString());
			}
		} else if (!dir.mkdirs()) {
			throw new IOException((new StringBuilder()).append("Failed to create directory '").append(dir).append("'").toString());
		}
	}

}
