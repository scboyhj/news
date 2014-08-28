package com.school.news.http;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.school.news.vo.Users;

public class HttpUserService extends HttpRequestService {
	public Users parserUser(String data) {
		Users user = null;
		try {
			JSONObject json = new JSONObject(data);
			JSONArray jsonArray = json.getJSONArray("user");
			if (jsonArray.length() > 0) {
				user = new Users();
				json = jsonArray.getJSONObject(0);
				user.setUserId(json.getInt("userId"));
				// user.setUserAddress(json.getString("userAddress"));
				user.setUserPassword(json.getString("userPassword"));
				user.setUserName(json.getString("userName"));
				// user.setUserNumber(json.getString("userNumber"));
				// user.setUserPhoto(json.getString("userPhoto"));
			}

		} catch (Exception e) {
			//e.printStackTrace();
			Log.e("TAG", "出错了");
			user = null;
		}
		return user;
	}
}
