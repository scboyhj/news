package com.school.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.school.news.adapter.MyCommentsAdapter;
import com.school.news.app.MyApplication;
import com.school.news.http.HttpNewsService;
import com.school.news.http.HttpRequestUrl;
import com.school.news.utils.ActivityUtil;
import com.school.news.view.MyListView;
import com.school.news.view.MyListView.OnRefreshListener;
import com.school.news.vo.MyComment;

public class MyCommentsActivity extends Activity implements
		MyCommentsAdapter.IPopShow, MyCommentsAdapter.IShanChu {

	private MyListView listView;
	// private ViewSwitcher viewSwitcher;
	private Map<String, String> params;
	private HttpNewsService newsService;
	private ImageView testView;
	private Button button;
	private ActivityUtil activityUtil;
	private List<MyComment> commentsList;
	private List<MyComment> commentsListtemp;
	String newsId;
	ProgressDialog dialog;
	private LinearLayout pLayout;
	String plNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_news_comment);
		pLayout = (LinearLayout) findViewById(R.id.linear);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		newsId = getIntent().getStringExtra("id");
		plNum = getIntent().getStringExtra("num");
		TextView textView = (TextView) findViewById(R.id.genTieTextNum);
		textView.setText(plNum + "+ ");
		initStaticViews();
		requestRSSFeed();
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

	private void data() {
		params = new HashMap<String, String>();
		params.clear();
		params.put("id", newsId);
		params.put("status", "getComByNewId");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_SELECT), params);
		commentsList = newsService.parseComments(data);
		int li = commentsList.size();

	}

	private void dataThen() {
		params = new HashMap<String, String>();
		params.clear();
		params.put("id", newsId);
		params.put("status", "getComByPId");
		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_SELECT), params);
		commentsListtemp = newsService.parseComments(data);
		// int li = commentsListtemp.size();

	}

	MyCommentsAdapter commentsAdapter;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				commentsAdapter = new MyCommentsAdapter(commentsList,
						getApplicationContext(), MyCommentsActivity.this);
				listView.setAdapter(commentsAdapter);
			} else {
				Toast.makeText(getApplicationContext(), "评论为空！", 2000).show();
			}
			dialog.hide();
		};
	};

	private void requestRSSFeed() {
		Thread t = new Thread() {
			@Override
			public void run() {
				// super.run();
				try {

					data();// 获得数据
					if (commentsList.size() == 0) {
						handler.sendEmptyMessage(-1);
					} else {
						handler.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void initStaticViews() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(this, null, "评论加载中...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		params = new HashMap<String, String>();

		listView = (MyListView) findViewById(R.id.list);

		listView.setCacheColorHint(Color.argb(0, 0, 0, 0));

		button = new Button(this);
		button.setText("查看更多的评论");

		activityUtil = new ActivityUtil();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AsyncTask<Void, Void, Boolean>() {
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						dialog.show();
					}

					@Override
					protected Boolean doInBackground(Void... arg0) {
						// TODO Auto-generated method stub

						dataThen();

						return null;

					}

					@Override
					protected void onPostExecute(Boolean result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						dialog.dismiss();
						if (commentsListtemp != null
								&& commentsListtemp.size() > 0) {
							for (int i = 0; i < commentsListtemp.size(); i++) {
								commentsList.add(commentsListtemp.get(i));

							}
							commentsAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(getApplicationContext(), "没有更多数据了！",
									2000).show();
						}

					}
				}.execute();

			}
		});

		listView.addFooterView(button);
		listView.setonRefreshListener(refreshListener);

		newsService = new HttpNewsService();

	}

	private OnRefreshListener refreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			new AsyncTask<Map<String, String>, Integer, List<MyComment>>() {

				@Override
				protected List<MyComment> doInBackground(
						Map<String, String>... params) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					data();
					return commentsList;
				}

				@Override
				protected void onPostExecute(List<MyComment> result) {
					button.setHint(21 + "");

					commentsAdapter = new MyCommentsAdapter(commentsList,
							getApplicationContext(), MyCommentsActivity.this);
					listView.setAdapter(commentsAdapter);

					// listView.setAdapter(commentsAdapter);
					listView.onRefreshComplete();
				}

			}.execute();
		}
	};
	PopupWindow popupWindow;
	EditText contentEditText;
	Dialog hfdialog;

	public void showDialog(String userName, final String comId,
			final String torealName) {
		hfdialog = new Dialog(this);
		View view = getLayoutInflater().inflate(R.layout.aa_huifu_dialog, null);
		hfdialog.setTitle(" 评论回复");
		hfdialog.setContentView(view);
		TextView myuserTextView = (TextView) view.findViewById(R.id.f_myuser);
		TextView touserTextView = (TextView) view.findViewById(R.id.f_mytouser);
		TextView fhTextView = (TextView) view.findViewById(R.id.f_fh);
		Button cancelButton = (Button) view.findViewById(R.id.f_cancel);
		Button tijiaolButton = (Button) view.findViewById(R.id.f_tijiao);
		contentEditText = (EditText) view.findViewById(R.id.f_ed);
		myuserTextView.setText(userName);
		if (torealName == null | torealName.equals("")) {
			touserTextView.setVisibility(View.INVISIBLE);
			fhTextView.setVisibility(View.INVISIBLE);
		} else {
			touserTextView.setText(torealName);
		}

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hfdialog.dismiss();
			}
		});
		tijiaolButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String str = contentEditText.getText().toString();
				if (str.trim().equals("")) {
					Toast.makeText(getApplicationContext(), "是输入回复内容！", 2000)
							.show();
				} else {
					hfdialog.dismiss();
					insertFuHuiComment(str, comId);// (str, toUserName);
				}
			}
		});
		hfdialog.show();
	}

	@Override
	public void ShowPop(String userName, final String comId,
			final String torealName) {
		showDialog(userName, comId, torealName);
	}

	// @Override
	// public void ShowPop(String userName, final String comId,
	// final String torealName) {
	// // TODO Auto-generated method stub
	// View view = getLayoutInflater()
	// .inflate(R.layout.aa_popo_show_lay, null);
	// popupWindow = new PopupWindow(getApplicationContext());
	// popupWindow.setAnimationStyle(R.style.popwin_anim_style);
	// popupWindow.setContentView(view);
	// popupWindow.setFocusable(true);
	// popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
	// popupWindow.setWidth(LayoutParams.FILL_PARENT);
	// popupWindow.setOutsideTouchable(true);
	// popupWindow.showAtLocation(pLayout, Gravity.CENTER, 0, getWindow()
	// .getWindowManager().getDefaultDisplay().getHeight() / 2);
	// popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
	// popupWindow
	// .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	// TextView nametextView = (TextView) view.findViewById(R.id.userName);
	// TextView tijiaotextView = (TextView) view.findViewById(R.id.tijiao);
	// TextView canceltextView = (TextView) view.findViewById(R.id.cancel);
	// canceltextView.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// popupWindow.dismiss();
	// }
	// });
	// editText2 = (EditText) view.findViewById(R.id.contenttext);
	//
	// TextView fh = (TextView) view.findViewById(R.id.fh);
	// TextView p2 = (TextView) view.findViewById(R.id.p2);
	//
	// if (torealName == null | torealName.equals("")) {
	// fh.setVisibility(View.INVISIBLE);
	// p2.setVisibility(View.INVISIBLE);
	// } else {
	// p2.setText(torealName);
	// }
	//
	// nametextView.setText(userName);
	// // if (!userName.equals("")) {
	// // editText.setText(toUserName);
	// // }
	// canceltextView.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// popupWindow.dismiss();
	// }
	// });
	// tijiaotextView.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	//
	// String str = editText2.getText().toString();
	// if (str.trim().equals("")) {
	// Toast.makeText(getApplicationContext(), "是输入回复内容！", 2000)
	// .show();
	// } else {
	// insertFuHuiComment(str, comId);// (str, toUserName);
	// }
	// }
	// });
	//
	// }
	EditText editText2;

	public boolean sendComment(String str) {
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "insertComments");
		params.put("newsId", newsId);
		params.put("pId", "0");
		params.put("content", str);
		params.put("userId", MyApplication.myUser.getUserName());// 传的是userName

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_ADD), params);

		if (data.equals("1"))
			return true;
		else
			return false;

	}

	public boolean sendHuiFuComment(String str, String pId) {
		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "insertComments");
		params.put("newsId", newsId);
		params.put("pId", pId);
		params.put("content", str);
		params.put("userId", MyApplication.myUser.getUserName());// 传的是userName

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_ADD), params);

		if (data.equals("1"))

			return true;
		else
			return false;

	}

	public boolean deleteComment(String id) {

		newsService = new HttpNewsService();
		params = new HashMap<String, String>();
		params.clear();
		params.put("status", "deleteComments");
		params.put("id", id);

		String data = newsService.requestByPost(
				HttpRequestUrl.url(HttpRequestUrl.COMMENTS_ADD), params);

		if (data.equals("1"))

			return true;
		else
			return false;

	}

	public void insertComment(final String strr) {

		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return sendComment(strr);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.hide();
				if (result) {
					Toast.makeText(getApplicationContext(), "评论成功！", 2000)
							.show();
					ListUpdate();
				} else {
					Toast.makeText(getApplicationContext(), "评论失败！", 2000)
							.show();
				}
			}
		}.execute();

	}

	public void ListUpdate() {
		new AsyncTask<Void, Void, Boolean>() {
			protected void onPreExecute() {
				dialog.show();
			};

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				data();
				return null;
			}

			protected void onPostExecute(Boolean result) {

				commentsAdapter = new MyCommentsAdapter(commentsList,
						getApplicationContext(), MyCommentsActivity.this);
				commentsAdapter.notifyDataSetChanged();
				listView.setAdapter(commentsAdapter);
				dialog.dismiss();
				// listView.setAdapter(commentsAdapter);
				listView.onRefreshComplete();
			};
		}.execute();
	}

	public void insertFuHuiComment(final String strr, final String toUserName) {

		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return sendHuiFuComment(strr, toUserName);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.hide();
				if (result) {
					ListUpdate();
					Toast.makeText(getApplicationContext(), "评论成功！", 2000)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "评论失败！", 2000)
							.show();
				}
			}
		}.execute();

	}

	@Override
	public void DeleteComment(final String id) {
		// TODO Auto-generated method stub

		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return deleteComment(id);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.hide();
				if (result) {
					ListUpdate();
					Toast.makeText(getApplicationContext(), "删除成功！", 2000)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), "删除失败！", 2000)
							.show();
				}
			}
		}.execute();

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
