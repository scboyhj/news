package com.school.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyAboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_about_us);
	}

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.backButton:
			this.finish();
			break;

		default:
			break;
		}
	}
}
