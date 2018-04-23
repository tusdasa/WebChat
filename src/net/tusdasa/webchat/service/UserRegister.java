package net.tusdasa.webchat.service;

import java.sql.SQLException;

import org.dom4j.DocumentException;

public class UserRegister {
	private UsersControl usersControl;

	public String register(String username, String password) {
		try {
			usersControl = new UsersControl();
			if (usersControl.newUsername(username, password)) {
				// 注册成功
				return "regist_success";
			} else {
				// 注册失败用户名重复
				return "username_exist";
			}

		} catch (DocumentException e) {

			return "config_error";
		} catch (SQLException e) {
			return "sql_error";
		}
	}

}
