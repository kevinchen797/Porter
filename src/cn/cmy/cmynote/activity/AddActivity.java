package cn.cmy.cmynote.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.dao.IFlyDao;
import cn.cmy.cmynote.dao.INoteDao;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.cmy.cmynote.dao.impl.FlyDaoImpl;
import cn.cmy.cmynote.dao.impl.NoteDaoImpl;
import cn.cmy.cmynote.entity.Note;

public class AddActivity extends Activity {

	@ViewInject(R.id.note_txt)
	private TextView tvNote;
	@ViewInject(R.id.title_txt)
	private TextView tvTitle;
	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;
	@ViewInject(R.id.save_btn)
	private ImageButton btnSave;
	@ViewInject(R.id.clear_btn)
	private Button btnClear;
	@ViewInject(R.id.delete_btn)
	private Button btnDelete;
	@ViewInject(R.id.length_txt)
	private TextView tvLength;
	@ViewInject(R.id.cloud_btn)
	private ImageButton btnCloud;

	private long note_id = 0;
	private String title;
	private String content;
	private long date = 0;

	private INoteDao dao;
	private IFlyDao fly;
	
	private SharedPreferences sp;
	private int color; // 当前皮肤颜色

	@ViewInject(R.id.add)
	private LinearLayout add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		x.view().inject(this);
		dao = new NoteDaoImpl(this);
		fly = new FlyDaoImpl();
		initData();// 还原未保存的数据

		sp = getSharedPreferences("notesetting", 0);
		color = sp.getInt("color", getResources().getColor(R.color.blue));
		add.setBackgroundColor(color);
		setListener();
	}

	/**
	 * 还原未保存的数据
	 */
	private void initData() {
		if (getIntent().hasExtra("title")) {
			Bundle data = getIntent().getExtras();
			if (data.containsKey("title"))
				tvTitle.setText(data.getString("title"));
			if (data.containsKey("content"))
				tvNote.setText(data.getString("content"));

		}

	}

	private void setListener() {

		tvNote.addTextChangedListener(changer);

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				back();
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				title = tvTitle.getText().toString();
				content = tvNote.getText().toString();
				if (!"".equals(content)) {

					date = System.currentTimeMillis();
					Note note = new Note();
					note.setTitle(title);
					note.setContent(content);
					note.setDate(date);
					long id = dao.savNote(note);

					// 4. 提示操作结果
					if (id > 0) {
						// 提示信息
						showToast("保存成功！");
						// 跳转到主界面, 更新ListView
						Intent intent = new Intent(AddActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						showToast("保存失败！");
					}
				} else {
					showToast("亲~写点什么吧");
				}
			}
		});

		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();
			}
		});

		btnCloud.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				letNoteFly();

			}
		});
	}

	private void letNoteFly() {
		String title = tvTitle.getText().toString();
		String content = tvNote.getText().toString();
		if ("".equals(content)) {
			showToast("亲 ~ 空心情无法帮您放飞哟！");
		} else {
			Note flynote = new Note(note_id, title, content, date);
			fly.letFly(flynote, new AsyncCallback() {
				@Override
				public void onSuccess(Object success) {
					showToast((String) success);
				}
				
				@Override
				public void onError(Object error) {
					showToast((String) error);
				}
			});
		}
	}
	
	private void clear() {
		View deleteView = View.inflate(AddActivity.this, R.layout.clear_layout, null);
		final Dialog clearDialog = new Dialog(AddActivity.this, R.style.dialog);
		clearDialog.setContentView(deleteView);
		Button yesBtn = (Button) deleteView.findViewById(R.id.delete_yes);
		yesBtn.setText(R.string.clear_note);
		Button noBtn = (Button) deleteView.findViewById(R.id.delete_no);
		noBtn.setText(R.string.clear_cancel);
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				tvTitle.setText("");
				tvNote.setText("");
				clearDialog.dismiss();
			}
		});
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				clearDialog.dismiss();
			}
		});
		clearDialog.show();

	}

	TextWatcher changer = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (s.length() > 0) {
				tvLength.setVisibility(View.VISIBLE);
				btnClear.setVisibility(View.VISIBLE);
				tvLength.setText(String.valueOf(s.length()));
			} else {
				tvLength.setVisibility(View.GONE);
				btnClear.setVisibility(View.GONE);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	private void back() { // 返回主界面
		Intent intent = new Intent(AddActivity.this, MainActivity.class);
		String title = tvTitle.getText().toString();
		String content = tvNote.getText().toString();
		if (title.length() > 0 || content.length() > 0) // 传递未保存的数据
		{
			Bundle data = new Bundle();
			data.putString("title", title);
			data.putString("content", content);
			intent.putExtras(data);
		}
		startActivity(intent);
		finish();
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
