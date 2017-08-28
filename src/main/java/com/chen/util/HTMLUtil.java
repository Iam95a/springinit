/**
 * Copyright(C) 2010 Fugle Technology Co. Ltd. All rights reserved.
 * 
 */
package com.chen.util;

/**
 * @since 2010-4-19 下午09:55:21
 * @version $Id: HTMLUtil.java 15463 2016-05-28 07:44:34Z WuJianqiang $
 * @author WuJianqiang
 * 
 */
public final class HTMLUtil {
	/*
	 * <!ENTITY nbsp CDATA "&#160;" -- no-break space = non-breaking space, U+00A0 ISOnum -->
	 * <!-- C0 Controls and Basic Latin -->
	 * <!ENTITY quot CDATA "&#34;" -- quotation mark = APL quote, U+0022 ISOnum -->
	 * <!ENTITY amp CDATA "&#38;" -- ampersand, U+0026 ISOnum -->
	 * <!ENTITY lt CDATA "&#60;" -- less-than sign, U+003C ISOnum -->
	 * <!ENTITY gt CDATA "&#62;" -- greater-than sign, U+003E ISOnum --> 
	 */
	private static final char SPACE = ' ';
	private static final char EQUAL = '=';
	private static final char BEGIN = '<';
	private static final char ENDED = '/';
	private static final char END = '>';
	private static final char QUOTES = '\"';
	private static final char NEWLINE = '\n';
	private static final String CRLF = "\r\n";
	private static final String NBSP = "&#160;"; // UTF-8 code: 0xC2A0
	private static final String TR = "tr";
	private static final String TH = "th";
	private static final String TD = "td";
	private static final String[] ESCAPE_OLD_SUB = { "&", "<", ">", "\"", " ", CRLF, "\n" };
	private static final String[] ESCAPE_NEW_SUB = { "&#38;", "&#60;", "&#62;", "&#34;", NBSP, "<br/>" };

	// Prevent instantiation
	private HTMLUtil() {
		super();
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public static String escape(String content) {
		if (null != content && content.length() > 0) {
			return StringUtil.replaceAll(content, ESCAPE_OLD_SUB, ESCAPE_NEW_SUB);
		} else {
			return NBSP;
		}
	}

	/**
	 * 
	 * @param sb
	 * @param equivalent
	 * @param content
	 * @return
	 */
	public static StringBuilder getMetaElement(StringBuilder sb, String equivalent, String content) {
		sb.append("<meta http-equiv=\"").append(equivalent).append("\" content=\"").append(content).append("\">" + CRLF);
		return sb;
	}

	/**
	 * 
	 * @param equivalent
	 * @param content
	 * @return
	 */
	public static String getMetaElement(String equivalent, String content) {
		StringBuilder sb = new StringBuilder(equivalent.length() + content.length() + 40);
		return getMetaElement(sb, equivalent, content).toString();
	}

	/**
	 * 
	 * @param sb
	 * @param linkFile
	 * @return
	 */
	public static StringBuilder getLinkElementCSS(StringBuilder sb, String linkFile) {
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(linkFile).append("\"/>" + CRLF);
		return sb;
	}

	/**
	 * 
	 * @param linkFile
	 * @return
	 */
	public static String getLinkElementCSS(String linkFile) {
		StringBuilder sb = new StringBuilder(linkFile.length() + 64);
		return getLinkElementCSS(sb, linkFile).toString();
	}

	/**
	 * 
	 * @param sb
	 * @param scriptFile
	 * @return
	 */
	public static StringBuilder getScriptElement(StringBuilder sb, String scriptFile) {
		sb.append("<script type=\"text/javascript\" src=\"").append(scriptFile).append("\"></script>" + CRLF);
		return sb;
	}

	/**
	 * 
	 * @param scriptFile
	 * @return
	 */
	public static String getScriptElement(String scriptFile) {
		StringBuilder sb = new StringBuilder(scriptFile.length() + 64);
		return getScriptElement(sb, scriptFile).toString();
	}

	/**
	 * 
	 * @param sb
	 * @param element
	 * @return
	 */
	public static StringBuilder beginElement(StringBuilder sb, String element) {
		if (sb.length() > 0 && TR.equalsIgnoreCase(element)) {
			sb.append(NEWLINE);
		}
		sb.append(BEGIN).append(element).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder beginElementTR(StringBuilder sb) {
		if (sb.length() > 0) {
			sb.append(NEWLINE);
		}
		sb.append(BEGIN).append(TR).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder beginElementTH(StringBuilder sb) {
		sb.append(BEGIN).append(TH).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder beginElementTD(StringBuilder sb) {
		sb.append(BEGIN).append(TD).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @param name
	 * @param value
	 * @return
	 */
	public static StringBuilder addAttribute(StringBuilder sb, String name, String value) {
		if (null != value && value.length() > 0) {
			int len = sb.length();
			if (len > 0) {
				sb.setLength(len - 1);
			}
			sb.append(SPACE).append(name).append(EQUAL).append(QUOTES).append(value).append(QUOTES);
			if (len > 0) {
				sb.append(END);
			}
		}
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @param name
	 * @param value
	 * @return
	 */
	public static StringBuilder addAttribute(StringBuilder sb, String name, int value) {
		return addAttribute(sb, name, Integer.toString(value));
	}

	/**
	 * 
	 * @param sb
	 * @param attribute
	 * @return
	 */
	public static StringBuilder addAttribute(StringBuilder sb, String attribute) {
		if (null != attribute && attribute.length() > 0) {
			int len = sb.length();
			if (len > 0) {
				sb.setLength(len - 1);
			}
			sb.append(SPACE).append(attribute);
			if (len > 0) {
				sb.append(END);
			}
		}
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @param content
	 * @return
	 */
	public static StringBuilder addContent(StringBuilder sb, String content) {
		return sb.append(escape(content));
	}

	/**
	 * 
	 * @param sb
	 * @param element
	 * @return
	 */
	public static StringBuilder endElement(StringBuilder sb, String element) {
		sb.append(BEGIN).append(ENDED).append(element).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @param element
	 * @param content
	 * @return
	 */
	public static StringBuilder addElement(StringBuilder sb, String element, String content) {
		if (sb.length() > 0 && TR.equalsIgnoreCase(element)) {
			sb.append(NEWLINE);
		}
		sb.append(BEGIN).append(element).append(END);
		sb.append(escape(content));
		sb.append(BEGIN).append(ENDED).append(element).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder endElementTR(StringBuilder sb) {
		sb.append(BEGIN).append(ENDED).append(TR).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder endElementTH(StringBuilder sb) {
		sb.append(BEGIN).append(ENDED).append(TH).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static StringBuilder endElementTD(StringBuilder sb) {
		sb.append(BEGIN).append(ENDED).append(TD).append(END);
		return sb;
	}

	/**
	 * 
	 * @param sb
	 * @param colSpan
	 * @param rowSpan
	 * @param width
	 *            CSS格式，数字或百分比等
	 */
	public static void addEmptyTD(StringBuilder sb, int colSpan, int rowSpan, String width) {
		beginElement(sb, TD);
		if (colSpan > 1) {
			addAttribute(sb, "colSpan", colSpan);
		}
		if (rowSpan > 1) {
			addAttribute(sb, "rowSpan", rowSpan);
		}
		if (null != width) {
			addAttribute(sb, "width", width);
		}
		sb.append(NBSP);
		endElement(sb, TD);
	}

	/**
	 * 
	 * @param sb
	 * @param colSpan
	 * @param width
	 *            CSS格式，数字或百分比等
	 */
	public static void addEmptyTD(StringBuilder sb, int colSpan, String width) {
		addEmptyTD(sb, colSpan, 0, width);
	}

	/**
	 * 
	 * @param source
	 * @param element
	 * @param caseInsensitive
	 * @return
	 */
	public static String getOuterHTML(String source, String element, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(element.length() + 16);
		sb.append(BEGIN).append(element);
		String start = sb.toString();
		int index = 0;
		int beginIndex = 0;
		for (;;) {
			beginIndex = StringUtil.indexOf(source, start, index, caseInsensitive);
			if (beginIndex < 0) {
				return null;
			}
			index = beginIndex + start.length();
			char ch = source.charAt(index);
			if (ch == END || Character.isWhitespace(ch)) {
				break;
			}
		}
		sb.setLength(0);
		sb.append(BEGIN).append(ENDED).append(element).append(END);
		String stop = sb.toString();
		int endIndex = StringUtil.indexOf(source, stop, index, caseInsensitive);
		if (endIndex < 0) {
			return null;
		}
		return source.substring(beginIndex, endIndex + stop.length());
	}

	/**
	 * 
	 * @param source
	 * @param element
	 * @param caseInsensitive
	 * @return
	 */
	public static String getInnerHTML(String source, String element, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(element.length() + 16);
		sb.append(BEGIN).append(element);
		String start = sb.toString();
		int index = 0;
		int beginIndex = 0;
		for (;;) {
			beginIndex = StringUtil.indexOf(source, start, index, caseInsensitive);
			if (beginIndex < 0) {
				return null;
			}
			index = beginIndex + start.length();
			char ch = source.charAt(index);
			if (ch == END || Character.isWhitespace(ch)) {
				break;
			}
		}
		sb.setLength(0);
		sb.append(BEGIN).append(ENDED).append(element).append(END);
		String stop = sb.toString();
		int endIndex = StringUtil.indexOf(source, stop, index, caseInsensitive);
		if (endIndex < 0) {
			return null;
		}
		beginIndex = source.indexOf(END, index);
		if (beginIndex >= endIndex) {
			return null;
		}
		return source.substring(beginIndex + 1, endIndex);
	}

}
