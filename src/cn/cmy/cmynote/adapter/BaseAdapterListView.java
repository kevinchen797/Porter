package cn.cmy.cmynote.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

public abstract class BaseAdapterListView<T>
	extends android.widget.BaseAdapter {
	private Context context;
	private List<T> data;
	private ListView listView;
	private LayoutInflater inflater;

	public BaseAdapterListView(Context context, List<T> data,ListView listView) {
		super();
		setContext(context);
		setData(data);
		setListView(listView);
		setLayoutInflater();
	}
	
	
	public ListView getListView() {
		return listView;
	}


	public void setListView(ListView listView) {
		this.listView = listView;
	}


	public void setContext(Context context) {
		if(context == null) {
			throw new IllegalArgumentException("参数Context不允许为null！！！");
		}
		this.context = context;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public void setData(List<T> data) {
		if(data == null) {
			data = new ArrayList<T>();
		}
		this.data = data;
	}
	
	public List<T> getData() {
		return this.data;
	}
	
	public void setLayoutInflater() {
		inflater = LayoutInflater.from(this.context);
	}
	
	public LayoutInflater getLayoutInflater() {
		return this.inflater;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
