package cn.cmy.cmynote.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.entity.Note;
import cn.cmy.cmynote.util.DateUtils;

public class MyAdapter extends BaseAdapterListView<Note> {

	public MyAdapter(Context context, List<Note> data, ListView listView) {
		super(context, data, listView);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = getLayoutInflater().inflate(R.layout.notes_items, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitleItem);
			holder.tvData = (TextView) convertView.findViewById(R.id.tvDataItem);
			convertView.setTag(holder);
		}
		Note note = getData().get(position);
		holder = (ViewHolder) convertView.getTag();
		holder.tvTitle.setText(note.getTitle());
		holder.tvData.setText(DateUtils.getDateTime(note.getData()));
		
		return convertView;
	}

	class ViewHolder {
		private TextView tvTitle;
		private TextView tvData;
	}
}
