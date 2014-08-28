package com.school.news.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.news.CheckMyCommentsActivity;
import com.school.news.R;
import com.school.news.app.MyApplication;
import com.school.news.vo.MyComment;
import com.school.news.vo.News;

public class CheckMyCommentsAdapter extends BaseAdapter {
	private List<MyComment> list;
	private LayoutInflater inflater;
	private News news;
	private Context context;
	CheckMyCommentsActivity activity;

	public interface IPopShow {
		public void ShowPop(String userName, String toUserName, String realName);
	}

	public interface NIShanChu {
		public void DeleteComment(String id);
	}

	public CheckMyCommentsAdapter(List<MyComment> list, Context context,
			CheckMyCommentsActivity commentsActivity) {
		this.context = context;
		this.list = list;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = commentsActivity;
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
		final MyComment comment = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.aa_news_comment_check_item,
					null);
			holder.tv_newsTitle = (TextView) convertView
					.findViewById(R.id.newsTitile);
			holder.tv_name = (TextView) convertView.findViewById(R.id.name);
			holder.tv_date = (TextView) convertView
					.findViewById(R.id.commentdate);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.commentcontent);
			holder.tv_shanchu = (TextView) convertView
					.findViewById(R.id.shanchu);
			// holder.tv_huifu = (TextView)
			// convertView.findViewById(R.id.huifu);
			holder.tv_fh = (TextView) convertView.findViewById(R.id.fh);
			holder.tv_p2 = (TextView) convertView.findViewById(R.id.p2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// String s1 = comment.getpComId();
		// String s2 = comment.getpUserId();
		// String s3 = comment.getpUserName();

	//	final MyComment comment = list.get(position);
		String ss = comment.toString();
		String tt=comment.getpUserName();
		if (comment.getpUserName().equals("")) {
			holder.tv_p2.setVisibility(View.INVISIBLE);
			holder.tv_fh.setVisibility(View.INVISIBLE);
		} else {
			holder.tv_p2.setVisibility(View.VISIBLE);
			holder.tv_fh.setVisibility(View.VISIBLE);
			holder.tv_p2.setText(comment.getpUserName());

		}
		if (MyApplication.myUser != null) {
			if (comment.getUserName()
					.equals(MyApplication.myUser.getUserName())) {
				holder.tv_shanchu.setVisibility(View.VISIBLE);

				holder.tv_shanchu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						activity.DeleteComment(comment.getComId());
					}
				});

			} else {

			}
		}
		String sss = comment.getNewsTitile();
		holder.tv_newsTitle.setText(comment.getNewsTitile());
		holder.tv_name.setText(comment.getUserName());
		holder.tv_date.setText(comment.getCommentsDate());
		holder.tv_content.setText(comment.getComContent());
		// holder.tv_huifu.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// activity.ShowPop("123", comment.getpComId(),
		// comment.getpUserName());
		// // popupWindow.showAtLocation(parent, gravity, x, y)
		//
		// }
		// });
		return convertView;
	}

	public static class ViewHolder {
		TextView tv_fh;
		TextView tv_p2;
		TextView tv_shanchu;
		TextView tv_name;
		TextView tv_date;
		TextView tv_newsTitle;
		// ImageView iv_news_photo;
		TextView tv_content;
		// TextView tv_huifu;
	}

}
