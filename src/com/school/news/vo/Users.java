package com.school.news.vo;

public class Users {
	private Integer userId;
	private String userName;
	private String userNumber;
	private String userPassword;
	private String userAddress;
	private Integer userFlag;
	private String userPhoto;
	public static Users user = null;

	public Users() {
	}

	public Users(Integer userId, String userName, String userNumber,
			String userPassword, String userAddress, Integer userFlag,
			String userPhoto) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userNumber = userNumber;
		this.userPassword = userPassword;
		this.userAddress = userAddress;
		this.userFlag = userFlag;
		this.userPhoto = userPhoto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userAddress == null) ? 0 : userAddress.hashCode());
		result = prime * result
				+ ((userFlag == null) ? 0 : userFlag.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userNumber == null) ? 0 : userNumber.hashCode());
		result = prime * result
				+ ((userPassword == null) ? 0 : userPassword.hashCode());
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
		Users other = (Users) obj;
		if (userAddress == null) {
			if (other.userAddress != null)
				return false;
		} else if (!userAddress.equals(other.userAddress))
			return false;
		if (userFlag == null) {
			if (other.userFlag != null)
				return false;
		} else if (!userFlag.equals(other.userFlag))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userNumber == null) {
			if (other.userNumber != null)
				return false;
		} else if (!userNumber.equals(other.userNumber))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (userPhoto == null) {
			if (other.userPhoto != null)
				return false;
		} else if (!userPhoto.equals(other.userPhoto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName
				+ ", userNumber=" + userNumber + ", userPassword="
				+ userPassword + ", userAddress=" + userAddress + ", userFlag="
				+ userFlag + ", userPhoto=" + userPhoto + "]";
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Integer getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}

}
