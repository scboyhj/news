package com.school.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WebIpActivity extends Activity {

	EditText editText;
	Button button;
	EditText porteditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myip);
		editText = (EditText) findViewById(R.id.iptext);
		porteditText = (EditText) findViewById(R.id.porttext);
		button = (Button) findViewById(R.id.connect);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WebIpActivity.this, MainActivity.class);

				String c = editText.getText().toString();
				if (c == null || c.trim().equals("")) {
					c = "";
				}
				intent.putExtra("ip", c);
				String cc = porteditText.getText().toString();
				if (cc == null || cc.trim().equals("")) {
					cc = "";
				}
				intent.putExtra("port", cc);
				WebIpActivity.this.startActivity(intent);
				WebIpActivity.this.finish();
			}
		});
	}
}
