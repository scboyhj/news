package com.school.news;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.school.news.adapter.MyGongGaoadapter;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.ActivityUtil;
import com.school.news.view.MyListView;
import com.school.news.view.MyListView.OnRefreshListener;
import com.school.news.vo.GongGao;

public class TabGongGaoTopActivity extends Activity {

	private MyListView listView;
	private List<GongGao> list;
	private List<GongGao> templist;
	private MyGongGaoadapter adapter;
	private ViewSwitcher viewSwitcher;
	private Map<String, String> params;
	private HttpNewsService newsService;
	private ImageView testView;
	private Button button;
	private ActivityUtil activityUtil;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_layout_gonggao_main);

		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		initStaticViews();
		requestRSSFeed();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//requestRSSFeed();
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

	TextView tv_title;

	private void initStaticViews() {
		// TODO Auto-generated method stub
		params = new HashMap<String, String>();
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher_news_top);
		dialog = new ProgressDialog(TabGongGaoTopActivity.this);
		dialog.setMessage("正在加载，请稍等..");
		listView = new MyListView(this);

		listView.setCacheColorHint(Color.argb(0, 0, 0, 0));
		testView = new ImageView(this);

		testView.setImageResource(R.drawable.p1);

		// layout2.addView(listView);

		button = new Button(this);
		button.setText("查看更多的公告");
		button.setHint(0 + "");
		activityUtil = new ActivityUtil();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String i = button.getHint().toString();
				int num = Integer.valueOf(i) + 5;
				button.setHint(num + "");
				LoadMore(num + "");
			}
		});
		listView.addFooterView(button);
		listView.setonRefreshListener(refreshListener);
		viewSwitcher.addView(listView);

		// viewSwitcher.addView(getLayoutInflater().inflate(
		// R.layout.layout_progress_page, null));
		// viewSwitcher.showNext();
		listView.setOnItemClickListener(listener);
		newsService = new HttpNewsService();

	}

	public void LoadMore(final String i) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				moredata(i);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();
				if (templist != null && templist.size() > 0) {
					for (int i = 0; i < templist.size(); i++) {
						list.add(templist.get(i));
					}

					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getApplicationContext(), "没有更多数据了！", 2000)
							.show();
				}

			}
		}.execute();
	}

	List<File> files;

	private void loadAdpter() {
		// TODO Auto-generated method stub

		adapter = new MyGongGaoadapter(list, TabGongGaoTopActivity.this);
		listView.setOnItemClickListener(listener);
		listView.setAdapter(adapter);

		listView.onRefreshComplete();

	}

	int i = 0;

	private OnRefreshListener refreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			new AsyncTask<Map<String, String>, Integer, List<GongGao>>() {
				protected void onPreExecute() {
					// viewSwitcher.showNext();
					// dialog.show();
				};

				@Override
				protected List<GongGao> doInBackground(
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
				protected void onPostExecute(List<GongGao> result) {
					// dialog.dismiss();

					button.setHint(0 + "");
					list = result;

					loadAdpter();

				}

			}.execute();
		}
	};

	private void requestRSSFeed() {
		dialog.show();
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
			dialog.dismiss();
			if (msg.what == 1) {

				loadAdpter();

			} else {
				Toast.makeText(getApplicationContext(), "无新闻数据！", 2000).show();
			}
		};
	};

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent intent = new Intent(TabGongGaoTopActivity.this,
					MyNewsGongGaoActivity.class);

			intent.putExtra("content_url",
					"http://" + HttpRequestUrl.IP
							+ ":8080/NewsPublishbeta/gonggao.jsp?annid="
							+ list.get(position - 1).getAnnouncement_id()
							+ "&user=1");

			TabGongGaoTopActivity.this.startActivityForResult(intent, position);
		}
	};

	private void moredata(String i) {
		params.clear();
		String data = newsService.requestByPost("http://" + HttpRequestUrl.IP
				+ ":" + HttpRequestUrl.port
				+ "/NewsPublishbeta/Announcement?user=1&startindex=" + i,
				params);
		templist = newsService.parseGongGao(data);

	}

	private void data() {
		params.clear();
		String data = newsService.requestByPost("http://" + HttpRequestUrl.IP
				+ ":" + HttpRequestUrl.port
				+ "/NewsPublishbeta/Announcement?user=1&startindex=0", params);
		list = newsService.parseGongGao(data);
		int li = list.size();
	}
}