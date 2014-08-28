package com.school.news;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.school.news.utils.MoveBg;
import com.school.news.vo.Users;

public class MainActivity extends TabActivity {
	TabHost tabHost;
	TabHost.TabSpec tabSpec;
	RadioGroup radioGroup;
	RelativeLayout bottom_layout;
	ImageView img;
	int startLeft;

	int dw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = getIntent();
		dw = getWindowManager().getDefaultDisplay().getWidth();
		bottom_layout = (RelativeLayout) findViewById(R.id.layout_bottom);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("news").setIndicator("News")
				.setContent(new Intent(this, TabNewsActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("topic").setIndicator("Topic")
				.setContent(new Intent(this, TabGongGaoTopActivity.class)));
		tabHost.addTab(tabHost
				.newTabSpec("picture")
				.setIndicator("Picture")
				.setContent(new Intent(this, MyOtherNewsContentActivity1.class)));
		tabHost.addTab(tabHost
				.newTabSpec("follow")
				.setIndicator("Follow")
				.setContent(new Intent(this, MyOtherNewsContentActivity2.class)));
		tabHost.addTab(tabHost
				.newTabSpec("vote")
				.setIndicator("Vote")
				.setContent(new Intent(this, MyOtherNewsContentActivity3.class)));
		tabHost.addTab(tabHost.newTabSpec("user").setIndicator("User")
				.setContent(new Intent(this, MyNewLoginActivity.class)));
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

		img = new ImageView(this);
		Bitmap src = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_front_bg);

		Bitmap bitmap = Bitmap.createScaledBitmap(src, dw / 6, src.getHeight(),
				true);
		img.setImageBitmap(bitmap);
		// img.setImageResource(R.drawable.tab_front_bg);
		bottom_layout.addView(img);
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio_news:
				tabHost.setCurrentTabByTag("news");
				MoveBg.moveFrontBg(img, startLeft, 0, 0, 0);
				startLeft = 0;
				break;
			case R.id.radio_topic:
				tabHost.setCurrentTabByTag("topic");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth(), 0, 0);
				// startLeft = (img.getWidth() - 2);
				startLeft = dw / 6;
				break;
			case R.id.radio_pic:
				tabHost.setCurrentTabByTag("picture");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 2, 0, 0);
				// startLeft = (img.getWidth() - 2) * 2;
				startLeft = dw / 6 * 2;
				break;
			case R.id.radio_follow:
				tabHost.setCurrentTabByTag("follow");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 3, 0, 0);
				startLeft = dw / 6 * 3;
				break;
			case R.id.radio_vote:
				tabHost.setCurrentTabByTag("vote");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 4, 0, 0);
				startLeft = dw / 6 * 4;
				break;
			case R.id.radio_user:
				tabHost.setCurrentTabByTag("user");
				MoveBg.moveFrontBg(img, startLeft, img.getWidth() * 5, 0, 0);
				startLeft = dw / 6 * 5;
				break;
			default:
				break;
			}
		}
	};

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

	@Override
	protected void onDestroy() {

		super.onDestroy();
		Users.user = null;
	}
}