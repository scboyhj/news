package com.school.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;

public class MyUserCenterActivity extends ActivityGroup {

	LinearLayout userInfolinearLayout;
	TextView pLTextView;
	TextView sCTextView;
	TextView hFTextView;
	TextView nameTextView;
	HttpNewsService newsService;
	private Map<String, String> parmes;
	ProgressDialog dialog;
	WebView weatherview;
	TextView zhuxiaoTextView;

	public void goToLoginPage() {
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		LayoutInflater inflater = getLayoutInflater();
		View page = inflater.inflate(R.layout.aa_user_main_lay, null);
		intent = new Intent();
		intent.setClass(MyUserCenterActivity.this, MyNewLoginActivity.class);

		View page2 = getLocalActivityManager().startActivity("activityavb",
				intent).getDecorView();
		layout.removeAllViews();
		layout.addView(page2, params);
	}

	LinearLayout layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_user_main_lay);
		layout = (LinearLayout) findViewById(R.id.user_main);
		pLTextView = (TextView) findViewById(R.id.m_pl_num);
		sCTextView = (TextView) findViewById(R.id.m_sc_text);
		hFTextView = (TextView) findViewById(R.id.m_fh_text);
		nameTextView = (TextView) findViewById(R.id.m_name_text);
		zhuxiaoTextView = (TextView) findViewById(R.id.zhuxiao);
		zhuxiaoTextView.setVisibility(View.GONE);
		// zhuxiaoTextView.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// SharedPreferences preferences = MyApplication.preferences;
		// Editor editor = preferences.edit();
		// editor.putString("name", "");
		// editor.putString("pass", "");
		// MyApplication.myUser = null;
		//
		// if (editor.commit())
		//
		// {
		// goToLoginPage();
		// }
		// }
		// });
		nameTextView.setText(MyApplication.myUser.getUserName());
		newsService = new HttpNewsService();
		weatherview = (WebView) findViewById(R.id.weather);
		weatherview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				weatherview.loadUrl(url);
				return true;
			}
		});
		weatherview.getSettings().setJavaScriptEnabled(true);
		weatherview.getSettings().setDefaultTextEncodingName("utf-8");
		String str = "<script src=\"http://lab.wangyuanwai.com/weather/v3/jquery.weather.build.min.js?parentbox=body&moveArea=all&zIndex=1&move=1&drag=1&autoDrop=0&styleSize=big&style=default&time=1407649622&area=client\"></script>";
		weatherview.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
		weatherview.setBackgroundColor(Color.TRANSPARENT);
		dialog = new ProgressDialog(getParent());
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setMessage("请稍等...");
		dialog.show();
		getInfo();

	}

	Intent intent;

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.l_ping:
			startActivity(new Intent(this, CheckMyCommentsActivity.class));
			break;
		case R.id.l_hui:
			startActivity(new Intent(this, CheckHuiFuMyCommentsActivity.class));
			break;
		case R.id.l_shou:
			startActivity(new Intent(this, CheckShouCangNewsActivity.class));
			break;
		case R.id.about:
			startActivity(new Intent(this, MyAboutActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(getParent())
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getInfo();
	}

	public void getInfo() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

				parmes = new HashMap<String, String>();
				parmes.put("status", "getThreeCount");
				parmes.put("userId", MyApplication.myUser.getUserName());

			}

			@Override
			protected String doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				String data = newsService.requestByPost(
						HttpRequestUrl.url(HttpRequestUrl.USER_GET_COUNT),
						parmes);
				if (!data.equals("")) {
					return data;
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();
				if (result != null) {
					List<String> list = newsService.parseCount(result);
					pLTextView.setText(list.get(0));
					sCTextView.setText(list.get(1));
					hFTextView.setText(list.get(2));

				} else {
					Toast.makeText(getApplicationContext(), "加载失败", 2000)
							.show();
				}

			}
		}.execute();
	}

}