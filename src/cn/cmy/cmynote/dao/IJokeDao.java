package cn.cmy.cmynote.dao;

public interface IJokeDao extends IDao {

	void getJoke(AsyncCallback callback);
}
