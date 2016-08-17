package cn.cmy.cmynote.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class MyApplication extends Application {

	private static MyApplication context;

	private static RequestQueue queue;


	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		queue = Volley.newRequestQueue(MyApplication.getContext());
	}

	public static MyApplication getContext() {
		return context;
	}

	public static RequestQueue getQueue() {
		return queue;
	}


}
