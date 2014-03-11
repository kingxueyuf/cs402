package com.common.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Util {

	public static String getUUID() {
		UUID idOne = UUID.randomUUID();
		return idOne.toString();
	}

	public static String getAppPath() {
		String path = null;

		path = System.getProperty("catalina.base")+"";

		path="/shop_assistant2";
		return path;
	}
}
