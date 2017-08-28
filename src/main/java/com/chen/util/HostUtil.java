/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package com.chen.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @since Jul 7, 2016 4:21:47 PM
 * @version $Id: HostUtil.java 18816 2016-07-14 05:17:33Z WuJianqiang $
 * @author WuJianqiang
 *
 */
public final class HostUtil {
	public static final String LOCAL_HOST_IPV4 = "127.0.0.1";
	public static final String LOCAL_HOST_IPV6 = "::1";
	private static final String LOCAL_HOST_IPV6_LONG = "0:0:0:0:0:0:0:1";
	private static final String LOCAL_HOST_IPV6_IPV4 = "::ffff:7f00:1";
	private static final String LOCAL_HOST_IPV6_SUFFIX = ":1";
	private static final int REACHABLE_CHECK_TIMEOUT = 3000;
	private static final int LOCAL_HOST_CACHE_UPPERBOUND = 4;
	private static volatile String[] LOCAL_HOST_NAMES = new String[LOCAL_HOST_CACHE_UPPERBOUND + 1];
	private static volatile String[] LOCAL_HOST_ADDRS = new String[LOCAL_HOST_CACHE_UPPERBOUND + 1];

	// Prevent instantiation
	private HostUtil() {
		super();
	}

	/**
	 * @return
	 */
	public static InetAddress getLocalHost() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return InetAddress.getLoopbackAddress();
		}
	}

	/**
	 * @param ipRightParts
	 * @return
	 */
	public static String getLocalHostName(int ipRightParts) {
		if (ipRightParts > LOCAL_HOST_CACHE_UPPERBOUND) {
			ipRightParts = LOCAL_HOST_CACHE_UPPERBOUND;
		} else if (ipRightParts < 0) {
			ipRightParts = 0;
		}
		String result = LOCAL_HOST_NAMES[ipRightParts];
		if (null == result) {
			synchronized (LOCAL_HOST_NAMES) {
				InetAddress localHost = getLocalHost();
				result = getHostName(localHost, ipRightParts);
				LOCAL_HOST_NAMES[ipRightParts] = result;
			}
		}
		return result;
	}

	/**
	 * @return
	 */
	public static String getLocalHostName() {
		return getLocalHostName(0);
	}

	/**
	 * @return
	 */
	public static String getLocalHostName2() {
		return getLocalHostName(2);
	}

	/**
	 * @return
	 */
	public static String getLocalHostName4() {
		return getLocalHostName(4);
	}

	/**
	 * @param ipRightParts
	 * @return
	 */
	public static String getLocalHostAddress(int ipRightParts) {
		if (ipRightParts > LOCAL_HOST_CACHE_UPPERBOUND) {
			ipRightParts = LOCAL_HOST_CACHE_UPPERBOUND;
		} else if (ipRightParts < 0) {
			ipRightParts = 0;
		}
		String result = LOCAL_HOST_ADDRS[ipRightParts];
		if (null == result) {
			synchronized (LOCAL_HOST_ADDRS) {
				InetAddress localHost = getLocalHost();
				result = getHostAddress(localHost, ipRightParts);
				LOCAL_HOST_ADDRS[ipRightParts] = result;
			}
		}
		return result;
	}

	/**
	 * @return
	 */
	public static String getLocalHostAddress() {
		return getLocalHostAddress(0);
	}

	/**
	 * @return
	 */
	public static String getLocalHostAddress1() {
		return getLocalHostAddress(1);
	}

	/**
	 * @return
	 */
	public static String getLocalHostAddress2() {
		return getLocalHostAddress(2);
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	public static String getHostName(InetAddress host, int ipRightParts) {
		String hostName = host.getHostName();
		if (ipRightParts > 0) {
			String hostAddress = host.getHostAddress();
			if (!hostName.equals(hostAddress)) {
				hostAddress = getHostAddress(host, ipRightParts);
				StringBuilder sb = new StringBuilder(hostName.length() + hostAddress.length() + 2);
				sb.append(hostName).append('(').append(hostAddress).append(')');
				hostName = sb.toString();
			}
		}
		return hostName;
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	public static String getHostName(InetAddress host) {
		return getHostName(host, 0);
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	public static String getHostName2(InetAddress host) {
		return getHostName(host, 2);
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	public static String getHostName4(InetAddress host) {
		return getHostName(host, 4);
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	public static String getHostAddress(InetAddress host, int ipRightParts) {
		if (host instanceof Inet4Address) {
			return getHostAddress((Inet4Address) host, ipRightParts);
		} else {
			return getHostAddress((Inet6Address) host, ipRightParts);
		}
	}

	/**
	 * @param host
	 * @return
	 */
	public static String getHostAddress(InetAddress host) {
		return getHostAddress(host, 0);
	}

	/**
	 * @param host
	 * @return
	 */
	public static String getHostAddress1(InetAddress host) {
		return getHostAddress(host, 1);
	}

	/**
	 * @param host
	 * @return
	 */
	public static String getHostAddress2(InetAddress host) {
		return getHostAddress(host, 2);
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	private static String getHostAddress(Inet4Address host, int ipRightParts) {
		StringBuilder sb = new StringBuilder(16);
		byte[] address = host.getAddress();
		int len = address.length;
		int i = ((ipRightParts <= 0 || ipRightParts >= len) ? 0 : len - ipRightParts);
		for (; i < len; ++i) {
			if (sb.length() > 0) {
				sb.append('.');
			}
			sb.append((address[i] & 0xff));
		}
		return sb.toString();
	}

	/**
	 * @param host
	 * @param ipRightParts
	 * @return
	 */
	private static String getHostAddress(Inet6Address host, int ipRightParts) {
		StringBuffer sb = new StringBuffer(39);
		byte[] address = host.getAddress();
		int len = address.length / 2;
		int i = ((ipRightParts <= 0 || ipRightParts >= len) ? 0 : len - ipRightParts);
		for (; i < len; ++i) {
			if (sb.length() > 0) {
				sb.append(':');
			}
			sb.append(Integer.toHexString(((address[i << 1] << 8) & 0xff00) | (address[(i << 1) + 1] & 0xff)));
		}
		return sb.toString();
	}

	/**
	 * 优先获取能够到达 remote 主机的本地地址，适用可能存在多个本机地址的情况。
	 * 
	 * @param remote
	 *            the remote host.
	 * @param ttl
	 *            the maximum numbers of hops to try or 0 for the default
	 * @param timeout
	 *            the time, in milliseconds, before the call aborts
	 * @return
	 */
	public static InetAddress getLocalHost(InetAddress remote, int ttl, int timeout) {
		InetAddress localHost = getLocalHost();
		try {
			NetworkInterface netif;
			InetAddress[] addresses = Inet4Address.getAllByName(localHost.getHostName());
			/**
			 * @see InetAddress#isReachable(NetworkInterface, int, int)
			 * 
			 *      A typical implementation will use ICMP ECHO REQUESTs if the privilege can be
			 *      obtained, otherwise it will try to establish a TCP connection on port 7 (Echo)
			 *      of the destination host.
			 */
			for (InetAddress address : addresses) {
				try {
					netif = NetworkInterface.getByInetAddress(address);
					// TODO 返回同网段检查
					if (remote.isReachable(netif, ttl, timeout)) {
						return address;
					}
				} catch (SocketException ignore) {
				} catch (IOException ignore) {
				}
			}
		} catch (UnknownHostException ignore) {
		}
		return localHost;
	}

	/**
	 * 优先获取能够到达 remote 主机的本地地址，适用可能存在多个本机地址的情况。
	 * 
	 * @param remote
	 *            the remote host.
	 * @return
	 */
	public static InetAddress getLocalHost(InetAddress remote) {
		return getLocalHost(remote, 0, REACHABLE_CHECK_TIMEOUT);
	}

	/**
	 * 优先获取能够到达 remote 主机的本地地址，适用可能存在多个本机地址的情况。
	 * 
	 * @param remoteHost
	 *            the remote host.
	 * @param ttl
	 *            the maximum numbers of hops to try or 0 for the default
	 * @param timeout
	 *            the time, in milliseconds, before the call aborts
	 * @return
	 */
	public static InetAddress getLocalHost(String remoteHost, int ttl, int timeout) {
		try {
			InetAddress remote = InetAddress.getByName(remoteHost);
			return getLocalHost(remote, ttl, timeout);
		} catch (UnknownHostException ignore) {
			return getLocalHost();
		}
	}

	/**
	 * 优先获取能够到达 remote 主机的本地地址，适用可能存在多个本机地址的情况。
	 * 
	 * @param remoteHost
	 *            the remote host.
	 * @return
	 */
	public static InetAddress getLocalHost(String remoteHost) {
		return getLocalHost(remoteHost, 0, REACHABLE_CHECK_TIMEOUT);
	}

	/**
	 * @param hostName
	 * @param defaultAddress
	 * @return
	 */
	public static String getHostAddress(String hostName, String defaultAddress) {
		InetAddress host;
		try {
			host = InetAddress.getByName(hostName);
		} catch (UnknownHostException e) {
			if (null != defaultAddress && !defaultAddress.isEmpty()) {
				try {
					host = InetAddress.getByName(defaultAddress);
				} catch (UnknownHostException e1) {
					return defaultAddress;
				}
			} else {
				return hostName;
			}
		}
		return host.getHostAddress();
	}

	/**
	 * @param hostName
	 * @return
	 */
	public static String getHostAddress(String hostName) {
		return getHostAddress(hostName, null);
	}

	/**
	 * @param localAddress
	 * @return
	 */
	public static String getLocalHostIPv4Address(String localAddress) {
		if (null == localAddress || LOCAL_HOST_IPV4.equals(localAddress)) {
			localAddress = LOCAL_HOST_IPV4;
		} else if (localAddress.endsWith(LOCAL_HOST_IPV6_SUFFIX) //
				&& (LOCAL_HOST_IPV6.equals(localAddress) //
						|| LOCAL_HOST_IPV6_LONG.equals(localAddress) //
						|| LOCAL_HOST_IPV6_IPV4.equals(localAddress))) {
			localAddress = LOCAL_HOST_IPV4;
		}
		return localAddress;
	}

	/**
	 * @param localAddress
	 * @return
	 */
	public static boolean isLocalHostAddress(String localAddress) {
		if (null == localAddress) {
			return false;
		} else if (LOCAL_HOST_IPV4.equals(localAddress)) {
			return true;
		} else if (localAddress.endsWith(LOCAL_HOST_IPV6_SUFFIX) //
				&& (LOCAL_HOST_IPV6.equals(localAddress) //
						|| LOCAL_HOST_IPV6_LONG.equals(localAddress) //
						|| LOCAL_HOST_IPV6_IPV4.equals(localAddress))) {
			return true;
		} else {
			return false;
		}
	}

}
