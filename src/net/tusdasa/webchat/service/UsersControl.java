package net.tusdasa.webchat.service;

import java.sql.SQLException;

import org.dom4j.DocumentException;

import net.tusdasa.webchat.database.*;
import net.tusdasa.webchat.security.*;
import net.tusdasa.webchat.util.*;

/**
 * 
 * @author tusdasa 类名：Users 时间：2017年7月17日22:31:48 创建此类时会抛出 DocumentException
 *         调用方法是会抛出 SQLException
 */
public class UsersControl {
	//private db_MySQL db_mysql = new db_MySQL();
	private db_MySQL db_mysql;

	/**
	 * XML读入错误时
	 * 
	 * @throws DocumentException
	 */
	public UsersControl() throws DocumentException {
		DBCache dbCache=new DBCache();
		db_mysql=dbCache.getMySQL();
		/*
		Readconfig redconfig = new Readconfig();
		db_mysql.setDb_adder(redconfig.getDb_adder());
		db_mysql.setDb_prot(redconfig.getDb_prot());
		db_mysql.setDb_dbname(redconfig.getDb_dbname());
		db_mysql.setDb_username(redconfig.getDb_username());
		db_mysql.setDb_password(redconfig.getDb_password());
		*/
	}
	/*
	 * 系统和管理员的方法
	 * 
	 * 
	 */

	/**
	 * 
	 * @param username
	 *            新的最后登录时间
	 * @param password
	 *            用户的密码(明文)
	 * @return true 用户创建成功, 否则创建失败
	 * @throws SQLException
	 */
	public boolean newUsername(String username, String password) throws SQLException {
		boolean flag = false;
		String name_select = "SELECT uid FROM webchat WHERE username=?";
		String[] arr = { username };
		int ro = db_mysql.select_exist(name_select, 1, arr);
		if (ro == 0) {
			// 六个参数, uid,username,password,nickname,firsttime,status
			String crateuser = "INSERT INTO webchat (uid,username,password,nickname,firsttime,status) VALUES(?,?,?,?,?,?)";
			// 获得唯一用户标识符，用户名，加密后的密码，昵称(默认为用户名)，用户账号创建日期，账号状态(默认为启用)
			String[] arr1 = { Uid.getuid(), username, PasswordSafe.encode(password), username, SystemDate.getDate(),
					"1" };
			db_mysql.inster(crateuser, 6, arr1);
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param username
	 *            新的最后登录时间
	 * @param password
	 *            用户的密码(明文)
	 * @return true 用户创建成功, 否则创建失败
	 * @throws SQLException
	 */
	public void setPassword(String newpassword, String uid) throws SQLException {
		String sql = "UPDATE webchat SET password=? WHERE uid=?";
		String[] arr = { PasswordSafe.encode(newpassword).toString(), uid };
		this.db_mysql.update(sql, 2, arr);
	}

	/**
	 * 
	 * @param newnickname
	 *            用户的新昵称
	 * @param uid
	 *            用户标识符
	 * @throws SQLException
	 */
	public void setNickname(String newnickname, String uid) throws SQLException {
		String sql = "UPDATE webchat SET nickname=? WHERE uid=?";
		String[] arr = { newnickname, uid };
		this.db_mysql.update(sql, 2, arr);
	}

	/**
	 * 
	 * @param newlasttime
	 *            新的最后登录时间
	 * @param uid
	 *            用户标识符
	 * @throws SQLException
	 */
	public void resetLasttime(String newlasttime, String uid) throws SQLException {
		String sql = "UPDATE webchat SET lasttime=? WHERE uid=?";
		String[] arr = { newlasttime, uid };
		this.db_mysql.update(sql, 2, arr);
	}

	/**
	 * 
	 * @param newstatus
	 *            true/false 启用或禁用
	 * @param uid
	 *            用户标识符
	 * @throws SQLException
	 */
	public void resetStatus(Boolean newstatus, String uid) throws SQLException {
		int status;
		if (!newstatus) {
			status = 0;
		} else {
			status = 1;
		}
		String sql = "UPDATE webchat SET status=? WHERE uid=?";
		String[] arr = { String.valueOf(status), uid };
		this.db_mysql.update(sql, 2, arr);
	}

	// ------------------------------------------
	// 用户个人使用到的一些方法
	// 提供改密码, 改用户名, 改昵称
	// ------------------------------------------
	/**
	 * 
	 * @param newusername
	 *            新的用户名 会自动检查是否全局唯一
	 * @param uid
	 *            用户标识符
	 * @return 返回true 用户名唯一, 更改成功 否则不唯一更改失败
	 * @throws SQLException
	 */
	public Boolean resetUsername(String newusername, String uid) throws SQLException {
		boolean flag = false;
		String name_select = "SELECT uid FROM webchat WHERE username=?";
		String[] arr = { newusername };
		int ro = db_mysql.select_exist(name_select, 1, arr);
		if (ro == 0) {
			String sql = "UPDATE webchat SET username=? WHERE uid=?";
			String[] arr1 = { newusername, uid };
			this.db_mysql.update(sql, 2, arr1);
			flag = true;
		}

		return flag;
	}

	/**
	 * 
	 * @param newpassword
	 *            新密码字符串(明文) 会自动加密
	 * @param uid
	 *            用户标识符
	 * @throws SQLException
	 */

	public void resetPassword(String newpassword, String uid) throws SQLException {
		String sql = "UPDATE webchat SET password=? WHERE uid=?";
		String[] arr = { PasswordSafe.encode(newpassword).toString(), uid };
		this.db_mysql.update(sql, 2, arr);
	}

	/**
	 * 
	 * @param newnickname
	 *            新的用户昵称
	 * @param uid
	 *            用户标识符
	 * @throws SQLException
	 */

	public void resetNickname(String newnickname, String uid) throws SQLException {
		String sql = "UPDATE webchat SET nickname=? WHERE uid=?";
		String[] arr = { newnickname, uid };
		this.db_mysql.update(sql, 2, arr);
	}

}
