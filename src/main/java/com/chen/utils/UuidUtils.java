package com.chen.utils;

import java.util.UUID;

public class UuidUtils {

	public static String generalUUID() {
		String str = UUID.randomUUID().toString();
		String[] uuids = { str.substring(0, 8), str.substring(9, 13), str.substring(14, 18), str.substring(19, 23) };
		String uuid = uuids[0] + uuids[1] + uuids[2];
		return uuid;
	}
}
