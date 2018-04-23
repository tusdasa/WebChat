package net.tusdasa.webchat.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.*;

/**
 * 
 * @author tusdasa 类名:db_MySQL 功能:连接MySQL 删 增 改 查 时间: 2017年7月15日11:44:11
 */

public class db_MySQL {
	/**
	 * 默认连接地址
	 */
	private String db_adder = "";
	/**
	 * 连接数据库的端口
	 */
	private String db_prot = "";
	/**
	 * 连接用户名
	 */
	private String db_username = "";
	/**
	 * 连接密码
	 */
	private String db_password = "";
	/**
	 * 默认连接数据库
	 */
	private String db_dbname = "";
	/**
	 * 拼接的数据库地址
	 */
	

	/**
	 * 
	 * @param db_adder
	 *            通过传入的字符串设置的连接地址
	 */

	public void setDb_adder(String db_adder) {
		this.db_adder = db_adder;
	}

	/**
	 * 
	 * @param db_prot
	 *            通过传入的字符串设置的连接端口
	 */

	public void setDb_prot(String db_prot) {
		this.db_prot = db_prot;
	}

	/**
	 * 
	 * @param db_username
	 *            通过传入的字符串设置的连接用户名
	 */

	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}

	/**
	 * 
	 * @param db_password
	 *            通过传入的字符串设置的连接账户的密码
	 */

	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}

	/**
	 * 
	 * @param db_dbname
	 *            通过传入的字符串设置的连接的目标库名
	 */

	public void setDb_dbname(String db_dbname) {
		this.db_dbname = db_dbname;
	}

	/**
	 * 
	 * @return 载入失败 false 成功 true 用户无需关心此函数的成功失败, 打包会包含目标库
	 */

	private boolean getDriver() {
		boolean flag = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("Driver not found");
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @return
	 * @throws InfoError
	 * @throws SQLException
	 */

	private Connection MySQLConnection() throws SQLException {
		Connection connection = null;
		String URL = "jdbc:mysql://" + db_adder + ":" + db_prot + "/" + db_dbname;
		if (this.getDriver()) {
			connection = (Connection) DriverManager.getConnection(URL, this.db_username, this.db_password);
		} else {
			connection = null;
		}

		return connection;
	}

	/**
	 * 
	 * @param sql
	 *            传入查询SQL语句
	 * @param arg
	 *            SQL语句中的参数个数
	 * @param arr
	 *            参数数组
	 * @return list 结果集
	 * @throws @throws
	 *             Exception 1. SQLException
	 */
	public List<Map> select(String sql, int arg, String[] args) throws SQLException {
		Connection connection = this.MySQLConnection();
		ResultSet rs = null;
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
		//
		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
				// System.out.println(i);
				// System.out.println(args[i-1]);
			}
		}

		rs = (ResultSet) preparedStatement.executeQuery();
		/*
		 * if(args!=null) {
		 * System.out.println("select "+"arg Size:"+arg+"  Array Size:"+args.
		 * length+"  Query time:"+preparedStatement.getQueryTimeout()); }else {
		 * System.out.println("select "+"arg Size:"+arg+"  Array Size:"
		 * +"null"+"  Query time:"+preparedStatement.getQueryTimeout()); }
		 */
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();

		int columnCount = md.getColumnCount();

		List<Map> list = new ArrayList<Map>();

		Map<String, String> rowData = new HashMap<String, String>();

		while (rs.next()) {
			rowData = new HashMap<String, String>(columnCount);
			for (int k = 1; k <= columnCount; k++) {
				rowData.put(md.getColumnName(k), rs.getString(k));
			}
			list.add(rowData);

		}
		/*
		 * for(int j=0;j<list.size();j++) { System.out.println(list.get(j)); }
		 */
		this.closeSQL(rs, connection, preparedStatement);
		return list;
	}

	public String selectString(String sql, int arg, String[] args) throws SQLException {
		String rst="";
		Connection connection = this.MySQLConnection();
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
			}
		}

		ResultSet rs = (ResultSet) preparedStatement.executeQuery();
		while(rs.next()){
			rst=rs.getString(1);
		}
		return rst;
		
	}
	/**
	 * 
	 * @param sql
	 *            传入的插入语句
	 * @param arg
	 *            参数数量
	 * @param args
	 *            参数数组
	 * @return int 成功插入的条数
	 * @throws Exception
	 *             1. SQLException
	 */

	public int inster(String sql, int arg, String[] args) throws SQLException {
		int row = 0;
		Connection connection = this.MySQLConnection();
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
		// 鍙傛暟妫�鏌�
		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
				// System.out.println(i);
				// System.out.println(args[i-1]);
			}
		}

		row = preparedStatement.executeUpdate();
		// System.out.println("instaer "+" row "+row+" arg Size:"+arg+" Array
		// Size:"+args.length+" Query
		// time:"+preparedStatement.getQueryTimeout());
		this.closeSQL(null, connection, preparedStatement);
		return row;
	}

	/**
	 * 
	 * @param sql
	 *            传入的删除语句
	 * @param arg
	 *            参数数量
	 * @param args
	 *            参数数组
	 * @return int 成功删除的条数
	 * @throws Exception
	 *             1. SQLException
	 */

	public int delete(String sql, int arg, String[] args) throws SQLException {
		int row = 0;
		Connection connection = this.MySQLConnection();
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
		// 鍙傛暟妫�鏌�
		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
				// System.out.println(i);
				// System.out.println(args[i-1]);
			}
		}

		row = preparedStatement.executeUpdate();
		// System.out.println("delete "+" row "+row+" arg Size:"+arg+" Array
		// Size:"+args.length+" Query
		// time:"+preparedStatement.getQueryTimeout());
		this.closeSQL(null, connection, preparedStatement);
		return row;
	}

	/**
	 * 
	 * @param sql
	 *            传入的要改的语句
	 * @param arg
	 *            参数数量
	 * @param args
	 *            参数数组
	 * @return int 成功更改的条数
	 * @throws SQLException
	 *             1. SQLException
	 */

	public int update(String sql, int arg, String[] args) throws SQLException {
		int row = 0;
		Connection connection = this.MySQLConnection();
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
		//
		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
				// System.out.println(i);
				// System.out.println(args[i-1]);
			}
		}

		row = preparedStatement.executeUpdate();
		// System.out.println("update "+" row: "+row+" arg Size:"+arg+" Array
		// Size:"+args.length+" Query
		// time:"+preparedStatement.getQueryTimeout());
		this.closeSQL(null, connection, preparedStatement);
		return row;
	}

	/**
	 * 
	 * @param sql
	 *            查询语句
	 * @param arg
	 *            参数个数
	 * @param args
	 *            参数数组
	 * @return 数据库中的行数
	 * @throws SQLException
	 */

	public int select_exist(String sql, int arg, String[] args) throws SQLException {
		int row = 0;
		Connection connection = this.MySQLConnection();
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

		if (this.argcheck(arg) && this.argscheck(args)) {

			for (int i = 1; i < arg + 1; i++) {
				preparedStatement.setString(i, args[i - 1].toString());
			}
		}

		ResultSet rs = (ResultSet) preparedStatement.executeQuery();

		rs.last();
		row = rs.getRow();
		/*
		 * if(args!=null) {
		 * System.out.println("select_exist "+"arg Size:"+arg+"  Array Size:"
		 * +args.length+"  Query time:"+preparedStatement.getQueryTimeout());
		 * }else {
		 * System.out.println("select_exist "+"arg Size:"+arg+"  Array Size:"
		 * +"null"+"  Query time:"+preparedStatement.getQueryTimeout()); }
		 */
		this.closeSQL(rs, connection, preparedStatement);
		return row;
	}

	/**
	 * 
	 * @param arg
	 *            要检查的参数 大于等于0 小于等于99
	 * @return
	 */

	private boolean argcheck(int arg) {
		boolean flag = false;
		if (arg >= 0 && arg <= 99) {
			flag = true;

		}

		return flag;

	}

	/**
	 * 
	 * @param args
	 *            要检查的参数数组 数组在参数为零时可以为空 数组元素个数小于等于200
	 * @return Boolean 来判断是否检查成功
	 */

	private boolean argscheck(String[] args) {
		boolean flag = false;
		if (args == null) {
			flag = true;
		} else if (args.length > 0 && args.length <= 200) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param rs
	 *            关闭 ResultSet
	 * @param connection
	 *            关闭 Connection
	 * @param preparedStatement
	 *            关闭 PreparedStatement
	 * @throws SQLException
	 */

	private void closeSQL(ResultSet rs, Connection connection, PreparedStatement preparedStatement) {
		// boolean flag=false;
		try {
			if (rs == null) {
				preparedStatement.clearParameters();
				preparedStatement.close();
				connection.close();
				// flag=true;
			} else {
				rs.close();
				preparedStatement.clearParameters();
				preparedStatement.close();
				connection.close();

				// flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return flag;
	}

}
