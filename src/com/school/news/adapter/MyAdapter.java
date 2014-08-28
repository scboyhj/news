package com.school.news.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.news.R;
import com.school.news.app.MyApplication;
import com.school.news.vo.News;

public class MyAdapter extends BaseAdapter {
	private List<News> list;
	private LayoutInflater inflater;
	private News news;
	private Context context;

	public MyAdapter(List<News> list, Context context) {
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
			convertView = inflater.inflate(R.layout.layout_news_top_item, null);
			holder.iv_news_photo = (ImageView) convertView
					.findViewById(R.id.iv_news_photo);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title_news_top_item);
			holder.tv_Description = (TextView) convertView
					.findViewById(R.id.tv_description_news_top_item);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_news_count_comments);
			holder.tv_Time = (TextView) convertView
					.findViewById(R.id.tv_news_count_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		news = list.get(position);
		holder.tv_title.setText(news.getNewsTitle());
		holder.tv_Description.setText(news.getNewsContent());
		holder.tv_count.setText(news.getCommentsCount() + "跟帖");

		String myTime = getTime(news.getNewsTime());
		holder.tv_Time.setText(myTime);
		// if (news.getPhotoUrl() != null) {
		// String photoName = news.getPhotoUrl(); //
		// news.getPhotos().get(0).getNewsFilmResourceUrl();
		// String photoUrl = context.getResources().getString(
		// R.string.android_url);
		// String[] ss = photoName.split("/");
		// File file = new File(photoUrl, ss[ss.length - 1]);
		//
		// sss = photoUrl;
		//
		// File file2 = new File(photoUrl);// 建立上级目录
		// if (!file2.exists()) {
		// if (file2.mkdirs()) {
		// System.out.println("ahah");
		// }
		// }
		//
		// // 从后台读取照片
		// sss = file.getAbsolutePath();
		// //if (!file.exists()) {
		// new HttpRequestService().putPhoto(photoName, file, context);
		// //}
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

	private String getTime(String str) {

		// TODO Auto-generated method stub

		String mys = str.substring(0, str.indexOf(" "));
		// System.out.println(mys);
		String[] ys = mys.split("-");

		for (int i = 0; i < ys.length; i++) {
			System.out.println(ys[i]);
		}

		String myh = str.substring(str.indexOf(" ") + 1, str.length());

		String[] myhs = myh.split(":", str.length());
		for (int i = 0; i < myhs.length; i++) {
			System.out.println(myhs[i]);
		}

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String mytime = dateFormat.format(date);
		System.out.println(mytime);

		String mys2 = mytime.substring(0, mytime.indexOf(" "));
		// System.out.println(mys);
		String[] ys2 = mys2.split("-");

		for (int i = 0; i < ys2.length; i++) {
			System.out.println(ys2[i]);
		}

		String myh2 = mytime
				.substring(mytime.indexOf(" ") + 1, mytime.length());

		String[] myhs2 = myh2.split(":", str.length());
		for (int i = 0; i < myhs2.length; i++) {
			System.out.println(myhs2[i]);
		}

		if (ys[0].equals(ys2[0])) {
			if (ys[1].equals(ys2[1])) {
				if (ys[2].equals(ys2[2])) {
					if (myhs[0].equals(myhs2[0])) {
						if (myhs[1].equals(myhs2[1])) {
							// System.out.println("刚刚发布");
							return "刚刚发布";
						} else {
							int i1 = Integer.parseInt(myhs2[1]);
							int i2 = Integer.parseInt(myhs[1]);
							// System.out.println((i1 - i2) + "分钟前发布");
							return (i1 - i2) + "分钟前发布";
						}
					} else {
						int i1 = Integer.parseInt(myhs2[0]);
						int i2 = Integer.parseInt(myhs[0]);
						// System.out.println((i1 - i2) + "小时前发布");
						return (i1 - i2) + "小时前发布";
					}
				} else {
					System.out.println("一天前发布");

					int i1 = Integer.parseInt(ys2[2]);
					int i2 = Integer.parseInt(ys[2]);
					// System.out.println((i1 - i2) + "小时前发布");
					return (i1 - i2) + "天前发布";

				}
			} else {
				System.out.println("一个月前发布");
				return "一个月前发布";
			}
		} else {
			System.out.println("一年前发布");
			return "一年前发布";
		}

		// return null;
	}

	public static class ViewHolder {
		TextView tv_title;
		TextView tv_Description;
		ImageView iv_news_photo;
		TextView tv_count;

		TextView tv_Time;

	}

}
