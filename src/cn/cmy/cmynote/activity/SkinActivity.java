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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.util.ColorUtils;
import cn.cmy.cmynote.util.GlobalConsts;
import cn.cmy.cmynote.widget.ColorPickerDialog;

public class SkinActivity extends Activity {

	@ViewInject(R.id.skin)
	private LinearLayout skin;
	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;
	@ViewInject(R.id.skinColor0)
	private ImageView ivSkin0;
	@ViewInject(R.id.skinColor1)
	private ImageView ivSkin1;
	@ViewInject(R.id.skinColor2)
	private ImageView ivSkin2;
	@ViewInject(R.id.skinColor3)
	private ImageView ivSkin3;
	@ViewInject(R.id.skinColor4)
	private ImageView ivSkin4;
	@ViewInject(R.id.skinColor5)
	private ImageView ivSkin5;
	@ViewInject(R.id.skinColor6)
	private ImageView ivSkin6;
	@ViewInject(R.id.skinColor7)
	private ImageView ivSkin7;
	@ViewInject(R.id.skinColor8)
	private ImageView ivSkin8;
	@ViewInject(R.id.skinColor9)
	private ImageView ivSkin9;
	@ViewInject(R.id.btnSkinPersonal)
	private ImageButton btnSkinPersonal;

	private SharedPreferences sp;
	private int color;				//当前皮肤颜色
	private ColorPickerDialog cpDialog;		//颜色选择对话框
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosecolor);
		x.view().inject(this);
		sp = getSharedPreferences("notesetting", 0);
		color=sp.getInt("color", getResources().getColor(R.color.blue));
		skin.setBackgroundColor(color);
		setListener();
	}

	private void setListener() {

		ivSkin0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(0);
			}
		});

		ivSkin1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(1);
			}
		});

		ivSkin2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(2);
			}
		});

		ivSkin3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(3);
			}
		});

		ivSkin4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(4);
			}
		});

		ivSkin5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(5);
			}
		});

		ivSkin6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(6);
			}
		});

		ivSkin7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(7);
			}
		});

		ivSkin8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(8);
			}
		});

		ivSkin9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseColor(9);
			}
		});
		
		btnSkinPersonal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				settingSkin();
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void settingSkin() {
		  cpDialog = new ColorPickerDialog(SkinActivity.this, color,   
                getResources().getString(R.string.word_confirm),   
                new ColorPickerDialog.OnColorChangedListener() { 
            @Override  
            public void colorChanged(int c) 
            {  
				sp.edit().putInt("color", c).commit();
				Intent intent = new Intent(SkinActivity.this, MainActivity.class);
				setResult(GlobalConsts.RESULT_SETTING_SKIN, intent);
				finish();
            }
        });
		cpDialog.getWindow().setBackgroundDrawableResource(R.drawable.list_focused);
        cpDialog.show();
		
	}

	private void chooseColor(int position) {
		int newcolor = ColorUtils.colors[position];
		sp.edit().putInt("color", newcolor).commit();
		Intent intent = new Intent(SkinActivity.this, MainActivity.class);
		setResult(GlobalConsts.RESULT_CHOOSE_SKIN, intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) // 返回事件重写
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return false;
	}
}
