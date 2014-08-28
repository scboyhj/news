package com.school.news.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.news.R;
import com.school.news.http.HttpRequestService;
import com.school.news.vo.Comments;

public class NewsDetailAdapter extends BaseAdapter {
	private List<Comments> list;
	private LayoutInflater inflater;
	private Comments comments;
	private Context context;
	
	public NewsDetailAdapter(List<Comments> list,Context context) {
		this.context = context;
		this.list = list;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_news_detail_topic, null);
			holder.iv_topic_image = (ImageView) convertView.findViewById(R.id.iv_topic_image);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		comments = list.get(position);
		holder.tv_content.setText(comments.getCommentsContent());
		holder.tv_date.setText(comments.getCommentsData());
		holder.tv_user.setText("网易"+comments.getUserAddress()+"网友：");
		String photoUrl=context.getResources().getString(R.string.android_url);
		String photoName=comments.getUserPhoto();
		File file = new File(photoUrl,photoName.split("/")[1]);
		//从后台读取照片
		if(!file.exists()){
			new HttpRequestService().putPhoto(photoName, file, context);
		}
		holder.iv_topic_image.setImageURI(Uri.fromFile(file));
		return convertView;
	}
	public static class ViewHolder {
		TextView tv_user;
		TextView tv_date;
		ImageView iv_topic_image;
		TextView tv_content;
	}

}
