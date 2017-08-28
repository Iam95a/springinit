/**
 * Copyright(C) 2013 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.util.Map;

/**
 * @since Aug 29, 2013 1:54:33 PM
 * @version $Id: StringMap.java 15463 2016-05-28 07:44:34Z WuJianqiang $
 * @author WuJianqiang
 * 
 * @param <V>
 */
public interface StringMap<V> extends Map<String, V> {

	/**
	 * 默认 key 大小写不敏感， caseInsensitive = true
	 * 
	 * @return Returns the caseInsensitive.
	 */
	public boolean isCaseInsensitive();

	/**
	 * 默认 key 大小写不敏感， caseInsensitive = true
	 * 
	 * @param value
	 *            The caseInsensitive to set.
	 */
	public void setCaseInsensitive(boolean caseInsensitive);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public V remove(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public V get(String key);

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(String key, V value);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Byte getByte(String key, Byte defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Byte getByte(String key);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Short getShort(String key, Short defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Short getShort(String key);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String key, Integer defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key);

}