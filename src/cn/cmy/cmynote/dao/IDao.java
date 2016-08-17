package cn.cmy.cmynote.dao;

public interface IDao {

	/**
	 * 回调方法接口
	 */
	public interface AsyncCallback {

		void onSuccess(Object success);

		void onError(Object error);

	}
	
}
