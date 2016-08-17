package cn.cmy.cmynote.dao;

import cn.cmy.cmynote.entity.Note;

public interface IFlyDao extends IDao {

	/**
	 * ·Å·ÉÐÄÇé
	 * @param note
	 */
	void letFly(Note note, AsyncCallback callback);
	
}
