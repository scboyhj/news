package com.school.news.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.school.news.R;
import com.school.news.vo.MyUser;

public class MyApplication extends Application {
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options;
	private static Application instance;
	public static MyUser myUser = null;
	public static SharedPreferences preferences;

	public static void initImageLoader(Context context) {

		// 这是自定义的配置调整。你可以调整每一个选项，你可以调谐他们中的一些人，或者你可以创建默认的配置
		// ImageLoaderConfiguration.createDefault(this)
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // 不是常见的
				.build();
		// 配置初始化类
		imageLoader = ImageLoader.getInstance();// .init(config);
		imageLoader.init(config);
	}

	public MyApplication() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		preferences = getSharedPreferences("info", MODE_PRIVATE);
		String userName = preferences.getString("name", "");
		String userPass = preferences.getString("pass", "");
		if (!userName.equals("")) {
			myUser = new MyUser(userName, userName);
		}
		initImageLoader(getApplicationContext());
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.morenshow).cacheInMemory()
				.cacheOnDisc().build();
	}
}
