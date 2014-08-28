package com.school.news.vo;

/**
 * @author Administrator
 * 
 */
public class MyComment {
	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getCommentsDate() {
		return commentsDate;
	}

	public void setCommentsDate(String commentsDate) {
		this.commentsDate = commentsDate;
	}

	public String getpComId() {
		return pComId;
	}

	public void setpComId(String pComId) {
		this.pComId = pComId;
	}

	public String getpUserId() {
		return pUserId;
	}

	public void setpUserId(String pUserId) {
		this.pUserId = pUserId;
	}

	public String getpUserName() {
		return pUserName;
	}

	public void setpUserName(String pUserName) {
		this.pUserName = pUserName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	private String comContent;

	@Override
	public String toString() {
		return "MyComment [comContent=" + comContent + ", userName=" + userName
				+ ", comId=" + comId + ", commentsDate=" + commentsDate
				+ ", pComId=" + pComId + ", pUserId=" + pUserId
				+ ", pUserName=" + pUserName + ", userId=" + userId
				+ ", userPhoto=" + userPhoto + "]";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public void setNewsTitile(String newsTitile) {
		this.newsTitile = newsTitile;
	}

	public String getNewsId() {
		return newsId;
	}

	public String getNewsTitile() {
		return newsTitile;
	}

	private String newsId;
	private String newsTitile;
	private String userName;
	private String comId;
	private String commentsDate;
	private String pComId;
	private String pUserId;
	private String pUserName;
	private String userId;
	private String userPhoto;
}
