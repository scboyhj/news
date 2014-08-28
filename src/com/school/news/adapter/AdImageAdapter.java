package com.school.news.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.school.news.R;
import com.school.news.app.MyApplication;
import com.school.news.vo.News;

public class AdImageAdapter extends BaseAdapter {

	private List<News> news;
	private Context context;
	private TextView mTextView;
	private List<View> dots;
	private int oldPosition = 0;

	// private int[] imageResId = new int[] { R.drawable.one_1,
	// R.drawable.one_4,
	// R.drawable.one_5, R.drawable.one_6, R.drawable.one_8 };
	// private List<Bitmap> bit;

	public AdImageAdapter(Context context, List<News> lists,
			TextView mTextView, List<View> dots) {
		this.context = context;
		this.news = lists;
		this.mTextView = mTextView;
		this.dots = dots;

	}

	public int getCount() {

		if (news == null || news.size() == 0) {
			return 0;
		} else {
			int i = news.size();
			return news.size();
		}
	}

	public News getItem(int position) {
		// TODO Auto-generated method stub
		return news.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		ImageView imageview = null;
		if (convertView == null) {
			convertView = new ImageView(context);
			imageview = (ImageView) convertView;
		}
		imageview.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		imageview.setScaleType(ScaleType.FIT_XY);
		// System.out.println("-------<>" + imagepath);
		if (news == null || news.equals(null)) {
			// imageview.setBackgroundResource(imageResId[position]);
			// mTextView.setText("");
		} else {
			final News info = news.get(position);
			String s = info.toString();
			// imageview.setBackgroundDrawable(null);
			//
			// imageview.setImageURI(Uri.fromFile(files.get(position)));
			MyApplication.imageLoader.displayImage(news.get(position)
					.getPhotoUrl(), imageview, MyApplication.options);

			mTextView.setText(info.getNewsTitle());
		}
		dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		oldPosition = position;
		return convertView;
	}
}
