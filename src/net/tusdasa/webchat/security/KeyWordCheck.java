package net.tusdasa.webchat.security;

public class KeyWordCheck {

	private static String[] key = { "<", ">", "&", "'", "\"", "..", ";", "%", "#", "exec", "insert", "drop", "delete",
			"update", "and", "or" };

	/**
	 * 
	 * @param str
	 *            ��Ҫ���滻���ַ���
	 */
	public static void Check(String str) {
		// ����Σ�յĹؼ���
		String[] key = { "<", ">", "&", "'", "\"", "..", ";", "%", "#", "exec", "insert", "drop", "delete", "update",
				"and", "or" };
		// ͳһתΪСд
		str = str.toUpperCase();
		// �滻
		for (int i = 0; i < key.length; i++) {
			str.replace(key[i], "");
		}
	}

	/**
	 * 
	 * 
	 * @param str
	 *            ��Ҫ���滻���ַ���
	 * @param key
	 *            �ؼ�������
	 */

	public static String allCheck(String str, String[] key) {
		// ͳһתΪСд
		str = str.toUpperCase();
		// �滻
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
