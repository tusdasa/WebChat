package net.tusdasa.webchat.security;

public class XssCheck {

	public static String xss(String str) {
		str = str.replace("<srcipt>", "&lt;srcipt&gt;");
		str = str.replace("</script>", "&lt;/script&gt;");
		return str;
	}
}
