package util;

import java.util.UUID;

public class Util {

	public static String getRandomId() {
		UUID idOne = UUID.randomUUID();
		return idOne.toString();
	}
}
