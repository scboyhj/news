package com.school.news.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.vo.News;

public class ActivityUtil {

	private HttpNewsService service;
	private Map<String, String> parmes;
	public static final int COUNT = 20;

	// 提示
	public static void toast(Context context, String msg) {
		Toast.makeText(context, msg, 1000).show();
	}

	public List<News> nextPage(List<News> listOldNews, int startIndex, int endIndex, String title) {

		// if (listOldNews == null) {
		// return null;
		// }
		// if (listOldNews.size() % COUNT != 0 && listOldNews.size() < COUNT) {
		// return null;
		// }
		service = new HttpNewsService();
		parmes = new HashMap<String, String>();
		if (title != null) {
			parmes.put("news.newsTitle", title);
		}
		parmes.put("news.startIndex", startIndex + "");
		parmes.put("news.endIndex", endIndex + "");
		String data = service.requestByPost(HttpRequestUrl.url(HttpRequestUrl.NEWS_SELECT), parmes);
		List<News> list = service.parseNews(data);
		if (list.size() > 0) {
			for (News news : list) {
				listOldNews.add(news);
			}
			return listOldNews;
		} else {
			return listOldNews;
		}
	}

}
