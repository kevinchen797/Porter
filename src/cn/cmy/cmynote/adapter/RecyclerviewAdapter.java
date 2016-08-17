package cn.cmy.cmynote.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import cn.cmy.cmynote.R;
import cn.cmy.cmynote.entity.Note;
import cn.cmy.cmynote.util.DateUtils;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {

	private Context context;
	private List<Note> notes;
	private LayoutInflater inflater;
	private List<Integer> mHeigths;
	private OnItemClickListener itemClickListener;
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.itemClickListener = listener;
	}
	
	public RecyclerviewAdapter(Context context, List<Note> notes) {
		this.context = context;
		this.notes = notes;
		inflater = LayoutInflater.from(context);
		mHeigths = new ArrayList<Integer>();
		for(int i = 0; i < notes.size(); i++){
			mHeigths.add((int)(100 + Math.random() * 400));
		}
	}
	
	@Override
	public int getItemCount() {
		return notes.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		
		LayoutParams lp = holder.itemView.getLayoutParams();
		lp.height = mHeigths.get(position);
		holder.itemView.setLayoutParams(lp);
		Note note = notes.get(position);
		holder.tvTitle.setText(note.getTitle());
		holder.tvData.setText(DateUtils.getDateTime(note.getDate()));
		if (itemClickListener != null) {
			holder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemClickListener.OnItemClick(holder.itemView, position);
				}
			});
			
			//longClick
			holder.itemView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					itemClickListener.OnItemLongClick(holder.itemView, position);
					return false;
				}
			});
		}
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup view, int arg1) {
		
		View itemView = inflater.inflate(R.layout.notes_items, view, false);
		MyViewHolder viewHolder = new MyViewHolder(itemView );
		return viewHolder;
	}
	
	public void add(Note note, int position){
		notes.add(note);
		notifyItemInserted(position);
	}
	
	public void delete(int position){
		notes.remove(position);
		notifyItemRemoved(position);
	}

	class MyViewHolder extends ViewHolder{
		
		TextView tvTitle;
		TextView tvData;
		
		public MyViewHolder(View itemView) {
			super(itemView);
			tvTitle = (TextView) itemView.findViewById(R.id.tvTitleItem);
			tvData = (TextView) itemView.findViewById(R.id.tvDataItem);
		}
		
	}
	
	public interface OnItemClickListener{
		void OnItemClick(View view, int position);
		void OnItemLongClick(View view, int position);
	}
}
