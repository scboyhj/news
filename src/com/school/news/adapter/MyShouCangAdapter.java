package com.school.news.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.news.CheckShouCangNewsActivity;
import com.school.news.R;
import com.school.news.app.MyApplication;
import com.school.news.vo.News;

public class MyShouCangAdapter extends BaseAdapter {
	private List<News> list;
	private LayoutInflater inflater;

	private CheckShouCangNewsActivity activity;

	public MyShouCangAdapter(List<News> list, CheckShouCangNewsActivity activity) {
		this.activity = activity;
		this.list = list;
		this.inflater = (LayoutInflater) activity
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
			convertView = inflater.inflate(R.layout.aa_layout_news_sou_cang,
					null);
			holder.iv_news_photo = (ImageView) convertView
					.findViewById(R.id.iv_news_photo);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title_news_top_item);
			holder.tv_Description = (TextView) convertView
					.findViewById(R.id.tv_description_news_top_item);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_news_count_comments);
			holder.tv_shanchu = (TextView) convertView
					.findViewById(R.id.shanchushoucang);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final News news = list.get(position);
		holder.tv_title.setText(news.getNewsTitle());
		holder.tv_Description.setText(news.getNewsContent());
		holder.tv_count.setText(news.getCommentsCount() + "跟帖");

		holder.tv_shanchu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activity.deleteShouCang(news.getNewsId() + "");
			}
		});
		MyApplication.imageLoader.displayImage(news.getPhotoUrl(),
				holder.iv_news_photo, MyApplication.options);
		// holder.iv_news_photo.setImageURI(Uri.fromFile(file));
		// holder.iv_news_photo.setImageDrawable(context.getResources().getDrawable(R.drawable.k));
		// } else {
		// holder.iv_news_photo
		// .setImageResource(R.drawable.base_list_default_icon);
		// }

		return convertView;
	}

	public static class ViewHolder {
		TextView tv_title;
		TextView tv_Description;
		TextView tv_shanchu;
		ImageView iv_news_photo;
		TextView tv_count;
	}

}
