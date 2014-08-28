package com.school.news;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.adapter.CheckMyCommentsAdapter;
import com.school.news.adapter.MyCommentsAdapter;
import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.ActivityUtil;
import com.school.news.view.MyListView;
import com.school.news.view.MyListView.OnRefreshListener;
import com.school.news.vo.MyComment;

public class CheckMyCommentsActivity extends Activity implements
		MyCommentsAdapter.IShanChu {
	MyListView listView;
	ProgressDialog dialog;
	HashMap<String, String> params;
	Button button;
	ActivityUtil activityUtil;
	HttpNewsService newsService;
	CheckMyCommentsAdapter commentsAdapter;
	private List<MyComment> commentsList;
	private List<MyComment> commentsListtemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_my_all_listview);
		TextView textView = (TextView) findViewById(R.id.title);
		textView.setText("我的评论");
		listView = (MyListView) findViewById(R.id.list);
		initStaticViews();
		getInfo();
	}

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.backButton:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void getInfo() {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				data();
				return null;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();
				if (commentsList != null) {
					commentsAdapter = new CheckMyCommentsAdapter(commentsList,
							getApplicationContext(),
							CheckMyCommentsActivity.this);
					listView.setAdapter(commentsAdapter);
				} else {
					Toast.makeText(getApplicationContext(), "暂无数据！", 2000)
							.show();
				}
			}
		}.execute();
	}

	private void getThenInfo() {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				dataThen();
				return null;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				dialog.dismiss();
				if (commentsListtemp != null && commentsListtemp.size() > 0) {
					for (int i = 0; i < commentsListtemp.size(); i++) {
						commentsList.add(commentsListtemp.get(i));
					}
					commentsAdapter.notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "加载完成！", 2000)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "没有更多数据了！", 2000)
							.show();
				}

			}
		}.execute();
	}

	private void initStaticViews() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, null, "评论加载中...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		params = new HashMap<String, String>();

		listView = (MyListView) findViewById(R.id.list);

		listView.setCacheColorHint(Color.argb(0, 0, 0, 0));

		button = new Button(this);
		button.setText("查看更多的评论");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getThenInfo();
			}
		});
		activityUtil = new ActivityUtil();

		listView.addFooterView(button);
		listView.setonRefreshListener(refreshListener);

		newsService = new HttpNewsService();

	}

	private void dataThen() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		params.clear();

		String ss = MyApplication.myUser.toString();
		params.put("userId", MyApplication.myUser.getUserName());
		params.put("status", "getMyComments");
		params.put("type", "again");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_SELECT), params);
		commentsListtemp = newsService.parseCommentsMyCheck(data);
		int li = commentsListtemp.size();
	}

	private void data() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		params.clear();

		String ss = MyApplication.myUser.toString();
		params.put("userId", MyApplication.myUser.getUserName());
		params.put("status", "getMyComments");
		params.put("type", "renovate");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_SELECT), params);
		commentsList = newsService.parseCommentsMyCheck(data);
		int li = commentsList.size();
	}

	private OnRefreshListener refreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			// new AsyncTask<Map<String, String>, Integer, List<MyComment>>() {
			//
			// @Override
			// protected List<MyComment> doInBackground(
			// Map<String, String>... params) {
			// try {
			// Thread.sleep(1000);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// data();
			// return commentsList;
			// }
			//
			// }.execute();
			new AsyncTask<Void, Void, Boolean>() {
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					// dialog.show();
				}

				@Override
				protected Boolean doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					data();
					return null;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					// dialog.dismiss();
					if (commentsList != null) {
						commentsAdapter = new CheckMyCommentsAdapter(
								commentsList, getApplicationContext(),
								CheckMyCommentsActivity.this);
						listView.setAdapter(commentsAdapter);
						Toast.makeText(getApplicationContext(), "加载完成！", 2000)
								.show();
					} else {
						Toast.makeText(getApplicationContext(), "暂无数据！", 2000)
								.show();
					}

					listView.onRefreshComplete();
				}
			}.execute();
		}
	};

	public boolean deleteComment(String id) {

		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "deleteComments");
		params.put("id", id);

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_ADD), params);

		if (data.equals("1"))

			return true;
		else
			return false;

	}

	@Override
	public void DeleteComment(final String id) {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return deleteComment(id);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.hide();
				if (result) {
					ListUpdate();
					Toast.makeText(getApplicationContext(), "删除成功！", 2000)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "删除失败！", 2000)
							.show();
				}
			}
		}.execute();
	}

	public void ListUpdate() {
		new AsyncTask<Void, Void, Boolean>() {
			protected void onPreExecute() {
				dialog.show();
			};

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				data();
				return null;
			}

			protected void onPostExecute(Boolean result) {

				commentsAdapter = new CheckMyCommentsAdapter(commentsList,
						getApplicationContext(), CheckMyCommentsActivity.this);
				listView.setAdapter(commentsAdapter);
				dialog.dismiss();
				// listView.setAdapter(commentsAdapter);
				listView.onRefreshComplete();
			};
		}.execute();
	}
}
