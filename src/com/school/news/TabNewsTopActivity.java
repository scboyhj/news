package com.school.news;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.school.news.adapter.AdImageAdapter;
import com.school.news.adapter.MyAdapter;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.ActivityUtil;
import com.school.news.view.MyGallery;
import com.school.news.view.MyListView;
import com.school.news.view.MyListView.OnRefreshListener;
import com.school.news.vo.News;

public class TabNewsTopActivity extends Activity {

	private MyListView listView;
	private List<News> list;
	private List<News> templist;
	private MyAdapter adapter;
	private ViewSwitcher viewSwitcher;
	private Map<String, String> params;
	private HttpNewsService newsService;
	private ImageView testView;
	private Button button;
	private ActivityUtil activityUtil;

	String categroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_news_top);
		categroy = getIntent().getStringExtra("news.newsTitle");
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		initStaticViews();
		requestRSSFeed();

	}

	TextView tv_title;
	View layout;
	private MyGallery g;
	private List<View> dots;

	private void initStaticViews() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher_news_top);

		layout = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.newviewpager, null);
		g = (MyGallery) layout.findViewById(R.id.ga);
		g.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(TabNewsTopActivity.this,
						MyNewsContentActivity.class);
				intent.putExtra("content_comment", list.get(arg2)
						.getCommentsCount() + "");
				intent.putExtra("content_url", list.get(arg2).getNewsId() + "");

				TabNewsTopActivity.this.startActivityForResult(intent, arg2);

			}
		});
		dots = new ArrayList<View>();
		dots.add(layout.findViewById(R.id.v_dot0));
		dots.add(layout.findViewById(R.id.v_dot1));
		dots.add(layout.findViewById(R.id.v_dot2));
		tv_title = (TextView) layout.findViewById(R.id.tv_title);

		listView = new MyListView(this);

		listView.setCacheColorHint(Color.argb(0, 0, 0, 0));
		testView = new ImageView(this);

		testView.setImageResource(R.drawable.p1);

		// layout2.addView(listView);

		listView.addHeaderView(layout);
		button = new Button(this);
		button.setText("查看更多的新闻");
		button.setHint(21 + "");
		activityUtil = new ActivityUtil();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int startIndex = Integer.parseInt(button.getHint().toString());
				int endIndex = startIndex + ActivityUtil.COUNT;
				int oldLength = list.size();
				list = activityUtil.nextPage(list, startIndex, endIndex,
						categroy);
				int newLength = list.size();
				if (newLength == oldLength) {
					ActivityUtil.toast(TabNewsTopActivity.this, "没有更多的新闻加载了");
				} else {
					adapter.notifyDataSetChanged();
					button.setHint(endIndex + "");
				}
			}
		});
		listView.addFooterView(button);
		listView.setonRefreshListener(refreshListener);
		viewSwitcher.addView(listView);
		// viewSwitcher.addView(layout2);
		viewSwitcher.addView(getLayoutInflater().inflate(
				R.layout.layout_progress_page, null));
		viewSwitcher.showNext();
		listView.setOnItemClickListener(listener);
		newsService = new HttpNewsService();

	}

	List<File> files;

	private void loadTitleAdapter() {
		// TODO Auto-generated method stub
		if (list.size() >= 3) {
			AdImageAdapter adImageAdapter = new AdImageAdapter(
					getApplicationContext(), list.subList(0, 3), tv_title, dots);
			g.setAdapter(adImageAdapter);
		} else {
			Toast.makeText(getApplicationContext(), "服务器有误！", 2000).show();
		}
	}

	private void initDataViews() {

		loadTitleAdapter();
		loadAdpter();
	}

	private void loadAdpter() {
		// TODO Auto-generated method stub

		List<News> mylist = list;
		int y = mylist.size();
		adapter = new MyAdapter(list.subList(3, list.size()),
				TabNewsTopActivity.this);
		listView.setOnItemClickListener(listener);
		listView.setAdapter(adapter);

		listView.onRefreshComplete();

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
					list = result;

					initDataViews();
					// viewSwitcher.showPrevious();
					// listView.setAdapter(new MyAdapter(list,
					// TabNewsTopActivity.this));
					// listView.onRefreshComplete();
					// AdImageAdapter adImageAdapter = new AdImageAdapter(
					// getApplicationContext(), list.subList(0, 3),
					// tv_title, dots);
					// g.setAdapter(adImageAdapter);
					//
					// List<News> mylist = list;
					// int y = mylist.size();
					// adapter = new MyAdapter(list.subList(3, list.size()),
					// TabNewsTopActivity.this);
					// listView.setOnItemClickListener(listener);
					// listView.setAdapter(adapter);
					//
					// listView.onRefreshComplete();
				}

			}.execute();
		}
	};

	private void requestRSSFeed() {
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
				viewSwitcher.showPrevious();
			} else {
				Toast.makeText(getApplicationContext(), "无新闻数据！", 2000).show();
			}
		};
	};

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 1) {
				return;
			}
			Intent intent = new Intent(TabNewsTopActivity.this,
					MyNewsContentActivity.class);

			intent.putExtra("content_url", list.get(position + 1).getNewsId()
					+ "");
			intent.putExtra("content_comment", list.get(position + 1)
					.getCommentsCount() + "");
			TabNewsTopActivity.this.startActivityForResult(intent, position);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("返回");
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void data() {
		params.clear();
		params.put("news.newsTitle", categroy);
		params.put("news.startIndex", 1 + "");
		params.put("news.endIndex", 21 + "");

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.NEWS_SELECT), params);
		list = newsService.parseNews(data);
		int li = list.size();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)
					.setMessage("是否退出\"E闻\"？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									android.os.Process
											.killProcess(android.os.Process
													.myPid());
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

								}
							}).show();
			return true;

		}
		return super.onKeyDown(keyCode, event);

	}
}