package com.school.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.vo.NewsTitle;

public class TabNewsActivity extends ActivityGroup implements OnClickListener {
	RelativeLayout layout;
	RelativeLayout layout_news_main;
	LayoutInflater inflater;
	Intent intent;
	View page;// 用来存放顶部具体分类的view
	// WebView tianqiView;
	// TextView tv_front;// 需要移动的View
	private List<NewsTitle> titlelist;
	RadioButton tv_bar_news;
	RadioButton tv_bar_sport;
	RadioButton tv_bar_play;
	RadioButton tv_bar_finance;
	RadioButton tv_bar_science;
	RadioButton tv_bar_more;
	HttpNewsService newsService;
	Map<String, String> params;
	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_news);

		initStaticView();
		getTitles();

	}

	private void initStaticView() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, null, "更新数据，请稍等...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
	}

	private void loadFirst() {
		// TODO Auto-generated method stub
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		inflater = getLayoutInflater();
		page = inflater.inflate(R.layout.layout_news_top, null);
		intent = new Intent();
		intent.setClass(TabNewsActivity.this, TabNewsTopActivity.class);
		intent.putExtra("title", HttpRequestUrl.URL_NEWS_TOP);
		intent.putExtra("news.newsTitle", titlelist.get(0).getTitleId());
		page = getLocalActivityManager().startActivity("activity1", intent)
				.getDecorView();
		layout_news_main.removeAllViews();
		layout_news_main.addView(page, params);

	}

	private void getTitles() {
		// TODO Auto-generated method stub

		new AsyncTask<Void, Void, Boolean>() {
			protected void onPreExecute() {
				dialog.show();
			};

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					TabNewsActivity.this.params.clear();
					TabNewsActivity.this.params.put("news.getTitle", "titles");
					String data = newsService.requestByPost(
							HttpRequestUrl.url(HttpRequestUrl.NEWS_SELECT),
							TabNewsActivity.this.params);
					titlelist = newsService.parseTitles(data);
					int i = titlelist.size();
					return true;
				} catch (Exception e) {
					return false;
				}

			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				// super.onPostExecute(result);
				if (result) {
					try {
						initViews();
						loadFirst();
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "请检查网络连接！",
								2000).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请检查网络连接！", 2000)
							.show();
				}
			}
		}.execute();
	}

	private void initViews() {
		layout_news_main = (RelativeLayout) findViewById(R.id.layout_news_main);
		LinearLayout linearlay = (LinearLayout) findViewById(R.id.layout);
		RadioGroup group = new RadioGroup(getApplicationContext());
		group.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < titlelist.size(); i++) {
			String title = titlelist.get(i).getTitleName();
			final String id = titlelist.get(i).getTitleId();
			RadioButton button = new RadioButton(getApplicationContext());
			button.setButtonDrawable(android.R.color.transparent);
			button.setId(Integer.valueOf(titlelist.get(i).getTitleId()));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(25, 5, 25, 5);
			// button.setPadding(0, 0, 15, 0);
			params.gravity = Gravity.CENTER;
			group.setLayoutParams(params);
			button.setLayoutParams(params);

			button.setPadding(5, 10,5 , 10);

			// button.setWidth(150);
			// button.setHeight(90);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// String mid =titlelist.get(id).getTitleId();
					LayoutParams params = new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
					inflater = TabNewsActivity.this.getLayoutInflater();
					// page = inflater.inflate(R.layout.layout_news_top, null);
					Intent intent = new Intent();
					intent.setClass(TabNewsActivity.this,
							TabNewsTopActivity.class);
					// intent.putExtra("title", HttpRequestUrl.URL_NEWS_TOP);
					intent.putExtra("news.newsTitle", id);
					page = TabNewsActivity.this.getLocalActivityManager()
							.startActivity("activity1" + id, intent)
							.getDecorView();
					layout_news_main.removeAllViews();
					layout_news_main.addView(page, params);
					// layout_news_main.invalidate();
				}
			});
			button.setBackgroundResource(R.drawable.title_select);
			button.setText("  " + title + "  ");

			button.setTextColor(Color.BLACK);
			button.setGravity(Gravity.CENTER);
			group.addView(button);

		}
		// group.ggetChildAt(1).setSelected(true);
		linearlay.addView(group);
		dialog.dismiss();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		inflater = getLayoutInflater();
		// page = inflater.inflate(R.layout.layout_news_top, null);
		Intent intent = new Intent();
		intent.setClass(TabNewsActivity.this, TabNewsTopActivity.class);
		// intent.putExtra("title", HttpRequestUrl.URL_NEWS_TOP);
		intent.putExtra("news.newsTitle", id);
		page = getLocalActivityManager().startActivity("activity1", intent)
				.getDecorView();
		layout_news_main.removeAllViews();
		layout_news_main.addView(page, params);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)
					.setMessage("是否退出？")
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
