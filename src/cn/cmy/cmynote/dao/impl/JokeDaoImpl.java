package cn.cmy.cmynote.dao.impl;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import cn.cmy.cmynote.dao.IJokeDao;
import cn.cmy.cmynote.util.GlobalConsts;
import cn.cmy.cmynote.util.HttpUtils;

public class JokeDaoImpl implements IJokeDao {

	@Override
	public void getJoke(final AsyncCallback callback) {
		AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				try {
					String path = GlobalConsts.URL_LOAD_JOKE + "?sort=asc&page=1&pagesize=1&key=1d055a9dd104f9664ff29dab2d0c0fcd";
					InputStream is = HttpUtils.get(path);
					String response = HttpUtils.isToString(is);
					Log.i("info", "xiaohua资讯：" + response);
					JSONObject obj = new JSONObject(response);
					JSONObject result = obj.getJSONObject("result");
					JSONArray data = result.getJSONArray("data");
					JSONObject joke = data.getJSONObject(0);
					String content = joke.getString("content");
					
					return content;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				callback.onSuccess(result);
			}

		};
		task.execute(); // 执行异步任务
	}
	
	

}
