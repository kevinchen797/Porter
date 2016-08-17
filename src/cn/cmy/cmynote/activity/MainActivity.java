package cn.cmy.cmynote.activity;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.adapter.RecyclerviewAdapter;
import cn.cmy.cmynote.adapter.RecyclerviewAdapter.OnItemClickListener;
import cn.cmy.cmynote.dao.INoteDao;
import cn.cmy.cmynote.dao.impl.NoteDaoImpl;
import cn.cmy.cmynote.entity.Note;
import cn.cmy.cmynote.util.Database;
import cn.cmy.cmynote.util.GlobalConsts;
import cn.cmy.cmynote.widget.SlidingMenu;

public class MainActivity extends Activity {

	@ViewInject(R.id.add_btn)
	private ImageButton btnAdd;
	@ViewInject(R.id.menu_btn)
	private ImageButton btnMenu;
	@ViewInject(R.id.regist_btn)
	private ImageButton btnSearch;
	@ViewInject(R.id.search_txt)
	private EditText etSearch;
	@ViewInject(R.id.id_recyclerview)
	private RecyclerView recyclerview;
	private RecyclerviewAdapter madapter;

	@ViewInject(R.id.title_main)
	private TextView tvTitle;
	@ViewInject(R.id.ll_Search)
	private LinearLayout llSearch;
	@ViewInject(R.id.btnSearch)
	private Button btnSearchData;

	private List<Note> notes;
	private INoteDao dao;

	private boolean isHide;

	@ViewInject(R.id.id_menu)
	private SlidingMenu mLeftMenu;
	private SharedPreferences sp;
	private int color; // 当前皮肤颜色
	private int isLogin; // 当前是否登陆

	@ViewInject(R.id.menu)
	private RelativeLayout main;

	@ViewInject(R.id.menu_skin)
	private TextView tvSkin;
	@ViewInject(R.id.menu_pwd)
	private TextView tvPwd;
	@ViewInject(R.id.menu_feeling)
	private TextView tvFeel;
	@ViewInject(R.id.menu_help)
	private TextView tvHelp;
	@ViewInject(R.id.menu_about)
	private TextView tvAbout;
	@ViewInject(R.id.menu_exit)
	private TextView tvExit;

	@ViewInject(R.id.ivUserPhoto)
	private ImageView ivUserPhoto;
	@ViewInject(R.id.tvUserName)
	private TextView tvUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding);
		x.view().inject(this);
		// 初始化BmobSDK
		Bmob.initialize(this, "a3b8537a16b7d153c89906db341262eb");
		dao = new NoteDaoImpl(this);
		sp = getSharedPreferences("notesetting", 0);
		color = sp.getInt("color", getResources().getColor(R.color.blue));
		isLogin = sp.getInt("isLogin", GlobalConsts.ISLOGIN_FALSE);
		mLeftMenu.setBackgroundColor(color);
		main.setBackgroundColor(color);
		initData();
		setListener();

	}

	private void initData() {
		if (isLogin == GlobalConsts.ISLOGIN_TRUE) {
			String name = sp.getString("username", "yonghuming");
			tvUserName.setText(name);
		}
		notes = dao.queryAllNote(null, null, null);
		setAdapter();
	}

	private void setListener() {
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				if (getIntent().hasExtra("title"))
					intent.putExtras(getIntent().getExtras());
				startActivity(intent);
				finish();
			}
		});

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isHide) {
					llSearch.setVisibility(View.VISIBLE);
					isHide = true;
				} else {
					llSearch.setVisibility(View.GONE);
					isHide = false;
				}
			}

		});

		btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation rotateAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
				btnMenu.startAnimation(rotateAnim);
				mLeftMenu.toggle();

			}
		});

		btnSearchData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String key = etSearch.getText().toString().trim();
				if ("".equals(key)) {
					searchNoteByKey(null);
				} else {
					searchNoteByKey(key);
				}
			}
		});

		madapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void OnItemClick(View view, int position) {
				Note note = notes.get(position);
				Intent intent = new Intent(MainActivity.this, NoteActivity.class);
				intent.putExtra("position", position);
				intent.putExtra(Database.Note.FIELD_ID, note.get_id());
				intent.putExtra(Database.Note.FIELD_TITLE, note.getTitle());
				intent.putExtra(Database.Note.FIELD_CONTENT, note.getContent());
				startActivityForResult(intent, GlobalConsts.REQUESTCODE_NOTE_ACTIVITY);

			}

			@Override
			public void OnItemLongClick(View view, int position) {
				showToast("长按功能");

			}

		});

		tvSkin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SkinActivity.class);
				startActivityForResult(intent, GlobalConsts.REQUESTCODE_SKIN_ACTIVITY);
			}
		});

		tvAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});

		tvExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exitLogin();
			}
		});

		ivUserPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivityForResult(intent, GlobalConsts.REQUESTCODE_LOGIN_ACTIVITY);
			}
		});
	}

	private void exitLogin() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("警告！");
		builder.setMessage("您确定要退出登录吗?");
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				sp.edit().putInt("isLogin", GlobalConsts.ISLOGIN_FALSE).commit();
				Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	private void exit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("警告！");
		builder.setMessage("您确定要退出吗?");
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case GlobalConsts.REQUESTCODE_NOTE_ACTIVITY:
			switch (resultCode) {
			case GlobalConsts.RESULT_DELETE_OK:
				int position = data.getIntExtra("position", -1);
				madapter.delete(position);
				showToast("删除成功！");
				break;
			case GlobalConsts.RESULT_UPDATE_OK:
				// TODO 更新adapter
				showToast("亲~已经更新了。。。");
				break;
			}
			break;

		case GlobalConsts.REQUESTCODE_SKIN_ACTIVITY:
			switch (resultCode) {
			case GlobalConsts.RESULT_CHOOSE_SKIN:
				chooseSkin();
				break;

			case GlobalConsts.RESULT_SETTING_SKIN:
				settingSkin();
				break;
			}
			break;

		case GlobalConsts.REQUESTCODE_LOGIN_ACTIVITY:
			switch (resultCode) {
			case GlobalConsts.RESULT_LOGIN_SUCCESS:
				Bundle bundle = data.getExtras();
				String username = bundle.getString("username");
				tvUserName.setText(username);
				break;
			}
			break;
		}

	}

	// 自定义皮肤
	private void settingSkin() {
		Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
		startActivity(intent);
		finish();
	}

	// 选择系统皮肤
	private void chooseSkin() {
		Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
		startActivity(intent);
		finish();
	}

	private void searchNoteByKey(String key) {
		if (key == null) {
			List<Note> subNotes = dao.queryAllNote(null, null, null);
			notes.clear();
			notes.addAll(subNotes);
			madapter.notifyDataSetChanged();
		} else {
			List<Note> subNotes = dao.queryAllNoteByKey(key);
			notes.clear();
			notes.addAll(subNotes);
			madapter.notifyDataSetChanged();
		}
	}

	private void setAdapter() {

		madapter = new RecyclerviewAdapter(MainActivity.this, notes);
		recyclerview.setAdapter(madapter);
		recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
		recyclerview.setItemAnimator(new DefaultItemAnimator());
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		exit();
		return super.onKeyUp(keyCode, event);
	}

}
