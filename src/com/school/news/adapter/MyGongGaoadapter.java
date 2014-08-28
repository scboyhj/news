package com.school.news.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.news.R;
import com.school.news.vo.GongGao;

public class MyGongGaoadapter extends BaseAdapter {
	private List<GongGao> list;
	private LayoutInflater inflater;

	private Context context;

	public MyGongGaoadapter(List<GongGao> list, Context context) {
		this.context = context;
		this.list = list;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	String sss;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.aa_gonggaoitem, null);
			holder.tv_Description = (TextView) convertView
					.findViewById(R.id.gonggaocontent);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.gonggaotitle);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GongGao gongGao = list.get(position);
		holder.tv_title.setText(gongGao.getTitle());
		holder.tv_Description.setVisibility(View.GONE);
		// holder.tv_Description.setText(Html.fromHtml(gongGao.getContent()));

		return convertView;
	}

	public static class ViewHolder {
		TextView tv_title;
		TextView tv_Description;

	}

}
