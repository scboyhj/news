package com.school.news;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.http.HttpNewsService;

public class MyNewsGongGaoActivity extends Activity {
	// "特大号字体","大号字体","中号字体","小号字体"
	public static final String TEXT_SIZE_VIRY = "特大号字体";
	public static final String TEXT_SIZE_BIG = "大号字体";
	public static final String TEXT_SIZE_CENTER = "中号字体";
	public static final String TEXT_SIZE_LITTLE = "小号字体";
	private HttpNewsService service;
	// private ViewSwitcher switcher;
	private Map<String, String> params;
	private TextView tv_news_details_content;

	private LinearLayout ll_writer, ll_witer_comments_menu;
	String content_url;
	private ProgressDialog dialog;
	private float textSize;
	WebView webView;

	HttpNewsService newsService;
	String commentContent;
	URL url;
	String urlString;

	// LinearLayout waitLayout;
	ProgressDialog downDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_news_top);
		setContentView(R.layout.layout_mygonggaocontent);
		initViews();
		// loadData();

	}

	public void myAction(View v) {
		switch (v.getId()) {
		case R.id.backButton:
			finish();
			break;

		default:
			break;
		}
	}

	private void initViews() {
		dialog = ProgressDialog.show(this, null, "加载中...");
		downDialog = new ProgressDialog(MyNewsGongGaoActivity.this);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		content_url = getIntent().getStringExtra("content_url");
		try {
			url = new URL(content_url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service = new HttpNewsService();
		webView = (WebView) findViewById(R.id.mywebview);
		// webView.getSettings().setPluginsEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("GBK");
		webView.setWebChromeClient(new WebChromeClient());
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
		urlString = url.toString();
		webView.loadUrl(url.toString());

		webView.setDownloadListener(new MyWebViewDownLoadListener());
	}

	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Toast t = Toast.makeText(getApplicationContext(), "需要SD卡。",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.CENTER, 0, 0);
				t.show();
				return;
			}
			DownloaderTask task = new DownloaderTask();

			task.execute(url);
		}

	}

	File Lastfile;

	// 内部类
	private class DownloaderTask extends AsyncTask<String, Void, String> {

		public DownloaderTask() {
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// String url = params[0];
			// Log.i("tag", "url="+url);
			// String fileName = "n.jpg";
			String myurl = params[0];
			// String fileName = myurl.substring(myurl.indexOf("="),
			// myurl.length());
			String fileName = myurl.substring(myurl.indexOf("=") + 1,
					myurl.indexOf("&"));
			// fileName = URLDecoder.decode(fileName);
			// Log.i("tag", "fileName=" + fileName);

			File directory = Environment.getExternalStorageDirectory();
			File SaveDic = new File(directory, "Ewen");
			if (!SaveDic.exists()) {

				SaveDic.mkdirs();

			}
			File file = new File(SaveDic, fileName);
			// String fileRealName = file.getAbsolutePath();
			if (file.exists()) {
				Log.i("tag", "The file has already exists.");
				return fileName;
			}
			try {
				HttpClient client = new DefaultHttpClient();
				// client.getParams().setIntParameter("http.socket.timeout",3000);//设置超时
				HttpGet get = new HttpGet(myurl);
				HttpResponse response = client.execute(get);
				if (HttpStatus.SC_OK == response.getStatusLine()
						.getStatusCode()) {
					HttpEntity entity = response.getEntity();
					InputStream input = entity.getContent();

					writeToSDCard(fileName, input);

					input.close();
					// entity.consumeContent();
					return fileName;
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			downDialog.dismiss();
			if (result == null) {
				Toast.makeText(getApplicationContext(), "连接错误！请稍后再试！",
						Toast.LENGTH_LONG).show();

				return;
			}

			File directory = Environment.getExternalStorageDirectory();
			File SaveDic = new File(directory, "Ewen");
			Lastfile = new File(SaveDic, result);

			Toast.makeText(getApplicationContext(),
					"已保存到" + Lastfile.getAbsolutePath(), 3000).show();;
			new AlertDialog.Builder(MyNewsGongGaoActivity.this)
					.setTitle("提示信息")
					.setMessage("文件已下载 ,是否打开文件？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									Intent intent = getFileIntent(Lastfile);

									startActivity(intent);
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

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			downDialog.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	public Intent getFileIntent(File file) {
		// Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
		Uri uri = Uri.fromFile(file);
		String type = getMIMEType(file);
		Log.i("tag", "type=" + type);
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, type);
		return intent;
	}

	public void writeToSDCard(String fileName, InputStream input) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// File directory = Environment.getExternalStorageDirectory();
			// File file = new File(directory, fileName);
			//
			File directory = Environment.getExternalStorageDirectory();
			File SaveDic = new File(directory, "Ewen");
			if (!SaveDic.exists()) {

				SaveDic.mkdirs();

			}
			File file = new File(SaveDic, fileName);
			// if(file.exists()){
			// Log.i("tag", "The file has already exists.");
			// return;
			// }
			try {
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int j = 0;
				int num = 0;
				while ((j = input.read(b)) != -1) {
					fos.write(b, 0, j);
					num += j;
				}
				int m = num / 1024;
				int l = num / 1024 / 1024;
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Log.i("tag", "NO SDCard.");
		}
	}

	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 依扩展名的类型决定MimeType */
		if (end.equals("pdf")) {
			type = "application/pdf";//
		} else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio/*";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video/*";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image/*";
		} else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
			type = "application/vnd.android.package-archive";
		}
		// else if(end.equals("pptx")||end.equals("ppt")){
		// type = "application/vnd.ms-powerpoint";
		// }else if(end.equals("docx")||end.equals("doc")){
		// type = "application/vnd.ms-word";
		// }else if(end.equals("xlsx")||end.equals("xls")){
		// type = "application/vnd.ms-excel";
		// }
		else {
			// /*如果无法直接打开，就跳出软件列表给用户选择 */
			type = "*/*";
		}
		return type;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dialog != null) {
			dialog.dismiss();
		}
	}

}
