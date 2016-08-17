package cn.cmy.cmynote.activity;


import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.dao.IDao.AsyncCallback;
import cn.cmy.cmynote.dao.IFlyDao;
import cn.cmy.cmynote.dao.INoteDao;
import cn.cmy.cmynote.dao.impl.FlyDaoImpl;
import cn.cmy.cmynote.dao.impl.NoteDaoImpl;
import cn.cmy.cmynote.entity.Note;
import cn.cmy.cmynote.util.Database;
import cn.cmy.cmynote.util.GlobalConsts;

public class NoteActivity extends Activity {

	@ViewInject(R.id.back_btn)
	private ImageButton btnBack;
	@ViewInject(R.id.title_note)
	private TextView tvNote_title;
	@ViewInject(R.id.confirm_btn)
	private ImageButton btnConfirm;
	@ViewInject(R.id.delete_btn)
	private ImageButton btnDelete;
	@ViewInject(R.id.note_txt)
	private TextView tvNote_content;
	@ViewInject(R.id.cloud_btn)
	private ImageButton btnCloud;

	private long note_id;
	private String note_title;
	private String note_content;
	private long note_date;
	private int position;

	private INoteDao dao;
	private IFlyDao fly;
	
	private SharedPreferences sp;
	private int color;				//当前皮肤颜色
	
	@ViewInject(R.id.note)
	private LinearLayout Note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		x.view().inject(this);
		dao = new NoteDaoImpl(this);
		fly = new FlyDaoImpl();
		sp = getSharedPreferences("notesetting", 0);
		color=sp.getInt("color", getResources().getColor(R.color.blue));
		Note.setBackgroundColor(color);
		Intent intent = getIntent();
		note_id = intent.getLongExtra(Database.Note.FIELD_ID, 0);
		note_title = intent.getStringExtra(Database.Note.FIELD_TITLE);
		note_content = intent.getStringExtra(Database.Note.FIELD_CONTENT);
		note_date = intent.getLongExtra("date", 0);
		position = intent.getIntExtra("position", -1);
		init();
		setListener();
	}

	private void init() {
		tvNote_title.setText(note_title);
		tvNote_content.setText(note_content);
	}

	private void setListener() {
		// 返回
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 删除Note
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteNote();
			}

		});
		// 修改Note
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String currentContent = tvNote_content.getText().toString();
				if (!note_content.equals(currentContent)) {
					Note note = new Note();
					note.setContent(currentContent);
					note.set_id(note_id);
					long affectedRows = dao.updateNote(note);
					if (affectedRows == 1) {
						// 通知主界面更新数据
						Intent intent = new Intent(NoteActivity.this, MainActivity.class);
						setResult(GlobalConsts.RESULT_UPDATE_OK, intent);
						finish();
					}
				} else {
					showToast("亲~无需更新哟。。。");
				}
			}
		});
		// 放飞心情
		btnCloud.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				letNoteFly();
				
			}
		});
	}

	private void letNoteFly() {
		String title = tvNote_title.getText().toString();
		String content = tvNote_content.getText().toString();
		if ("".equals(content)) {
			showToast("亲 ~ 空心情无法帮您放飞哟！");
		} else {
			Note flynote = new Note(note_id, title, content, note_date);
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
	
	private void deleteNote() {
		View deleteView = View.inflate(this, R.layout.deletenote_layout, null);
		final Dialog delDialog = new Dialog(this, R.style.dialog);
		delDialog.setContentView(deleteView);
		Button yesBtn = (Button) deleteView.findViewById(R.id.delete_yes);
		Button noBtn = (Button) deleteView.findViewById(R.id.delete_no);

		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				long affectedRows = dao.deleteNote(note_id);
				if (affectedRows == 1) {
					// 通知主界面更新数据
					Intent intent = new Intent(NoteActivity.this, MainActivity.class);
					intent.putExtra("position", position);
					setResult(GlobalConsts.RESULT_DELETE_OK, intent);
					finish();
				} else {
					showToast("删除失败！");
				}
			}
		});
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				delDialog.dismiss();
			}
		});
		delDialog.show();

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
