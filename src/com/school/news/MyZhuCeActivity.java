package com.school.news;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;

public class MyZhuCeActivity extends Activity {

	EditText userNameEditText;
	EditText passOneEditText;
	EditText passtwoEditText;
	Button zhuceButton;
	Button cleanButton;
	private Map<String, String> parmes;
	HttpNewsService newsService;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_zhuce);
		dialog = new ProgressDialog(this);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setMessage("数据处理中...");
		newsService = new HttpNewsService();
		parmes = new HashMap<String, String>();
		userNameEditText = (EditText) findViewById(R.id.txtView_phoneNumber);
		passOneEditText = (EditText) findViewById(R.id.txtView_phonePwd);
		passtwoEditText = (EditText) findViewById(R.id.txtView_phonePwd2);
	}

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.register_Btn:
			String username = userNameEditText.getText().toString();
			String passone = passOneEditText.getText().toString();
			String passtwo = passtwoEditText.getText().toString();
			if (username == null | passone == null | passtwo == null
					| username.equals("") | passone.equals("")
					| passtwo.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入完整信息！", 2000)
						.show();
			} else {
				if (!passone.equals(passtwo)) {
					Toast.makeText(getApplicationContext(), "两次密码不一致！", 2000)
							.show();
				} else {

					parmes = new HashMap<String, String>();
					parmes.put("user.userNumber", username);
					parmes.put("user.userPassword", passone);
					parmes.put("user.userName", username);
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

							String data = newsService.requestByPost(
									HttpRequestUrl
											.url(HttpRequestUrl.USER_REGISTER),
									parmes);
							return data;
						}

						@Override
						protected void onPostExecute(String result) {
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							dialog.dismiss();
							if (result.equals("ok")) {
								Toast.makeText(getApplicationContext(),
										"注册成功，请重新登陆!", 2000).show();
								MyZhuCeActivity.this.finish();

							} else {

								Toast.makeText(getApplicationContext(),
										"注册失败,请换个用户名再试!", 2000).show();
							}
						}
					}.execute();

				}
			}

			break;
		case R.id.clear_Btn:
			userNameEditText.setText("");
			passOneEditText.setText("");
			passtwoEditText.setText("");
			break;
		case R.id.backButton:
			this.finish();
			break;
		default:
			break;
		}
	}
}
