package com.edss.models;

public class UserResponse {

	private String userId;
	private String disaster;
	private String state;

	public UserResponse(String question, String response, String email) {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisaster() {
		return disaster;
	}

	public void setDisaster(String disaster) {
		this.disaster = disaster;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
