package com.edss.models;

public class EdssSubscription {

	private String userId;
	private String endpoint;
	private String p256dh;
	private String auth;

	public EdssSubscription(String userId, String endpoint, String p256dh, String auth) {
		this.endpoint = endpoint;
		this.p256dh = p256dh;
		this.auth = auth;
		this.userId = userId;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getP256dh() {
		return p256dh;
	}

	public void setP256dh(String p256dh) {
		this.p256dh = p256dh;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
