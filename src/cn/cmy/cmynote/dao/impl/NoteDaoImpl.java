package cn.cmy.cmynote.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.cmy.cmynote.dao.INoteDao;
import cn.cmy.cmynote.entity.Note;
import cn.cmy.cmynote.util.DBOpenHelper;
import cn.cmy.cmynote.util.Database;
import cn.cmy.cmynote.util.DateUtils;

public class NoteDaoImpl implements INoteDao {

	private Context context;

	public NoteDaoImpl(Context context) {
		this.context = context;
	}

	@Override
	public long savNote(Note note) {
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Database.Note.FIELD_TITLE, note.getTitle());
		values.put(Database.Note.FIELD_CONTENT, note.getContent());
		values.put(Database.Note.FIELD_DATE, note.getDate());
		long id = db.insert(Database.Note.TABLE_NAME, Database.Note.FIELD_ID, values);
		close(null, db);
		// 返回
		return id;

	}

	/**
	 * 释放资源
	 * 
	 * @param c
	 *            Cursor对象
	 * @param db
	 *            SQLiteDatabase对象
	 */
	private void close(Cursor c, SQLiteDatabase db) {
		if (c != null) {
			c.close();
			c = null;
		}
		if (db != null) {
			db.close();
			db = null;
		}
	}

	@Override
	public List<Note> queryAllNote(String[] columns, String whereClause, String[] whereArgs) {
		// 1. 获取SQLiteDatabase对象
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// 2.1. 准备必要数据
		List<Note> notes = new ArrayList<Note>();
		// 2.2. 执行，并获取结果
		String orderBy = Database.Note.FIELD_DATE + " desc";
		Cursor c = db.query(Database.Note.TABLE_NAME, columns, whereClause, whereArgs, null, null, orderBy);
		if (c.moveToFirst()) {
			for (; !c.isAfterLast(); c.moveToNext()) {
				Note note = new Note();
				note.set_id(c.getLong(c.getColumnIndex(Database.Note.FIELD_ID)));
				note.setTitle(c.getString(c.getColumnIndex(Database.Note.FIELD_TITLE)));
				note.setContent(c.getString(c.getColumnIndex(Database.Note.FIELD_CONTENT)));
				note.setDate(c.getLong(c.getColumnIndex(Database.Note.FIELD_DATE)));
				notes.add(note);
			}
		}
		// 2.3. 释放资源
		close(c, db);

		// 3. 返回结果
		return notes;
	}

	@Override
	public List<Note> queryAllNoteByKey(String key) {
		List<Note> notes = queryAllNote(null, null, null);
		List<Note> subNotes = new ArrayList<Note>();
		for (Note note : notes) {
			if (note.getTitle().contains(key) || note.getContent().contains(key)) {
				subNotes.add(note);
			}
		}
		return subNotes;
	}

	@Override
	public long deleteNote(long id) {
		// 1. 获取SQLiteDatabase对象
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

		// 2.1. 准备必要数据
		// (暂无)
		// 2.2. 执行，并获取结果
		int affectedRows = db.delete(Database.Note.TABLE_NAME, Database.Note.FIELD_ID + "=?", new String[] { id + "" });
		// 2.3. 释放资源
		close(null, db);

		// 3. 返回结果
		return affectedRows;
	}

	@Override
	public long updateNote(Note note) {
		// 1. 获取SQLiteDatabase对象
		DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

		// 2.1. 准备必要数据
		ContentValues values = new ContentValues();
		values.put(Database.Note.FIELD_DATE, System.currentTimeMillis());
		values.put(Database.Note.FIELD_CONTENT, note.getContent());
		String whereClause = Database.Note.FIELD_ID + "=?";
		String[] whereArgs = { note.get_id() + "" };
		// 2.2. 执行，并获取结果
		int affectedRows = db.update(Database.Note.TABLE_NAME, values, whereClause, whereArgs);
		// 2.3. 释放资源
		close(null, db);

		// 3. 返回结果
		return affectedRows;
	}

}
