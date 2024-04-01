package com.edss.models;

public class DecisionResponse {

	private TimestampDatabase timestamp;
	private String city;
	private String disaster;
	private String state;

	public DecisionResponse(String timestamp, String city, String disaster, String state) {
		this.timestamp = new TimestampDatabase(timestamp);
		this.city = city;
		this.disaster = disaster;
		this.state = state;
	}

	public TimestampDatabase getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(TimestampDatabase timestamp) {
		this.timestamp = timestamp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
