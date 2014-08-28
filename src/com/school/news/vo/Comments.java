package com.school.news.vo;





public class Comments {
	private Integer commentsId;
	private Integer commentsNewsId;
	private Integer commentsUserId;
	private String commentsContent;
	private String commentsData;
	private Integer commentsFlag;
	private String userName;
	private String userAddress;
	private String userPhoto;
	private Integer startIndex = 1;
	private Integer endIndex = 6;
	private Integer r;
	public Comments(Integer commentsNewsId) {
		super();
		this.commentsNewsId = commentsNewsId;
	}
	public Comments() {
	}
	public Integer getR() {
		return r;
	}
	public void setR(Integer r) {
		this.r = r;
	}
	public Integer getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(Integer commentsId) {
		this.commentsId = commentsId;
	}
	public Integer getCommentsNewsId() {
		return commentsNewsId;
	}
	public void setCommentsNewsId(Integer commentsNewsId) {
		this.commentsNewsId = commentsNewsId;
	}
	public Integer getCommentsUserId() {
		return commentsUserId;
	}
	public void setCommentsUserId(Integer commentsUserId) {
		this.commentsUserId = commentsUserId;
	}
	public String getCommentsContent() {
		return commentsContent;
	}
	public void setCommentsContent(String commentsContent) {
		this.commentsContent = commentsContent;
	}
	public String getCommentsData() {
		return commentsData;
	}
	public void setCommentsData(String commentsData) {
		this.commentsData = commentsData;
	}
	public Integer getCommentsFlag() {
		return commentsFlag;
	}
	public void setCommentsFlag(Integer commentsFlag) {
		this.commentsFlag = commentsFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	public Comments(Integer commentsId, Integer commentsNewsId,
			Integer commentsUserId, String commentsContent,
			String commentsData, Integer commentsFlag, String userName,
			String userAddress, String userPhoto, Integer startIndex,
			Integer endIndex) {
		super();
		this.commentsId = commentsId;
		this.commentsNewsId = commentsNewsId;
		this.commentsUserId = commentsUserId;
		this.commentsContent = commentsContent;
		this.commentsData = commentsData;
		this.commentsFlag = commentsFlag;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userPhoto = userPhoto;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	@Override
	public String toString() {
		return "Comments [commentsId=" + commentsId + ", commentsNewsId="
				+ commentsNewsId + ", commentsUserId=" + commentsUserId
				+ ", commentsContent=" + commentsContent + ", commentsData="
				+ commentsData + ", commentsFlag=" + commentsFlag
				+ ", userName=" + userName + ", userAddress=" + userAddress
				+ ", userPhoto=" + userPhoto + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commentsContent == null) ? 0 : commentsContent.hashCode());
		result = prime * result
				+ ((commentsData == null) ? 0 : commentsData.hashCode());
		result = prime * result
				+ ((commentsFlag == null) ? 0 : commentsFlag.hashCode());
		result = prime * result
				+ ((commentsId == null) ? 0 : commentsId.hashCode());
		result = prime * result
				+ ((commentsNewsId == null) ? 0 : commentsNewsId.hashCode());
		result = prime * result
				+ ((commentsUserId == null) ? 0 : commentsUserId.hashCode());
		result = prime * result
				+ ((userAddress == null) ? 0 : userAddress.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userPhoto == null) ? 0 : userPhoto.hashCode());
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
		Comments other = (Comments) obj;
		if (commentsContent == null) {
			if (other.commentsContent != null)
				return false;
		} else if (!commentsContent.equals(other.commentsContent))
			return false;
		if (commentsData == null) {
			if (other.commentsData != null)
				return false;
		} else if (!commentsData.equals(other.commentsData))
			return false;
		if (commentsFlag == null) {
			if (other.commentsFlag != null)
				return false;
		} else if (!commentsFlag.equals(other.commentsFlag))
			return false;
		if (commentsId == null) {
			if (other.commentsId != null)
				return false;
		} else if (!commentsId.equals(other.commentsId))
			return false;
		if (commentsNewsId == null) {
			if (other.commentsNewsId != null)
				return false;
		} else if (!commentsNewsId.equals(other.commentsNewsId))
			return false;
		if (commentsUserId == null) {
			if (other.commentsUserId != null)
				return false;
		} else if (!commentsUserId.equals(other.commentsUserId))
			return false;
		if (userAddress == null) {
			if (other.userAddress != null)
				return false;
		} else if (!userAddress.equals(other.userAddress))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPhoto == null) {
			if (other.userPhoto != null)
				return false;
		} else if (!userPhoto.equals(other.userPhoto))
			return false;
		return true;
	}
	
	
	
}
