/**
 * Copyright(C) 2014 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @since Dec 20, 2014 11:24:11 PM
 * @version $Id: ClassUtil.java 18987 2016-07-16 11:25:18Z WuJianqiang $
 * @author WuJianqiang
 * 
 */
public final class ClassUtil {
	private static final Pattern ENHANCER_BY_CGLIB = Pattern.compile("\\$\\$EnhancerByCGLIB\\$\\$\\w+");
	private static final int CONTEXT_OFFSET = 2; // may need to change if this class is redesigned
	private static final CallerResolver CALLER_RESOLVER;

	// Prevent instantiation
	private ClassUtil() {
		super();
	}

	static {
		CALLER_RESOLVER = AccessController.doPrivileged(new PrivilegedAction<CallerResolver>() {
			@Override
			public CallerResolver run() {
				return new CallerResolver();
			}
		});
	}

	/**
	 * 在当前执行类堆栈中获取 beforeAncestor 及其继承者之前的调用类
	 * 
	 * @param beforeAncestor
	 * @return 若没有发现则返回 null
	 */
	public static Class<?> getCallerClass(Class<?> beforeAncestor) {
		Class<?>[] callers = CALLER_RESOLVER.getClassContext();
		boolean found = false;
		int len = callers.length;
		for (int index = CONTEXT_OFFSET; index < len; ++index) {
			if (beforeAncestor.isAssignableFrom(callers[index])) {
				found = true;
			} else if (found) {
				return callers[index];
			}
		}
		return null;
	}

	/**
	 * 在当前执行类堆栈中获取调用者之前 callerOffset 个的调用类
	 * 
	 * @param callerOffset
	 *            0 based caller offset, indexes into the current method call context with a given
	 *            offset.
	 * @return
	 */
	public static Class<?> getCallerClass(final int callerOffset) {
		// ClassLoader.getCallerClassLoader() 内部方法实现类似功能，疑不同JDK实现平台不兼容
		Class<?>[] callers = CALLER_RESOLVER.getClassContext();
		int len = callers.length;
		for (int index = CONTEXT_OFFSET + callerOffset; index < len; ++index) {
			if (callers[index] != ClassUtil.class) {
				return callers[index];
			}
		}
		throw new IllegalArgumentException("callerOffset");
	}

	/**
	 * 在当前执行类堆栈中获取调用者之前的调用类
	 * 
	 * @return
	 */
	public static Class<?> getCallerClass() {
		return getCallerClass(0);
	}

	/**
	 * A helper class to get the call context. It subclasses SecurityManager to make
	 * getClassContext() accessible. An instance of CallerResolver only needs to be created, not
	 * installed as an actual security manager.
	 */
	private static final class CallerResolver extends SecurityManager {
		@Override
		protected Class<?>[] getClassContext() {
			return super.getClassContext();
		}
	}

	/**
	 * 
	 * @param caller
	 * @return
	 * @author (C) <a href="http://www.javaworld.com/columns/jw-qna-index.shtml">Vlad Roubtsov</a>,
	 *         2003
	 */
	public static ClassLoader getClassLoader(final Class<?> caller) {
		final ClassLoader callerLoader = caller.getClassLoader();
		final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();

		ClassLoader result;

		// if 'callerLoader' and 'contextLoader' are in a parent-child
		// relationship, always choose the child:
		if (callerLoader == contextLoader || null == callerLoader) { // most of
			result = contextLoader;
		} else if (null == contextLoader) {
			result = callerLoader;
		} else {
			ClassLoader parent = callerLoader.getParent();
			for (;; parent = parent.getParent()) {
				if (null == parent) {
					result = contextLoader;
					break;
				} else if (contextLoader == parent) {
					result = callerLoader;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @return
	 * @author (C) <a href="http://www.javaworld.com/columns/jw-qna-index.shtml">Vlad Roubtsov</a>,
	 *         2003
	 */
	public static ClassLoader getClassLoader() {
		return getClassLoader(getCallerClass(1));
	}

	/**
	 * 清除动态代理代码部分，如清除“com.ifugle.Test$$EnhancerByCGLIB$$...”为“com.ifugle.Test”
	 * 
	 * @param name
	 * @return
	 */
	private static String Clean$$Name(String name) {
		int index = name.lastIndexOf('$');
		if (index > 0 && name.charAt(index - 1) == '$') {
			name = ENHANCER_BY_CGLIB.matcher(name).replaceAll("");
		}
		return name;
	}

	/**
	 * 清除动态代理代码部分，如清除“com.ifugle.Test$$EnhancerByCGLIB$$...”为“com.ifugle.Test”
	 * 
	 * @param type
	 * @return
	 */
	public static String getClassName(Class<?> type) {
		return Clean$$Name(type.getName());
	}

	/**
	 * 清除动态代理代码部分，如清除“com.ifugle.Test$$EnhancerByCGLIB$$...”为“com.ifugle.Test”
	 * 
	 * @param bean
	 * @return
	 */
	public static String getClassName(Object bean) {
		if (null != bean) {
			return getClassName(bean.getClass());
		}
		return null;
	}

	/**
	 * 清除动态代理代码部分，如清除“com.ifugle.Test$$EnhancerByCGLIB$$...”为“com.ifugle.Test”
	 * 
	 * @param type
	 * @return
	 */
	public static String getCanonicalName(Class<?> type) {
		String name = type.getCanonicalName();
		if (null == name) {
			name = type.getName();
		}
		name = Clean$$Name(name);
		return name;
	}

	/**
	 * 清除动态代理代码部分，如清除“com.ifugle.Test$$EnhancerByCGLIB$$...”为“com.ifugle.Test”
	 * 
	 * @param bean
	 * @return
	 */
	public static String getCanonicalName(Object bean) {
		if (null != bean) {
			return getCanonicalName(bean.getClass());
		}
		return null;
	}

	/**
	 * 清除动态代理代码部分，如清除“Test$$EnhancerByCGLIB$$...”为“Test”
	 * 
	 * @param type
	 * @return
	 */
	public static String getSimpleName(Class<?> type) {
		return Clean$$Name(type.getSimpleName());
	}

	/**
	 * 清除动态代理代码部分，如清除“Test$$EnhancerByCGLIB$$...”为“Test”
	 * 
	 * @param bean
	 * @return
	 */
	public static String getSimpleName(Object bean) {
		if (null != bean) {
			return getSimpleName(bean.getClass());
		}
		return null;
	}

	/**
	 * 
	 * @param className
	 * @param loader
	 *            class loader from which the class must be loaded
	 * @return 永远不会返回null
	 * @throws ClassNotFoundException
	 */
	private static Class<?> loadClass(String className, ClassLoader loader) throws ClassNotFoundException {
		return Class.forName(className, true, loader);
	}

	/**
	 * 
	 * @param className
	 * @return 永远不会返回null
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		return loadClass(className, getClassLoader(getCallerClass(1)));
	}

	/**
	 * 
	 * @param classNames
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?>[] loadClasses(String... classNames) throws ClassNotFoundException {
		if (null != classNames) {
			int len = classNames.length;
			Class<?>[] types = new Class<?>[len];
			if (len > 0) {
				ClassLoader loader = getClassLoader(getCallerClass(1));
				for (int i = 0; i < len; ++i) {
					types[i] = Class.forName(classNames[i], true, loader);
				}
			}
			return types;
		}
		return null;
	}

	/**
	 * @param className
	 * @param loader
	 * @return
	 */
	public static boolean isPresent(String className, ClassLoader loader) {
		try {
			Class.forName(className, true, loader);
			return true;
		} catch (Throwable ignore) {
			return false;
		}
	}

	/**
	 * @param className
	 * @return
	 */
	public static boolean isPresent(String className) {
		return isPresent(className, getClassLoader(getCallerClass(1)));
	}

	/**
	 * 
	 * @param objs
	 * @return
	 */
	public static Class<?>[] getClasses(Object... objs) {
		if (null != objs) {
			int len = objs.length;
			Class<?>[] types = new Class<?>[len];
			if (len > 0) {
				for (int i = 0; i < len; ++i) {
					if (null != objs[i]) {
						types[i] = objs[i].getClass();
					} else {
						types[i] = null;
					}
				}
			}
			return types;
		}
		return null;
	}

	/**
	 * Add a package name prefix if the name is not absolute Remove leading "/" if name is absolute
	 * 
	 * @param name
	 * @param caller
	 * @return
	 */
	private static String resolveName(String name, Class<?> caller) {
		if (name == null) {
			return name;
		}
		if (name.charAt(0) != '/') {
			while (caller.isArray()) {
				caller = caller.getComponentType();
			}
			String baseName = getCanonicalName(caller);
			int index = baseName.indexOf('[');
			if (index > 0) {
				baseName = baseName.substring(0, index);
			}
			index = baseName.lastIndexOf('.');
			if (index != -1) {
				name = baseName.substring(0, index + 1).replace('.', '/') + name;
			}
		} else {
			name = name.substring(1);
		}
		return name;
	}

	/**
	 * 此方法改变了 ClassLoader.getResource() 方法默认返回第一个发现资源的规则，新规则优先顺序如下：
	 * <ul>
	 * <li>第一个未打包在 jar 文件中的资源优先返回</li>
	 * <li>第一个与调用此方法的类打包在相同 jar 文件的资源优先返回</li>
	 * <li>第一个资源优先返回</li>
	 * </ul>
	 * 
	 * @param name
	 * @param caller
	 * @return
	 */
	public static URL getResource(String name, Class<?> caller) {
		ClassLoader loader = getClassLoader(caller);
		name = resolveName(name, caller);
		try {
			Enumeration<URL> resources;
			if (null != loader) {
				resources = loader.getResources(name);
			} else {
				resources = ClassLoader.getSystemResources(name);
			}

			URL resource = null;
			List<URL> urls = null;
			for (; resources.hasMoreElements();) {
				if (null != resource) {
					if (null == urls) {
						urls = new ArrayList<>();
					}
					urls.add(resource);
				}
				resource = resources.nextElement();
			}
			if (null == urls) {
				return resource;
			} else if (null != resource) {
				urls.add(resource);
			}

			String resJarPath;
			URL resJarUrl = null;
			String callerJarPath = URLUtil.getJarPath(findClassURL(caller));
			for (URL url : urls) {
				resJarPath = URLUtil.getJarPath(url);
				if (null == resJarPath) {
					return url;
				} else if (null != callerJarPath && null == resJarUrl //
						&& callerJarPath.equalsIgnoreCase(resJarPath)) {
					resJarUrl = url;
				}
			}
			if (null != resJarUrl) {
				return resJarUrl;
			} else {
				return urls.get(0);
			}
		} catch (IOException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * 此方法改变了 ClassLoader.getResource() 方法默认返回第一个发现资源的规则，新规则优先顺序如下：
	 * <ul>
	 * <li>第一个未打包在 jar 文件中的资源优先返回</li>
	 * <li>第一个与调用此方法的类打包在相同 jar 文件的资源优先返回</li>
	 * <li>第一个资源优先返回</li>
	 * </ul>
	 * 
	 * @param name
	 * @return
	 */
	public static URL getResource(String name) {
		return getResource(name, getCallerClass(1));
	}

	/**
	 * @param name
	 * @param caller
	 * @return
	 */
	public static Enumeration<URL> getResources(String name, Class<?> caller) {
		ClassLoader loader = getClassLoader(caller);
		name = resolveName(name, caller);
		try {
			if (null != loader) {
				return loader.getResources(name);
			} else {
				return ClassLoader.getSystemResources(name);
			}
		} catch (IOException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * @param name
	 * @return
	 */
	public static Enumeration<URL> getResources(String name) {
		return getResources(name, getCallerClass(1));
	}

	/**
	 * 此方法改变了 ClassLoader.getResourceAsStream() 方法默认返回第一个发现资源的规则，新规则优先顺序如下：
	 * <ul>
	 * <li>第一个未打包在 jar 文件中的资源优先返回</li>
	 * <li>第一个与调用此方法的类打包在相同 jar 文件的资源优先返回</li>
	 * <li>第一个资源优先返回</li>
	 * </ul>
	 * 
	 * @param name
	 * @param caller
	 * @return
	 */
	public static InputStream getResourceAsStream(String name, Class<?> caller) {
		URL url = getResource(name, caller);
		if (null != url) {
			try {
				return url.openStream();
			} catch (IOException ignore) {
			}
		}
		return null;
	}

	/**
	 * 此方法改变了 ClassLoader.getResourceAsStream() 方法默认返回第一个发现资源的规则，新规则优先顺序如下：
	 * <ul>
	 * <li>第一个未打包在 jar 文件中的资源优先返回</li>
	 * <li>第一个与调用此方法的类打包在相同 jar 文件的资源优先返回</li>
	 * <li>第一个资源优先返回</li>
	 * </ul>
	 * 
	 * @param name
	 * @return
	 */
	public static InputStream getResourceAsStream(String name) {
		return getResourceAsStream(name, getCallerClass(1));
	}

	/**
	 * 
	 * @param className
	 * @param caller
	 * @return
	 */
	public static URL findClassURL(String className, Class<?> caller) {
		try {
			Class<?> type = loadClass(className, getClassLoader(caller));
			StringBuilder sb = new StringBuilder(className.length() + 16);
			sb.append('/').append(className.replace('.', '/')).append(".class");
			return type.getResource(sb.toString());
		} catch (ClassNotFoundException ignore) {
		}
		return null;
	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	public static URL findClassURL(String className) {
		return findClassURL(className, getCallerClass(1));
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static URL findClassURL(Class<?> type) {
		String className = getCanonicalName(type);
		int index = className.indexOf('[');
		if (index > 0) {
			className = className.substring(0, index);
		}
		StringBuilder sb = new StringBuilder(className.length() + 16);
		sb.append('/').append(className.replace('.', '/')).append(".class");
		return type.getResource(sb.toString());
	}

	/**
	 * 
	 * @param sb
	 * @param className
	 * @param methodName
	 * @param parameterTypes
	 */
	public static void buildMethodMessage(StringBuilder sb, String className, String methodName, Class<?>... parameterTypes) {
		sb.append(className).append('.').append(methodName).append('(');
		if (null != parameterTypes) {
			String name;
			int len = parameterTypes.length;
			for (int i = 0; i < len; ++i) {
				if (i > 0) {
					sb.append(", ");
				}
				Class<?> clazz = parameterTypes[i];
				if (null == clazz) {
					sb.append("null");
				} else {
					name = clazz.getCanonicalName();
					if (name.startsWith("java.")) {
						name = clazz.getSimpleName();
					}
					sb.append(name);
				}
			}
		}
		sb.append(')');
	}

	/**
	 * 
	 * @param sb
	 * @param className
	 * @param methodName
	 * @param textLimit
	 * @param args
	 */
	public static void buildMethodMessage(StringBuilder sb, String className, String methodName, int textLimit, Object... args) {
		sb.append(className).append('.').append(methodName).append('(');
		if (null != args) {
			Object arg;

			int len = args.length;
			for (int i = 0; i < len; ++i) {
				if (i > 0) {
					sb.append(", ");
				}
				arg = args[i];
				if (arg instanceof String) {
					sb.append(StringUtil.doubleQuotes(StringUtil.valueOf(arg, textLimit)));
				} else {
					sb.append(StringUtil.valueOf(arg, textLimit));
				}
			}
		}
		sb.append(')');
	}

	/**
	 * 
	 * @param type
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @see Class#getMethod
	 * @see Class#getDeclaredMethod
	 * @see Method#setAccessible(boolean)
	 */
	public static Method getMethod(Class<?> type, String methodName, Class<?>... parameterTypes) {
		try {
			Method method;
			try {
				method = type.getMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException ignore) {
				for (Class<?> superType = type;;) {
					try {
						method = superType.getDeclaredMethod(methodName, parameterTypes);
						break;
					} catch (NoSuchMethodException again) {
						superType = superType.getSuperclass();
						if (null == superType) {
							throw again;
						}
					}
				}
			}
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			return method;
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("getMethod(): ");
			buildMethodMessage(sb, getCanonicalName(type), methodName, parameterTypes);
			throw new UtilException(sb.toString(), e);
		}
	}

	/**
	 * 
	 * @param type
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @see Class#getMethod
	 */
	public static boolean hasMethod(Class<?> type, String methodName, Class<?>... parameterTypes) {
		try {
			Method method = type.getMethod(methodName, parameterTypes);
			return (method != null);
		} catch (NoSuchMethodException | SecurityException ignore) {
		}
		return false;
	}

	/**
	 * 
	 * @param type
	 * @param fieldName
	 * @return
	 */
	public static boolean hasField(Class<?> type, String fieldName) {
		try {
			Field field = type.getField(fieldName);
			return (field != null);
		} catch (NoSuchFieldException | SecurityException ignore) {
		}
		return false;
	}

	/**
	 * @param type
	 * @param fieldName
	 * @return
	 * @see Class#getField
	 * @see Class#getDeclaredField
	 * @see Field#setAccessible(boolean)
	 */
	public static Field getField(Class<?> type, String fieldName) {
		try {
			Field field;
			try {
				field = type.getField(fieldName);
			} catch (NoSuchFieldException ignore) {
				for (Class<?> superType = type;;) {
					try {
						field = superType.getDeclaredField(fieldName);
						break;
					} catch (NoSuchFieldException again) {
						superType = superType.getSuperclass();
						if (null == superType) {
							throw again;
						}
					}
				}
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
			}
			int modifiers = field.getModifiers();
			if (Modifier.isFinal(modifiers)) {
				// 为允许 setFieldValue 在这里预先去除 final 属性。
				// 注意调用 field.get() 方法会缓存诸如 final 等访问属性，因此在 field.set() 方法去除 final 属性已晚矣！
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, (modifiers & ~Modifier.FINAL));
			}
			return field;
		} catch (Exception e) {
			throw new UtilException("getField({}, \"{}\")", getCanonicalName(type), fieldName, e);
		}
	}

	/**
	 * @param field
	 *            a static field
	 * @return
	 * @see Class#getField
	 * @see Class#getDeclaredField
	 * @see Field#setAccessible(boolean)
	 */
	public static Object getFieldValue(Field field) {
		if (Modifier.isStatic(field.getModifiers())) {
			try {
				return field.get(null);
			} catch (Exception e) {
				throw new UtilException("getFieldValue({}.{})", getCanonicalName(field.getType()), field.getName(), e);
			}
		} else {
			throw new UtilException("getFieldValue({}.{}), is not a static field.", getCanonicalName(field.getType()), field.getName());
		}
	}

	/**
	 * @param type
	 * @param fieldName
	 *            a static field
	 * @return
	 * @see Class#getField
	 * @see Class#getDeclaredField
	 * @see Field#setAccessible(boolean)
	 */
	public static Object getFieldValue(Class<?> type, String fieldName) {
		return getFieldValue(getField(type, fieldName));
	}

	/**
	 * @param field
	 *            a static field
	 * @param value
	 * @see Class#getField
	 * @see Class#getDeclaredField
	 * @see Field#setAccessible(boolean)
	 */
	public static void setFieldValue(Field field, Object value) {
		int mod = field.getModifiers();
		if (Modifier.isStatic(mod)) {
			try {
				field.set(null, value);
			} catch (Exception e) {
				throw new UtilException("setFieldValue({}.{}, {})", getCanonicalName(field.getType()), field.getName(), value, e);
			}
		} else {
			throw new UtilException("setFieldValue({}.{}, {}), is not a static field.", getCanonicalName(field.getType()), field.getName(), value);
		}
	}

	/**
	 * @param type
	 * @param fieldName
	 *            a static field
	 * @param value
	 * @see Class#getField
	 * @see Class#getDeclaredField
	 * @see Field#setAccessible(boolean)
	 */
	public static void setFieldValue(Class<?> type, String fieldName, Object value) {
		setFieldValue(getField(type, fieldName), value);
	}

}
