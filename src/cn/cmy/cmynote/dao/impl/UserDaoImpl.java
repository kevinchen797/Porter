package cn.cmy.cmynote.dao.impl;

import cn.cmy.cmynote.app.MyApplication;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.cmy.cmynote.dao.IUserDao;

public class UserDaoImpl implements IUserDao {

	@Override
	public void userRegister(String username, String password, String phonenumber, String email,
			final AsyncCallback callback) {
		BmobUser user = new BmobUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setMobilePhoneNumber(phonenumber);
		user.setEmail(email);
		user.signUp(MyApplication.getContext(), new SaveListener() {
			String msg = "注册成功";

			@Override
			public void onFailure(int arg0, String arg1) {
				callback.onError(arg1);
			}

			@Override
			public void onSuccess() {
				callback.onSuccess(msg);
			}
		});
	}

	@Override
	public void UserLogin(String username, String password, final AsyncCallback callback) {
		BmobUser user = new BmobUser();
		user.setUsername(username);
		user.setPassword(password);
		user.login(MyApplication.getContext(), new SaveListener() {
			String str = "欢迎回来";

			@Override
			public void onSuccess() {
				callback.onSuccess(str);
			}

			@Override
			public void onFailure(int arg0, String msg) {
				callback.onError(msg);
			}
		});
	}

}
