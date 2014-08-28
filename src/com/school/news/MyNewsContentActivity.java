package com.school.news;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.vo.News;

public class MyNewsContentActivity extends Activity {
	// "特大号字体","大号字体","中号字体","小号字体"
	public static final String TEXT_SIZE_VIRY = "特大号字体";
	public static final String TEXT_SIZE_BIG = "大号字体";
	public static final String TEXT_SIZE_CENTER = "中号字体";
	public static final String TEXT_SIZE_LITTLE = "小号字体";
	private HttpNewsService service;
	// private ViewSwitcher switcher;
	private Map<String, String> params;
	private TextView tv_news_details_content;
	TextView genTieTextView;
	private News news;
	ImageButton imageButton;
	private LinearLayout ll_writer, ll_witer_comments_menu;
	String content_url;
	private ProgressDialog dialog;
	private float textSize;
	WebView webView;
	EditText commentEditText;
	String commentCount;
	HttpNewsService newsService;
	String commentContent;
	ImageButton shouCangimageButton;

	// LinearLayout waitLayout;
	public void myAction(View c) {
		switch (c.getId()) {
		case R.id.backButton:
			finish();
			break;
		case R.id.myShare:

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT,
					"我在\"E闻\"发现的了一篇不错的新闻，大家快来看看哦！新闻地址戳这里：" + s);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getTitle()));

			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_news_top);
		setContentView(R.layout.layout_mynewscontent);
		initViews();
		// loadData();

	}

	private void initViews() {
		dialog = ProgressDialog.show(this, null, "加载中...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		content_url = getIntent().getStringExtra("content_url");
		commentCount = getIntent().getStringExtra("content_comment");

		genTieTextView = (TextView) findViewById(R.id.genTieText);
		genTieTextView.setText(commentCount + "+ 人跟帖");
		genTieTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("id", content_url);
				intent.putExtra("num", commentCount);
				intent.setClass(getApplicationContext(),
						MyCommentsActivity.class);
				startActivity(intent);

			}
		});

		imageButton = (ImageButton) findViewById(R.id.tijiaoButton);
		commentEditText = (EditText) findViewById(R.id.pingLunEdit);
		shouCangimageButton = (ImageButton) findViewById(R.id.shoucangButton);

		shouCangimageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (MyApplication.myUser == null) {
					Toast.makeText(getApplicationContext(), "请先登录", 2000)
							.show();
				} else {

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

							return insertCollection();
						}

						@Override
						protected void onPostExecute(Boolean result) {
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							dialog.dismiss();
							if (result) {
								Toast.makeText(getApplicationContext(),
										"收藏成功！", 2000).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"收藏失败！", 2000).show();
							}
						}

					}.execute();
				}
			}
		});

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (MyApplication.myUser == null) {
					Toast.makeText(getApplicationContext(), "请先登录", 2000)
							.show();
				} else {
					commentContent = commentEditText.getText().toString();
					if (commentContent == null || commentContent.equals("")) {
						Toast.makeText(getApplicationContext(), null, 2000)
								.show();
					} else {

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
								return insertIntoComment(commentContent);
							}

							@Override
							protected void onPostExecute(Boolean result) {
								// TODO Auto-generated method stub
								super.onPostExecute(result);
								dialog.dismiss();
								commentEditText.setText("");
								if (result) {
									Toast.makeText(getApplicationContext(),
											"评论成功！", 2000).show();
								} else {
									Toast.makeText(getApplicationContext(),
											"评论失败！", 2000).show();
								}
							}
						}.execute();
					}
				}

			}
		});
		service = new HttpNewsService();
		webView = (WebView) findViewById(R.id.mywebview);
		// webView.getSettings().setPluginsEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("GBK");

		webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String myurl) {
				// handler.sendEmptyMessage(1);
				webView.loadUrl(myurl);

				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				dialog.hide();
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {

		});

		s = "http://" + HttpRequestUrl.IP + ":" + HttpRequestUrl.port
				+ "/NewsPublishbeta/news.jsp?news.newsId=" + content_url;
		webView.loadUrl(s);

	}

	String s;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public boolean insertIntoComment(String str) {
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "insertComments");
		params.put("newsId", content_url);
		params.put("pId", "0");

		params.put("content", str);
		// params.put("userId", "123");//此处传的是userName
		params.put("userId", MyApplication.myUser.getUserName());// 此处传的是userName
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_ADD), params);

		if (data.equals("1"))
			return true;
		else
			return false;

	}

	public boolean insertCollection() {
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "insertCollection");
		params.put("newsId", content_url);
		params.put("userId", MyApplication.myUser.getUserName());

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.Collection_ADD), params);

		if (data.equals("1"))
			return true;
		else
			return false;

	}

}
