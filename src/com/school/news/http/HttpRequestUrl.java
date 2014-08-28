package com.school.news.http;

public class HttpRequestUrl {
	private HttpRequestUrl() {
	};

	//
	// public static final String URL =
	// "http://113.251.161.199:8080/NewsPublishbeta/";
	// IP="113.250.158.28";65.49.54.138//113.251.219.1
	// public static String IP = "192.168.191.2";
	// public static String IP = "113.250.155.45";
	// public static String IP = "192.168.155.1";
	// public static String IP = "192.168.191.1";
	public static String IP = "192.168.173.1";
	public static String port = "8080";
	public static final String URL = "http://" + IP + ":" + port + "/"
			+ "NewsPublishbeta/";
	private static final String ACTION = ".action";
	// 用户的请求地址
	private static final String USER = "new/userClientAction!";
	public static final String USER_LOGIN = USER + "login";
	public static final String USER_REGISTER = USER + "register";
	public static final String USER_COLLECT = USER + "collect";
	public static final String USER_INFO = USER + "info";

	private static final String USER_New = "new/CountClientAction!";
	public static final String USER_GET_COUNT = USER_New + "selectCount";

	// 头条
	public static final String URL_NEWS_TOP = "头条";
	// 体育
	public static final String URL_NEWS_SPORT = "体育";
	// 娱乐
	public static final String URL_NEWS_PLAY = "娱乐";
	// 财经
	public static final String URL_NEWS_FINANCE = "财经";
	// 科技
	public static final String URL_NEWS_SCIENCE = "科技";

	// 国内
	public static final String URL_NEWS_DOMESTIC = "国内";
	// 军事
	public static final String URL_NEWS_MILITARY = "军事";
	// 国际
	public static final String URL_NEWS_INTERNATIONAL = "国际";
	// 社会
	public static final String URL_NEWS_COMMUNITY = "社会";
	// 深度
	public static final String URL_NEWS_DEPTH = "深度";
	// 彩票
	public static final String URL_NEWS_TICKET = "彩票";
	// 电影
	public static final String URL_NEWS_FILM = "电影";
	// 音乐
	public static final String URL_NEWS_MUSIC = "音乐";
	// IT
	public static final String URL_NEWS_IT = "IT";
	// 汽车
	public static final String URL_NEWS_CAR = "汽车";
	// 数码
	public static final String URL_NEWS_DIGITAL = "数码";

	// 网易话题
	public static final String URL_TOPIC = "话题";
	// 网易图片
	public static final String URL_PICTURE = "图片";
	// 网易跟帖
	public static final String URL_FOLLOW = "跟帖";
	// 网易投票
	public static final String URL_VOTE = "投票";
	// 用户名字
	public static final String USER_NAME = "姓名";
	// 新闻
	private static final String NEWS = "new/newsClientAction!";
	public static final String NEWS_SELECT = NEWS + "selectNews";
	public static final String NEWS_LOAD = NEWS + "load";
	public static final String NEWS_COMMENTS = NEWS + "comments";
	public static final String NEWS_PIC = NEWS + "pic";
	public static final String NEWS_GET_TITLES = NEWS + "title";
	// 评论
	private static final String COMMENTS = "new/commentsClientAction!";
	public static final String COMMENTS_SELECT = COMMENTS + "selectNews";
	public static final String COMMENTS_ADD = COMMENTS + "add";
	// 收藏
	private static final String Collection = "new/CollectionClientAction!";
	public static final String Collection_ADD = Collection + "selectCollection";

	// 拼接地址
	public static String url(String method) {
		StringBuffer buffer = new StringBuffer(URL);
		buffer.append(method);
		buffer.append(ACTION);
		String ss = buffer.toString();
		return ss;
	}
}
