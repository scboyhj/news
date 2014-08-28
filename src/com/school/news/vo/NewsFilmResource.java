package com.school.news.vo;

public class NewsFilmResource {
	private Integer newsFilmResourceId;
	private Integer newsFilmResourceNewsId;
	private String newsFileResourceType;
	private String newsFilmResourceUrl;
	private Integer newsFilmResourceFlag;
	public NewsFilmResource() {
	}
	public NewsFilmResource(Integer newsFilmResourceId,
			Integer newsFilmResourceNewsId, String newsFileResourceType,
			String newsFilmResourceUrl, Integer newsFilmResourceFlag) {
		this.newsFilmResourceId = newsFilmResourceId;
		this.newsFilmResourceNewsId = newsFilmResourceNewsId;
		this.newsFileResourceType = newsFileResourceType;
		this.newsFilmResourceUrl = newsFilmResourceUrl;
		this.newsFilmResourceFlag = newsFilmResourceFlag;
	}
	public NewsFilmResource(String newsFilmResourceUrl) {
		super();
		this.newsFilmResourceUrl = newsFilmResourceUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((newsFileResourceType == null) ? 0 : newsFileResourceType
						.hashCode());
		result = prime
				* result
				+ ((newsFilmResourceFlag == null) ? 0 : newsFilmResourceFlag
						.hashCode());
		result = prime
				* result
				+ ((newsFilmResourceId == null) ? 0 : newsFilmResourceId
						.hashCode());
		result = prime
				* result
				+ ((newsFilmResourceNewsId == null) ? 0
						: newsFilmResourceNewsId.hashCode());
		result = prime
				* result
				+ ((newsFilmResourceUrl == null) ? 0 : newsFilmResourceUrl
						.hashCode());
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
		NewsFilmResource other = (NewsFilmResource) obj;
		if (newsFileResourceType == null) {
			if (other.newsFileResourceType != null)
				return false;
		} else if (!newsFileResourceType.equals(other.newsFileResourceType))
			return false;
		if (newsFilmResourceFlag == null) {
			if (other.newsFilmResourceFlag != null)
				return false;
		} else if (!newsFilmResourceFlag.equals(other.newsFilmResourceFlag))
			return false;
		if (newsFilmResourceId == null) {
			if (other.newsFilmResourceId != null)
				return false;
		} else if (!newsFilmResourceId.equals(other.newsFilmResourceId))
			return false;
		if (newsFilmResourceNewsId == null) {
			if (other.newsFilmResourceNewsId != null)
				return false;
		} else if (!newsFilmResourceNewsId.equals(other.newsFilmResourceNewsId))
			return false;
		if (newsFilmResourceUrl == null) {
			if (other.newsFilmResourceUrl != null)
				return false;
		} else if (!newsFilmResourceUrl.equals(other.newsFilmResourceUrl))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewsFilmResource [newsFilmResourceId=" + newsFilmResourceId
				+ ", newsFilmResourceNewsId=" + newsFilmResourceNewsId
				+ ", newsFileResourceType=" + newsFileResourceType
				+ ", newsFilmResourceUrl=" + newsFilmResourceUrl
				+ ", newsFilmResourceFlag=" + newsFilmResourceFlag + "]";
	}
	public Integer getNewsFilmResourceId() {
		return newsFilmResourceId;
	}
	public void setNewsFilmResourceId(Integer newsFilmResourceId) {
		this.newsFilmResourceId = newsFilmResourceId;
	}
	public Integer getNewsFilmResourceNewsId() {
		return newsFilmResourceNewsId;
	}
	public void setNewsFilmResourceNewsId(Integer newsFilmResourceNewsId) {
		this.newsFilmResourceNewsId = newsFilmResourceNewsId;
	}
	public String getNewsFileResourceType() {
		return newsFileResourceType;
	}
	public void setNewsFileResourceType(String newsFileResourceType) {
		this.newsFileResourceType = newsFileResourceType;
	}
	public String getNewsFilmResourceUrl() {
		return newsFilmResourceUrl;
	}
	public void setNewsFilmResourceUrl(String newsFilmResourceUrl) {
		this.newsFilmResourceUrl = newsFilmResourceUrl;
	}
	public Integer getNewsFilmResourceFlag() {
		return newsFilmResourceFlag;
	}
	public void setNewsFilmResourceFlag(Integer newsFilmResourceFlag) {
		this.newsFilmResourceFlag = newsFilmResourceFlag;
	}
	
}
