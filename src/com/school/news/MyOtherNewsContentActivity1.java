package com.school.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Toast;

public class MyOtherNewsContentActivity1 extends Activity {
	WebView view;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mynewsothercontent);
		dialog = ProgressDialog.show(this, null, "正在加载，请稍等...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		view = (WebView) findViewById(R.id.mywebview);
		//view.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		view.getSettings().setSupportZoom(true);

		view.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				// super.onReceivedError(view, errorCode, description,
				// failingUrl);
				view.loadData("cuole", "text/html", "utf-8");

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				dialog.dismiss();

			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
		view.loadUrl("http://news.sohu.com/");
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

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.qian:
			if (view.canGoBack()) {
				view.goBack();
			} else {
				Toast.makeText(getApplicationContext(), "无效操作！", 2000).show();
			}
			break;
		case R.id.hou:
			if (view.canGoForward()) {
				view.goForward();
			} else {
				Toast.makeText(getApplicationContext(), "无效操作！", 2000).show();
			}
			break;

		default:
			break;
		}
	}
}
