package org.bgi.springboot1.api.tokens;

public class UserTokenInfo {
	
	private String userName;
	
	private long userId;
	
	public UserTokenInfo(String userName, long userId) {
		super();
		this.userName = userName;
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
