package com.school.news.vo;

import java.util.ArrayList;
import java.util.List;

public class News {

	private int type;// type=1 1图 ;type=2 3图
	private Integer newsId;
	private String newsTitle;

	private String newsTime;

	private String newsContent;
	private String newsAddress;
	private String newsSource;
	private String newsData;
	private Integer newsFlag;
	private Integer commentsCount;// 跟帖数量

	// public List<String> photoUrls = new ArrayList<String>();
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}

	public String getNewsTime() {
		return newsTime;
	}

	private List<NewsFilmResource> photos = new ArrayList<NewsFilmResource>();
	private List<Comments> comments = new ArrayList<Comments>();
	private String photoUrl;

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public News() {
	}

	public News(Integer newsId, String newsTitle, String newsContent,
			String newsAddress, String newsSource, String newsData,
			Integer newsFlag, Integer commentsCount,
			List<NewsFilmResource> photos) {
		this.newsId = newsId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsAddress = newsAddress;
		this.newsSource = newsSource;
		this.newsData = newsData;
		this.newsFlag = newsFlag;
		this.commentsCount = commentsCount;
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", newsTitle=" + newsTitle
				+ ", newsContent=" + newsContent + ", newsAddress="
				+ newsAddress + ", newsSource=" + newsSource + ", newsData="
				+ newsData + ", newsFlag=" + newsFlag + ", commentsCount="
				+ commentsCount + ", photoUrl=" + photos + ", comments="
				+ comments + "]";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commentsCount == null) ? 0 : commentsCount.hashCode());
		result = prime * result
				+ ((newsAddress == null) ? 0 : newsAddress.hashCode());
		result = prime * result
				+ ((newsContent == null) ? 0 : newsContent.hashCode());
		result = prime * result
				+ ((newsData == null) ? 0 : newsData.hashCode());
		result = prime * result
				+ ((newsFlag == null) ? 0 : newsFlag.hashCode());
		result = prime * result + ((newsId == null) ? 0 : newsId.hashCode());
		result = prime * result
				+ ((newsSource == null) ? 0 : newsSource.hashCode());
		result = prime * result
				+ ((newsTitle == null) ? 0 : newsTitle.hashCode());
		result = prime * result + ((photos == null) ? 0 : photos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (commentsCount == null) {
			if (other.commentsCount != null)
				return false;
		} else if (!commentsCount.equals(other.commentsCount))
			return false;
		if (newsAddress == null) {
			if (other.newsAddress != null)
				return false;
		} else if (!newsAddress.equals(other.newsAddress))
			return false;
		if (newsContent == null) {
			if (other.newsContent != null)
				return false;
		} else if (!newsContent.equals(other.newsContent))
			return false;
		if (newsData == null) {
			if (other.newsData != null)
				return false;
		} else if (!newsData.equals(other.newsData))
			return false;
		if (newsFlag == null) {
			if (other.newsFlag != null)
				return false;
		} else if (!newsFlag.equals(other.newsFlag))
			return false;
		if (newsId == null) {
			if (other.newsId != null)
				return false;
		} else if (!newsId.equals(other.newsId))
			return false;
		if (newsSource == null) {
			if (other.newsSource != null)
				return false;
		} else if (!newsSource.equals(other.newsSource))
			return false;
		if (newsTitle == null) {
			if (other.newsTitle != null)
				return false;
		} else if (!newsTitle.equals(other.newsTitle))
			return false;
		if (photos == null) {
			if (other.photos != null)
				return false;
		} else if (!photos.equals(other.photos))
			return false;
		return true;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsAddress() {
		return newsAddress;
	}

	public void setNewsAddress(String newsAddress) {
		this.newsAddress = newsAddress;
	}

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getNewsData() {
		return newsData;
	}

	public void setNewsData(String newsData) {
		this.newsData = newsData;
	}

	public Integer getNewsFlag() {
		return newsFlag;
	}

	public void setNewsFlag(Integer newsFlag) {
		this.newsFlag = newsFlag;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public List<NewsFilmResource> getPhotos() {
		return photos;
	}

	public void setPhotos(List<NewsFilmResource> photos) {
		this.photos = photos;
	}

}
