package cn.cmy.cmynote.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "cmynote.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("info", "DBOpenHelper.onCreate()");
		// 当创建数据库时，创建出数据表
		String sql = "CREATE TABLE " + Database.Note.TABLE_NAME + " ("
			+ Database.Note.FIELD_ID + " integer primary key autoincrement, "
			+ Database.Note.FIELD_TITLE + " varchar(512), "
			+ Database.Note.FIELD_CONTENT + " varchar(1024) not null unique, "
			+ Database.Note.FIELD_DATE + " int4 not null unique"
			+ ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 当升级版本时
	}

}
