package net.tusdasa.webchat.security;

public class KeyWordCheck {

	private static String[] key = { "<", ">", "&", "'", "\"", "..", ";", "%", "#", "exec", "insert", "drop", "delete",
			"update", "and", "or" };

	/**
	 * 
	 * @param str
	 *            需要被替换的字符串
	 */
	public static void Check(String str) {
		// 可能危险的关键字
		String[] key = { "<", ">", "&", "'", "\"", "..", ";", "%", "#", "exec", "insert", "drop", "delete", "update",
				"and", "or" };
		// 统一转为小写
		str = str.toUpperCase();
		// 替换
		for (int i = 0; i < key.length; i++) {
			str.replace(key[i], "");
		}
	}

	/**
	 * 
	 * 
	 * @param str
	 *            需要被替换的字符串
	 * @param key
	 *            关键字数组
	 */

	public static String allCheck(String str, String[] key) {
		// 统一转为小写
		str = str.toUpperCase();
		// 替换
		for (int i = 0; i < key.length; i++) {
			str.replace(key[i], "");
		}
		return str;
	}

	public static String[] getKey() {
		return key;
	}

	public static void setKey(String[] key) {
		KeyWordCheck.key = key;
	}

}
