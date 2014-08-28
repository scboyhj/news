package com.school.news.vo;

public class NewsBetween {
	private String newsBetweenTypeName;
	private Integer newsBetweenNewsId;
	public NewsBetween() {
	}
	public NewsBetween(String newsBetweenTypeName, Integer newsBetweenNewsId) {
		super();
		this.newsBetweenTypeName = newsBetweenTypeName;
		this.newsBetweenNewsId = newsBetweenNewsId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((newsBetweenNewsId == null) ? 0 : newsBetweenNewsId
						.hashCode());
		result = prime
				* result
				+ ((newsBetweenTypeName == null) ? 0 : newsBetweenTypeName
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
		NewsBetween other = (NewsBetween) obj;
		if (newsBetweenNewsId == null) {
			if (other.newsBetweenNewsId != null)
				return false;
		} else if (!newsBetweenNewsId.equals(other.newsBetweenNewsId))
			return false;
		if (newsBetweenTypeName == null) {
			if (other.newsBetweenTypeName != null)
				return false;
		} else if (!newsBetweenTypeName.equals(other.newsBetweenTypeName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewsBetween [newsBetweenTypeName=" + newsBetweenTypeName
				+ ", newsBetweenNewsId=" + newsBetweenNewsId + "]";
	}
	public String getNewsBetweenTypeName() {
		return newsBetweenTypeName;
	}
	public void setNewsBetweenTypeName(String newsBetweenTypeName) {
		this.newsBetweenTypeName = newsBetweenTypeName;
	}
	public Integer getNewsBetweenNewsId() {
		return newsBetweenNewsId;
	}
	public void setNewsBetweenNewsId(Integer newsBetweenNewsId) {
		this.newsBetweenNewsId = newsBetweenNewsId;
	}
}
