package cn.cmy.cmynote.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Note extends BmobObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private long note_id;
	private String title;
	private String content;
	private long date;

	public Note() {
		
	}

	public Note(long note_id, String title, String content, long date) {
		super();
		this.note_id = note_id;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public long get_id() {
		return note_id;
	}

	public void set_id(long _id) {
		this.note_id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Note [note_id=" + note_id + ", title=" + title + ", content=" + content + ", date=" + date + "]";
	}

}
