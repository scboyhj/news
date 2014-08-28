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
import com.school.news.vo.News;

public class PicAdapter extends BaseAdapter {
	private List<News> list;
	private LayoutInflater inflater;
	private News news;
	private Context context;
	
	public PicAdapter(List<News> list,Context context) {
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
			convertView = inflater.inflate(R.layout.layout_pic_item, null);
			holder.iv_pic_photo = (ImageView)convertView.findViewById(R.id.iv_pic_photo);
			holder.tv_pic_title = (TextView) convertView.findViewById(R.id.tv_pic_title);
			holder.tv_pic_comments_count = (TextView) convertView.findViewById(R.id.tv_pic_comments_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		news = list.get(position);
	    holder.tv_pic_title.setText(news.getNewsTitle());
		holder.tv_pic_comments_count.setText(news.getCommentsCount()+"跟帖");
			if(news.getPhotos()!=null){
				String photoName=news.getPhotos().get(0).getNewsFilmResourceUrl();
				String photoUrl=context.getResources().getString(R.string.android_url);
				File file = new File(photoUrl,photoName.split("/")[1]);
				//从后台读取照片
				if(!file.exists()){
					new HttpRequestService().putPhoto(photoName, file, context);
				}
				holder.iv_pic_photo.setImageURI(Uri.fromFile(file));
			}else{
				holder.iv_pic_photo.setImageResource(R.drawable.biz_pics_set_item_icon);
			}		
		
		return convertView;
	}
	public static class ViewHolder {
		TextView tv_pic_title;
		ImageView iv_pic_photo;
		TextView tv_pic_comments_count;
	}
	
}
