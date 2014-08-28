package com.school.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.adapter.MyShouCangAdapter;
import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.ActivityUtil;
import com.school.news.view.MyListView;
import com.school.news.view.MyListView.OnRefreshListener;
import com.school.news.vo.News;

public class CheckShouCangNewsActivity extends Activity {

	private MyListView listView;
	private List<News> list;
	private List<News> templist;
	private MyShouCangAdapter adapter;

	private Map<String, String> params;
	private HttpNewsService newsService;
	private ImageView testView;
	private Button button;
	private ActivityUtil activityUtil;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.aa_layout_news_top);
		setContentView(R.layout.aa_my_all_listview);

		TextView textView = (TextView) findViewById(R.id.title);
		textView.setText("我的收藏");

		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		initStaticViews();
		requestRSSFeed();

	}

	TextView tv_title;

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.backButton:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void initStaticViews() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		listView = (MyListView) findViewById(R.id.list);

		listView.setCacheColorHint(Color.argb(0, 0, 0, 0));
		testView = new ImageView(this);

		testView.setImageResource(R.drawable.p1);

		button = new Button(this);
		button.setText("查看更多的收藏");
		button.setHint(21 + "");
		activityUtil = new ActivityUtil();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getThenInfo();
			}
		});
		listView.addFooterView(button);
		listView.setonRefreshListener(refreshListener);

		listView.setOnItemClickListener(listener);
		newsService = new HttpNewsService();

	}

	private void dataThen() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		params.clear();

		String ss = MyApplication.myUser.toString();
		params.put("status", "getCollectionByUserId");
		params.put("userId", MyApplication.myUser.getUserName());
		params.put("type", "again");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.Collection_ADD), params);
		templist = newsService.parseNews(data);
		// int li = commentsListtemp.size();
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
				if (templist != null && templist.size() > 0) {

					for (int i = 0; i < templist.size(); i++) {
						list.add(templist.get(i));
					}
					adapter.notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "加载完成！", 3000)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "没有更多数据了！", 3000)
							.show();
				}

			}
		}.execute();
	}

	private void initDataViews() {

		loadAdpter();
	}

	private void loadAdpter() {
		// TODO Auto-generated method stub
		if (list != null) {
			// List<News> mylist = list;
			// int y = mylist.size();
			adapter = new MyShouCangAdapter(list,
					CheckShouCangNewsActivity.this);
			listView.setOnItemClickListener(listener);
			listView.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(), "数据为空！", 2000).show();
		}
		listView.onRefreshComplete();
		dialog.dismiss();
	}

	int i = 0;
	// Handler handler2 = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// pager.setCurrentItem(i);
	// i = ++i % 3;
	// };
	// };
	private OnRefreshListener refreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			new AsyncTask<Map<String, String>, Integer, List<News>>() {
				// protected void onPreExecute() {
				// viewSwitcher.showNext();
				// };
				@Override
				protected List<News> doInBackground(
						Map<String, String>... params) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					data();
					return list;
				}

				@Override
				protected void onPostExecute(List<News> result) {
					button.setHint(21 + "");
					// list = result;

					initDataViews();

					listView.onRefreshComplete();
				}

			}.execute();
		}
	};

	private void requestRSSFeed() {
		dialog = ProgressDialog.show(this, null, "正在加载...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		Thread t = new Thread() {
			@Override
			public void run() {
				// super.run();
				try {

					data();// 获得数据
					if (list.size() == 0) {
						handler.sendEmptyMessage(-1);
					} else {
						handler.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {

				initDataViews();// ();

			}
		};
	};

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// if (position == 1) {
			// return;
			// }
			List<News> ml = list;
			Intent intent = new Intent(CheckShouCangNewsActivity.this,
					MyNewsContentActivity.class);

			intent.putExtra("content_url", list.get(position - 1).getNewsId()
					+ "");
			intent.putExtra("content_comment", list.get(position - 1)
					.getCommentsCount() + "");
			CheckShouCangNewsActivity.this.startActivityForResult(intent,
					position);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("返回");
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void data() {
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "getCollectionByUserId");
		params.put("userId", MyApplication.myUser.getUserName());
		params.put("type", "renovate");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.Collection_ADD), params);
		list = newsService.parseNews(data);
		int li = list.size();
	}

	public void deleteShouCang(final String newsId) {
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
				return deleData(newsId);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				dialog.dismiss();
				if (result) {
					Toast.makeText(getApplicationContext(), "删除成功!", 2000)
							.show();
					ListUpdate();

				} else {
					Toast.makeText(getApplicationContext(), "删除失败!", 2000)
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

				adapter = new MyShouCangAdapter(list,
						CheckShouCangNewsActivity.this);
				listView.setOnItemClickListener(listener);
				listView.setAdapter(adapter);

				listView.onRefreshComplete();

				dialog.dismiss();
				// listView.setAdapter(commentsAdapter);
				listView.onRefreshComplete();
			};
		}.execute();
	}

	public boolean deleData(String id) {
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "deleteCollByUserId");
		params.put("userId", MyApplication.myUser.getUserName());
		params.put("newsId", id);
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.Collection_ADD), params);
		if (data.equals("1")) {
			return true;
		} else {
			return false;
		}

	}
}