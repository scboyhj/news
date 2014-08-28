package com.school.news.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import android.widget.ListView;

import com.school.news.vo.Comments;
import com.school.news.vo.GongGao;
import com.school.news.vo.MyComment;
import com.school.news.vo.News;
import com.school.news.vo.NewsFilmResource;
import com.school.news.vo.NewsTitle;
import com.school.news.vo.Users;

public class HttpNewsService extends HttpRequestService {
	private JSONObject json = null;
	private JSONArray arrayUrl = null;
	private Comments comment = null;

	public Users parseUser(String data) {
		Users users = null;
		// List<NewsFilmResource> photos = new ArrayList<NewsFilmResource>();
		// News news;
		try {
			json = new JSONObject(data);
			JSONArray jsonArray = json.getJSONArray("user");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					json = jsonArray.getJSONObject(i);
					users = new Users();
					String ss = json.getString("userName");
					users.setUserName(json.getString("userName"));
					// users.setUserId(json.getInt("userId"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<String> parseCount(String data) {

		List<String> counts = new ArrayList<String>();
		try {// {commCount:4,myCollCount:2,sonCommCount:1}
			json = new JSONObject(data);
			String c1 = json.getString("commCount");
			String c2 = json.getString("myCollCount");
			String c3 = json.getString("sonCommCount");
			counts.add(c1);
			counts.add(c2);
			counts.add(c3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counts;
	}

	public List<NewsTitle> parseTitles(String titles) {
		List<NewsTitle> list = new ArrayList<NewsTitle>();
		JSONArray array;
		try {

			// json = new JSONObject(titles);
			// JSONArray jsonArray = json.getJSONArray("titles");
			// if (jsonArray.length() > 0) {
			// for (int i = 0; i < jsonArray.length(); i++) {
			// json = jsonArray.getJSONObject(i);
			//
			// String title = json.getString("title");
			// list.add(title);
			// }
			// }
			array = new JSONArray(titles);
			for (int i = 0; i < array.length(); i++) {
				NewsTitle newsTitle = new NewsTitle();
				JSONObject jsonObject = array.getJSONObject(i);
				String title = jsonObject.getString("newsTypeName");
				String titleId = jsonObject.getString("newsTypeId");
				newsTitle.setTitleName(title);
				newsTitle.setTitleId(titleId);
				list.add(newsTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<MyComment> parseCommentsMyCheck(String data) {
		// HashMap<String, List<News>> hashMap = new HashMap<String,
		// List<News>>();
		List<MyComment> commentsList = new ArrayList<MyComment>();

		MyComment comments;
		try {

			// json = new JSONObject(data);
			// JSONArray jsonArray = json.getJSONArray(data);
			JSONArray jsonArray = new JSONArray(data);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					json = jsonArray.getJSONObject(i);
					comments = new MyComment();
					comments.setComContent(json.getString("comContent"));
					comments.setComId(json.getString("comId"));
					comments.setCommentsDate(json.getString("commentsDate"));
					comments.setNewsId(json.getString("newsId"));
					comments.setNewsTitile(json.getString("newsTitle"));
					comments.setUserPhoto(json.getString("userPhoto"));
					comments.setUserName(json.getString("userName"));
					comments.setpUserName(json.getString("pUserName"));
					comments.setUserId(json.getString("userId"));
					String string = comments.toString();
					commentsList.add(comments);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentsList;
	}

	public List<MyComment> parseComments(String data) {
		// HashMap<String, List<News>> hashMap = new HashMap<String,
		// List<News>>();
		List<MyComment> commentsList = new ArrayList<MyComment>();

		MyComment comments;
		try {

			// json = new JSONObject(data);
			// JSONArray jsonArray = json.getJSONArray(data);
			JSONArray jsonArray = new JSONArray(data);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					json = jsonArray.getJSONObject(i);
					comments = new MyComment();
					comments.setComContent(json.getString("comContent"));
					comments.setComId(json.getString("comId"));
					comments.setCommentsDate(json.getString("commentsDate"));
					comments.setpComId(json.getString("pComId"));
					comments.setpUserId(json.getString("pUserId"));
					comments.setUserPhoto(json.getString("userPhoto"));
					comments.setUserName(json.getString("userName"));
					comments.setpUserName(json.getString("pUserName"));
					comments.setUserId(json.getString("userId"));
					String string = comments.toString();
					commentsList.add(comments);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentsList;
	}

	public List<News> parseNews(String data) {
		// HashMap<String, List<News>> hashMap = new HashMap<String,
		// List<News>>();
		List<News> newsList = new ArrayList<News>();

		News news;
		try {

			// json = new JSONObject(data);
			// JSONArray jsonArray = json.getJSONArray(data);
			JSONArray jsonArray = new JSONArray(data);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					json = jsonArray.getJSONObject(i);
					news = new News();
					news.setCommentsCount(json.getInt("commentsCount"));
					news.setNewsAddress(json.getString("newsAddress"));
					news.setNewsContent(json.getString("newsContent"));

					news.setNewsTime(json.getString("newsData"));

					news.setNewsData(json.getString("newsData"));
					news.setNewsId(json.getInt("newsId"));
					news.setNewsSource(json.getString("newsSource"));
					news.setNewsTitle(json.getString("newsTitle"));
					news.setPhotoUrl(json.getString("photos"));
					String string = news.toString();
					newsList.add(news);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}

	public List<GongGao> parseGongGao(String data) {
		// HashMap<String, List<News>> hashMap = new HashMap<String,
		// List<News>>();
		List<GongGao> gongGaos = new ArrayList<GongGao>();

		GongGao gongGao;
		try {

			// json = new JSONObject(data);
			// JSONArray jsonArray = json.getJSONArray(data);
			JSONArray jsonArray = new JSONArray(data);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					json = jsonArray.getJSONObject(i);
					gongGao = new GongGao();
					gongGao.setAnnouncement_id(json
							.getString("announcement_id"));
					gongGao.setContent(json.getString("content"));
					gongGao.setTitle(json.getString("title"));
					// gongGao.setUrl(json.getString("url"));

					String string = gongGao.toString();
					gongGaos.add(gongGao);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return gongGaos;
	}

	String sss;

	public News loadNews(String data) {
		News news = new News();
		List<NewsFilmResource> photos = new ArrayList<NewsFilmResource>();
		try {
			JSONObject jsonChild;
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				json = jsonArray.getJSONObject(i);
				news.setCommentsCount(json.getInt("commentsCount"));
				news.setNewsAddress(json.getString("newsAddress"));

				news.setNewsContent(json.getString("newsContent"));
				String ss = json.getString("newsContent");
				news.setNewsData(json.getString("newsData"));
				news.setNewsId(json.getInt("newsId"));
				news.setNewsSource(json.getString("newsSource"));
				news.setNewsTitle(json.getString("newsTitle"));
				arrayUrl = new JSONArray(json.getString("photos"));
				if (arrayUrl.length() > 0) {
					for (int j = 0; j < arrayUrl.length(); j++) {
						jsonChild = arrayUrl.getJSONObject(j);
						photos.add(new NewsFilmResource(jsonChild
								.getString("newsFilmResourceUrl")));
					}
				}
				news.setPhotos(photos);
				news.setComments(comments(new JSONArray(json
						.getString("comments"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TAG", "报错了");
		}
		return news;
	}

	private List<Comments> comments(JSONArray arrayUrl) {
		List<Comments> comments = new ArrayList<Comments>();
		JSONObject json;
		try {
			if (arrayUrl.length() > 0) {
				for (int j = 0; j < arrayUrl.length(); j++) {
					json = arrayUrl.getJSONObject(j);
					comments.add(getComments(json));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public List<Comments> parseComments(String data, List<Comments> list) {

		try {
			json = new JSONObject(data);
			arrayUrl = json.getJSONArray("comments");
			if (arrayUrl.length() > 0) {
				for (int j = 0; j < arrayUrl.length(); j++) {
					json = arrayUrl.getJSONObject(j);
					list.add(getComments(json));
				}
			} else {
				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private Comments getComments(JSONObject json) throws Exception {
		comment = new Comments();
		comment.setCommentsContent(json.getString("commentsContent"));
		comment.setCommentsData(json.getString("commentsParserData"));
		comment.setCommentsUserId(json.getInt("commentsUserId"));
		comment.setUserAddress(json.getString("userAddress"));
		comment.setUserName(json.getString("userName"));
		comment.setUserPhoto(json.getString("userPhoto"));
		comment.setCommentsId(json.getInt("commentsId"));
		return comment;
	}
}
