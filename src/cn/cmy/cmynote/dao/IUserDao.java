package cn.cmy.cmynote.dao;

import cn.cmy.cmynote.dao.IDao.AsyncCallback;

public interface IUserDao {

	/**
	 * 注册用户
	 */
	void userRegister(String username, String password, String phonenumber, String email, AsyncCallback callback);

	/**
	 * 用户登陆
	 */
	void UserLogin(String username, String password, AsyncCallback callback);
	
}
