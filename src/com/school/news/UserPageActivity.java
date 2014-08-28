package com.school.news;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.vo.Users;

public class UserPageActivity extends Activity {
	ViewSwitcher viewSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_userpage);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		initViews();
		requestRSSFeed();
	}

	String content = "";

	private void requestRSSFeed() {
		// TODO Auto-generated method stub

		Thread t = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					data();
					String ss=users.getUserName();
					if (content.trim().equals("")) {
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
	Users users=null;
	private void data() {
		params.clear();
		//params.put("news.newsTitle", HttpRequestUrl.URL_TOPIC);
		content = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.USER_INFO), params);
	    users = newsService.parseUser(content);
	    
	}

	TextView UserNametextView;

	private void initViews() {
		// TODO Auto-generated method stub

		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher_tab_user);

	

		viewSwitcher.addView(getLayoutInflater().inflate(
				R.layout.user_infopage, null));
		viewSwitcher.addView(getLayoutInflater().inflate(
				R.layout.layout_progress_page, null));
		viewSwitcher.showNext();
		// listView.setOnItemClickListener(listener);
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				// adapter = new MyAdapter(list, TabTopicActivity.this);
				// listView.setOnItemClickListener(listener);
				// listView.setAdapter(adapter);
				viewSwitcher.showPrevious();
				View view = getLayoutInflater().inflate(R.layout.user_infopage, null);
				UserNametextView = (TextView) view.findViewById(R.id.textViewUserName);
				String sss=users.getUserName();
				UserNametextView.setText(sss);
			}
		};
	};
	HttpNewsService newsService;
	HashMap<String, String> params;
}
