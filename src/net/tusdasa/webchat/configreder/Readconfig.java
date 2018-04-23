package net.tusdasa.webchat.configreder;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Iterator;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class Readconfig {
	private String xmlpath = "Config.xml";
	//// 默认连接地址
	private String db_adder;
	// 默认连接端口
	private String db_prot;
	// 连接用户名
	private String db_username;
	// 默认连接密码
	private String db_password;
	// 默认连接数据库
	private String db_dbname;

	private String db_redis;

	private String db_redis_port;

	/**
	 * 读取Config失败会抛出此异常
	 * 
	 * @throws DocumentException
	 */
	public Readconfig() throws DocumentException {
		this.setXmlpath(this.getClassPath() + "Config.xml");

		SAXReader reader = new SAXReader();

		Document document = reader.read(new File(this.xmlpath));

		Element root = document.getRootElement();

		for (Iterator<Element> iterator = root.elementIterator(); 
			iterator.hasNext();) {
			Element le = (Element) iterator.next();
			// System.out.println(le.getName()+" ------- "+le.getText());
			switch (le.getName()) {
			case "MySQL":
				this.setDb_adder(le.getText());
				break;
			case "Username":
				this.setDb_username(le.getText());
				break;
			case "Password":
				this.setDb_password(le.getText());
				break;
			case "DBname":
				this.setDb_dbname(le.getText());
				break;
			case "Port":
				this.setDb_prot(le.getText());
				break;
			case "Redis":
				this.setDb_redis(le.getText());
				break;
			case "RedisPort":
				this.setDb_redis_port(le.getText());
				break;
			default:
				break;
			}
		}
	}

	public String getDb_adder() {
		return db_adder;
	}

	public void setDb_adder(String db_adder) {
		this.db_adder = db_adder;
	}

	public String getDb_prot() {
		return db_prot;
	}

	public void setDb_prot(String db_prot) {
		this.db_prot = db_prot;
	}

	public String getDb_username() {
		return db_username;
	}

	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}

	public String getDb_password() {
		return db_password;
	}

	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}

	public String getDb_dbname() {
		return db_dbname;
	}

	public void setDb_dbname(String db_dbname) {
		this.db_dbname = db_dbname;
	}

	public String getXmlpath() {
		return xmlpath;
	}

	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}

	public String getDb_redis() {
		return db_redis;
	}

	public void setDb_redis(String db_redis) {
		this.db_redis = db_redis;
	}

	public String getDb_redis_port() {
		return db_redis_port;
	}

	public void setDb_redis_port(String db_redis_port) {
		this.db_redis_port = db_redis_port;
	}

	public String getClassPath() {
		String S;
		try {
			S = Readconfig.class.getClassLoader().getResource("").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			S = null;
		}
		return S;

	}

}
