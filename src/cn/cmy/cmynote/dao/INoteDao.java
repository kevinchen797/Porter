package cn.cmy.cmynote.dao;

import java.util.List;

import cn.cmy.cmynote.entity.Note;

public interface INoteDao {

	/**
	 * 保存日记
	 * @param note
	 */
	long savNote(Note note);
	
	/**
	 * 查询所有日记
	 * @return
	 */
	List<Note> queryAllNote(String[] columns, String whereClause, String[] whereArgs);
	
	/**
	 * 查询指定日记
	 * @return
	 */
	List<Note> queryAllNoteByKey(String key);
	
	/**
	 * 修改记事
	 * @param note
	 * @return
	 */
	long updateNote(Note note);
	
	/**
	 * 删除记事
	 * @param id
	 * @return
	 */
	long deleteNote(long id);
}
