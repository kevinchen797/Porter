package cn.cmy.cmynote.dao.impl;

import cn.bmob.v3.listener.SaveListener;
import cn.cmy.cmynote.app.MyApplication;
import cn.cmy.cmynote.dao.IFlyDao;
import cn.cmy.cmynote.entity.Note;

public class FlyDaoImpl implements IFlyDao {

	@Override
	public void letFly(Note note, final AsyncCallback callback) {
		note.save(MyApplication.getContext(), new SaveListener() {
			String str = "¹§Ï²£¡³É¹¦·Å·É";
			@Override
			public void onSuccess() {
				callback.onSuccess(str);
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				callback.onError(arg1);				
			}
		});
	}

}
