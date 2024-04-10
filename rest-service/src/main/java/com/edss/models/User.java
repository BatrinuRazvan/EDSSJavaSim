package com.edss.models;

import com.edss.models.helperclasses.DbHelper;

public class User {

	private String userId;
	private String email;
	private UserLocation currentLocation;
	private EdssSubscription subscription;
	private String closestCity;
	private String userType;

	public User(String userId, String email, UserLocation currentLocation, String userType) {
		this.userId = userId;
		this.email = email;
		this.currentLocation = currentLocation;
		this.closestCity = DbHelper.exctactClosestCity(this.currentLocation.getLatitude(),
				this.currentLocation.getLongitude());
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserLocation getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(UserLocation currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EdssSubscription getSubscription() {
		return subscription;
	}

	public void setSubscription(EdssSubscription subscription) {
		this.subscription = subscription;
	}

	public String getClosestCity() {
		return closestCity;
	}

	public void setClosestCity(String closestCity) {
		this.closestCity = closestCity;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
