package com.school.news;

import java.util.HashMap;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.vo.MyUser;

public class MyNewLoginActivity extends ActivityGroup {

	Button zhuceButton;

	EditText numeditText;
	EditText passEditText;
	private Map<String, String> parmes;
	HttpNewsService newsService;
	ProgressDialog dialog;
	LinearLayout layout;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_my_login);
		zhuceButton = (Button) findViewById(R.id.zhuceBtn);
		title = (TextView) findViewById(R.id.title);
		settintAlphaAnimation();
		String tmp = "没账号怎么办?";
		SpannableString sp = new SpannableString("没账号怎么办?");
		// 设置高亮样式二
		sp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, tmp.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		TextView appEditor = (TextView) findViewById(R.id.text);
		appEditor.setText(sp);
		layout = (LinearLayout) findViewById(R.id.total);
		dialog = new ProgressDialog(this);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setMessage("数据处理中...");
		newsService = new HttpNewsService();
		numeditText = (EditText) findViewById(R.id.et_number);
		passEditText = (EditText) findViewById(R.id.et_password);
		if (MyApplication.myUser != null) {

			goToMyMainPage();
		}
	}

	Intent intent;

	public void goToMyMainPage() {
		title.setText("用户中心");
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		LayoutInflater inflater = getLayoutInflater();
		View page = inflater.inflate(R.layout.aa_user_main_lay, null);
		intent = new Intent();
		intent.setClass(MyNewLoginActivity.this, MyUserCenterActivity.class);

		View page2 = getLocalActivityManager().startActivity("activityav",
				intent).getDecorView();
		layout.removeAllViews();
		layout.addView(page2, params);
	}

	String pass;
	String num;

	public boolean checkInfo(String num, String pass) {
		if (num.trim().equals("") | pass.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public void myAction(View v) {
		pass = passEditText.getText().toString();
		num = numeditText.getText().toString();
		switch (v.getId()) {
		case R.id.denglu:
			if (checkInfo(pass, num)) {

				parmes = new HashMap<String, String>();
				parmes.put("user.userNumber", num);
				parmes.put("user.userPassword", pass);

				new AsyncTask<Void, Void, String>() {
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						dialog.show();
					}

					@Override
					protected String doInBackground(Void... arg0) {
						// TODO Auto-generated method stub
						String data = newsService.requestByPost(
								HttpRequestUrl.url(HttpRequestUrl.USER_LOGIN),
								parmes);
						return data;
					}

					@Override
					protected void onPostExecute(String result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						dialog.dismiss();
						if (result.equals("ok")) {
							Toast.makeText(getApplicationContext(), "登录成功!",
									2000).show();
							MyApplication.myUser = new MyUser(pass, num);
							goToMyMainPage();

						} else {

							Toast.makeText(getApplicationContext(),
									"您输入的密码不正确!", 2000).show();
						}
					}
				}.execute();

			} else {
				Toast.makeText(getApplicationContext(), "请把信息补充完整!", 2000)
						.show();
			}
			break;
		case R.id.zhuceBtn:

			startActivity(new Intent(this, MyZhuCeActivity.class));
			break;
		default:
			break;
		}
	}

	private void settintAlphaAnimation() {
		// 闪烁
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
		alphaAnimation.setDuration(2500);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		zhuceButton.setAnimation(alphaAnimation);
		alphaAnimation.start();
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
