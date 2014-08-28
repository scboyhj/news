package com.school.news.utils;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.school.news.adapter.MyAdapter;
import com.school.news.vo.News;

public class HandlerUtil extends Handler{
	private MyAdapter adapter;
	private ListView listView;
	private ViewSwitcher viewSwitcher;
	private Context context;
	private List<News> list;
	private OnItemClickListener listener;
	
	public HandlerUtil(MyAdapter adapter, ListView listView,
			ViewSwitcher viewSwitcher, Context context, List<News> list,
			OnItemClickListener listener) {
		this.adapter = adapter;
		this.listView = listView;
		this.viewSwitcher = viewSwitcher;
		this.context = context;
		this.list = list;
		this.listener = listener;
	}
	@Override
	public void handleMessage(android.os.Message msg) {
		if (msg.what == 1) {
			adapter = new MyAdapter(list,context);
			listView.setOnItemClickListener(listener);
			listView.setAdapter(adapter);
			viewSwitcher.showPrevious();
		}
	};
}
