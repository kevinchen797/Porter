package cn.cmy.cmynote.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.cmy.cmynote.dao.IUserDao;
import cn.cmy.cmynote.dao.impl.UserDaoImpl;
import cn.cmy.cmynote.util.GlobalConsts;

public class LoginActivity extends Activity {

	@ViewInject(R.id.login)
	private LinearLayout login;
	@ViewInject(R.id.etLoginUsername)
	private EditText etUName;
	@ViewInject(R.id.etLoginPassword)
	private EditText etUPwd;
	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;
	@ViewInject(R.id.btRegister)
	private Button btnLogin;
	@ViewInject(R.id.btReset)
	private Button btnReset;
	@ViewInject(R.id.regist_btn)
	private ImageButton btnRegist;
	@ViewInject(R.id.tvRegist)
	private TextView tvRegist;
	@ViewInject(R.id.tvForgetpwd)
	private TextView tvForgetpwd;

	private IUserDao dao;

	private SharedPreferences sp;
	private int color;				//当前皮肤颜色
	
	String username;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		x.view().inject(this);
		dao = new UserDaoImpl();
		
		sp = getSharedPreferences("notesetting", 0);
		color=sp.getInt("color", getResources().getColor(R.color.blue));
		login.setBackgroundColor(color);
		setListener();
	}

	private void setListener() {
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean ischeck = checkInput();
				if (ischeck) {
					dao.UserLogin(username, password, new AsyncCallback() {
						
						@Override
						public void onSuccess(Object success) {
							showToast((String) success);
							sp.edit().putInt("isLogin", GlobalConsts.ISLOGIN_TRUE).commit();
							sp.edit().putString("username", username).commit();
							Intent data = new Intent();
							data.putExtra("username", username);
							setResult(GlobalConsts.RESULT_LOGIN_SUCCESS, data);
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
		
		btnRegist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
				startActivity(intent);
			}
		});
		
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				etUName.setText("");
				etUPwd.setText("");
			}
		});
		
		tvRegist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
				startActivity(intent);
			}
		});
		
		tvForgetpwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showToast("亲~此功能暂未开放");
			}
		});
	}

	private void back() { // 返回主界面
		finish();
	}

	private boolean checkInput() {
		username = etUName.getText().toString();
		password = etUPwd.getText().toString();
		if ("".equals(username) || "".equals(password)) {
			if ("".equals(username)) {
				showToast("请输入用户名！");
				return false;
			} else {
				showToast("请输入密码！");
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
