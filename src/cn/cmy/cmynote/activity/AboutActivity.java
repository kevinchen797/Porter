package cn.cmy.cmynote.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import cn.cmy.cmynote.R;


public class AboutActivity extends Activity {

	@ViewInject(R.id.about)
	private LinearLayout about; // 布局
	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;

	private SharedPreferences sp;
	private int color; // 当前皮肤颜色

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		x.view().inject(this);
		sp = getSharedPreferences("notesetting", 0);
		color = sp.getInt("color", getResources().getColor(R.color.blue));
		about.setBackgroundColor(color);
		setListener();
	}

	private void setListener() {
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}
