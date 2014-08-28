package com.school.news.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class HttpRequestService {
	private final static HttpClient httpClient = new DefaultHttpClient();;

	public String loadDataForNormal(String url) {
		String content = "";
		// httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public String requestByPostGetTitle(String url, Map<String, String> params) {

		String content = "";
		try {

			HttpPost request = new HttpPost(url);
			List<NameValuePair> args = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				args.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(args,
					"UTF-8");
			request.setEntity(entity);
			// request.
			// 浏览器去执行这次get请求，返回一个响应对象
			HttpResponse response = httpClient.execute(request);
			// 获取响应码
			int code = response.getStatusLine().getStatusCode();
			Header[] headers = response.getAllHeaders();
			String c = "";
			for (Header h : headers) {
				c += h.getName() + ":" + h.getValue() + "\n";
			}

			if (code == 200) {
				// 读取响应的内容
				content = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;

	}

	public String requestByPost(String url, Map<String, String> params) {
		String content = "";
		try {

			HttpPost request = new HttpPost(url);

			// String tempt="http://192.168.191.1:8080/SecretTest/news.jsp";
			// HttpPost request = new HttpPost(tempt);

			List<NameValuePair> args = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				args.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(args,
					"UTF-8");
			request.setEntity(entity);
			// request.
			// 浏览器去执行这次get请求，返回一个响应对象
			HttpResponse response = httpClient.execute(request);
			// 获取响应码
			int code = response.getStatusLine().getStatusCode();
			Header[] headers = response.getAllHeaders();
			String c = "";
			for (Header h : headers) {
				c += h.getName() + ":" + h.getValue() + "\n";
			}

			if (code == 200) {
				// 读取响应的内容
				content = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	// private Drawable loadImageFromNetwork(String imageUrl) {
	// Drawable drawable = null;
	// try {
	// // 可以在这里通过文件名来判断，是否本地有此图片
	// drawable = Drawable.createFromStream(new URL(imageUrl).openStream(),
	// "image.jpg");
	// } catch (IOException e) {
	// Log.d("test", e.getMessage());
	// }
	// if (drawable == null) {
	// Log.d("test", "null drawable");
	// } else {
	// Log.d("test", "not null drawable");
	// }
	//
	// return drawable;
	// }

	// 获取图像资源
	public void putPhoto(String photoName, File file, Context context) {
		FileOutputStream output = null;
		InputStream input = null;
		try {
			output = new FileOutputStream(file);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HttpClient client = new DefaultHttpClient();
		int len = 0;
		try {
			// HttpResponse response = client.execute(new HttpGet(
			// HttpRequestUrl.URL + photoName));
			HttpResponse response = httpClient.execute(new HttpGet(photoName
					.trim()));
			byte[] buffer = new byte[1024];
			String ss = photoName;

			// Drawable drawable = loadImageFromNetwork(IMAGE_URL);
			// mImageView.setImageDrawable(drawable);

			// output=context.openFileOutput(photoName.split("/")[1],Context.MODE_WORLD_WRITEABLE);
			// output=context.openFileOutput(photoName,Context.MODE_WORLD_WRITEABLE);

			String sss = file.getAbsolutePath();
			if (response.getStatusLine().getStatusCode() == 200) {
				input = response.getEntity().getContent();
				while ((len = input.read(buffer)) != -1) {
					output.write(buffer, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

}
