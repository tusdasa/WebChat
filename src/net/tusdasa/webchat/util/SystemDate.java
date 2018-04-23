package net.tusdasa.webchat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemDate {

	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String time = df.format(new Date());
		return time;
	}
}
