package net.tusdasa.webchat.util;

import java.util.UUID;

public class Uid {

	public static String getuid() {
		int uuid = UUID.randomUUID().toString().hashCode();
		if (uuid < 0) {
			uuid = -uuid;
		}
		String s = String.valueOf(uuid);
		return s;
	}
}
