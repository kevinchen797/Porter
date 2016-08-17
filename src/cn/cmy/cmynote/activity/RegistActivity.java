package cn.cmy.cmynote.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.cmy.cmynote.dao.IUserDao;
import cn.cmy.cmynote.dao.impl.UserDaoImpl;

public class RegistActivity extends Activity {

	@ViewInject(R.id.regist)
	private LinearLayout regist;
	@ViewInject(R.id.etUsername)
	private EditText etRName;
	@ViewInject(R.id.etPassword)
	private EditText etRPwd;
	@ViewInject(R.id.etPhone)
	private EditText etRPhone;
	@ViewInject(R.id.etEmail)
	private EditText etREail;
	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;
	@ViewInject(R.id.btRegister)
	private Button btnRegister;

	private IUserDao dao;

	private SharedPreferences sp;
	private int color;				//当前皮肤颜色
	
	String username;
	String password;
	String phonenumber;
	String email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		x.view().inject(this);
		dao = new UserDaoImpl();
		
		sp = getSharedPreferences("notesetting", 0);
		color=sp.getInt("color", getResources().getColor(R.color.blue));
		regist.setBackgroundColor(color);
		setListener();
	}

	private void setListener() {
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back();
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean ischeck = checkInput();
				if (ischeck) {
					dao.userRegister(username, password, phonenumber, email, new AsyncCallback() {
						
						@Override
						public void onSuccess(Object success) {
							showToast((String) success);
							finish();
						}
						
						@Override
						public void onError(Object error) {
							showToast((String) error);
						}
					});
				}
			}
		});
	}

	private void back() { // 返回主界面
		finish();
	}

	private boolean checkInput() {
		username = etRName.getText().toString();
		password = etRPwd.getText().toString();
		phonenumber = etRPhone.getText().toString();
		email = etREail.getText().toString();
		if ("".equals(username) || "".equals(password)) {
			if ("".equals(username)) {
				showToast("用户名不可为空~");
				return false;
			} else {
				showToast("密码不可为空~");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) // 返回事件重写
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return false;
	}

	/**
	 * 使用Toast提示信息
	 * 
	 * @param msg
	 *            信息的内容
	 */
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
