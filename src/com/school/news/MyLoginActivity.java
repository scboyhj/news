package com.school.news;

import java.util.HashMap;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.MD5;
import com.school.news.vo.MyUser;

public class MyLoginActivity extends ActivityGroup {
	EditText numeditText;
	EditText passEditText;
	private Map<String, String> parmes;
	HttpNewsService newsService;
	ProgressDialog dialog;
	LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_user_login_lay);
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
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		LayoutInflater inflater = getLayoutInflater();
		View page = inflater.inflate(R.layout.aa_user_main_lay, null);
		intent = new Intent();
		intent.setClass(MyLoginActivity.this, MyUserCenterActivity.class);

		View page2 = getLocalActivityManager().startActivity("activityav",
				intent).getDecorView();
		layout.removeAllViews();
		layout.addView(page2, params);
	}

	public boolean checkInfo(String num, String pass) {
		if (num.trim().equals("") | pass.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	String pass;
	String num;

	public void myAction(View v) {
		pass = passEditText.getText().toString();
		num = numeditText.getText().toString();
		switch (v.getId()) {
		case R.id.zhuce:
			if (checkInfo(pass, num)) {

				parmes = new HashMap<String, String>();
				parmes.put("user.userNumber", num);
				// parmes.put("user.userPassword", pass);
				parmes.put("user.userPassword", MD5.GetMD5Code(pass));
				parmes.put("user.userName", num);
				parmes.put("userFlag", "1");
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

						String data = newsService.requestByPost(HttpRequestUrl
								.url(HttpRequestUrl.USER_REGISTER), parmes);
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
							MyApplication.myUser = new MyUser(num, num);

							goToMyMainPage();

						} else {

							Toast.makeText(getApplicationContext(),
									"注册失败,请换个用户名再试!", 2000).show();
						}
					}
				}.execute();

			} else {
				Toast.makeText(getApplicationContext(), "请把信息补充完整!", 2000)
						.show();
			}
			break;
		case R.id.denglu:
			if (checkInfo(pass, num)) {

				parmes = new HashMap<String, String>();
				parmes.put("user.userNumber", num);
				parmes.put("user.userPassword", MD5.GetMD5Code(pass));

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
							MyApplication.myUser = new MyUser(num, pass);

							SharedPreferences preferences = MyApplication.preferences;
							Editor editor = preferences.edit();
							editor.putString("name", num);
							editor.putString("pass", pass);
							if (editor.commit())
								goToMyMainPage();
							else {

								Toast.makeText(getApplicationContext(),
										"存储错误!", 2000).show();
							}

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

		default:
			break;
		}
	}
}
