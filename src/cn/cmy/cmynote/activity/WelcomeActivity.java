package cn.cmy.cmynote.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.cmy.cmynote.dao.IJokeDao;
import cn.cmy.cmynote.dao.impl.JokeDaoImpl;
import cn.cmy.cmynote.widget.ColorPickerDialog;

public class WelcomeActivity extends Activity {

	@ViewInject(R.id.welcome)
	private LinearLayout welcome;		//布局
	@ViewInject(R.id.quote_txt)
	private TextView quoteTxt;			//引言标签
	private Handler welcomeHand;		//欢迎页停留 
	private Runnable welcomeShow;
	
	private IJokeDao dao;

	private SharedPreferences sp;
	private int color;				//当前皮肤颜色
	private ColorPickerDialog cpDialog;		//颜色选择对话框
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		x.view().inject(this);
		dao = new JokeDaoImpl();
		sp = getSharedPreferences("notesetting", 0);
		color=sp.getInt("color", getResources().getColor(R.color.blue));
		welcome.setBackgroundColor(color);
		
		initJoke();
		
		welcomeHand = new Handler();
		welcomeShow=new Runnable()
	    {
	        @Override
	        public void run()
	        {  
	        	welcome();
	        }
	    };
		welcomeHand.postDelayed(welcomeShow, 5000); 
		
	}
	
	private void initJoke() {
		dao.getJoke(new AsyncCallback() {
			
			@Override
			public void onSuccess(Object success) {
				String content = (String) success;
				quoteTxt.setTextColor(color);
				quoteTxt.setText(content);
			}
			
			@Override
			public void onError(Object error) {
			}
		});
		
		quoteTxt.setTextColor(color);
		quoteTxt.setText("cmy");
		
	}

	private void welcome() {
		Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
	}
}
