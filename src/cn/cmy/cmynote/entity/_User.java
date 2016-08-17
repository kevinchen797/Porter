package cn.cmy.cmynote.entity;

import cn.bmob.v3.BmobObject;

public class _User extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private String username;
	private String password;
	private String mobilePhoneNumber;
	private String email;

	public _User() {
	}

	public _User(int user_id, String username, String password, String mobilePhoneNumber, String email) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.mobilePhoneNumber = mobilePhoneNumber;
		this.email = email;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "_User [user_id=" + user_id + ", username=" + username + ", password=" + password
				+ ", mobilePhoneNumber=" + mobilePhoneNumber + ", email=" + email + "]";
	}

}
