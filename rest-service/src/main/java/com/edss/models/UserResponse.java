package com.edss.models;

import java.util.List;

import com.edss.models.helperclasses.Constants;
import com.edss.models.helperclasses.DbHelper;

public class UserResponse {

	private String userId;
	private String city;
	private String disaster;
	private String state;
	private TimestampDatabase timestamp;
	private List<Response> responses;

	public UserResponse(String userId, List<Response> responses) {
		this.userId = userId;
		this.responses = responses;
		extractDisasterType();
		this.city = extractCityFromUser();
	}

	public UserResponse(String userId, String city, String disaster, String state, String timestamp) {
		this.userId = userId;
		this.city = city;
		this.disaster = disaster;
		this.state = state;
		this.setTimestamp(new TimestampDatabase(timestamp));
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

	private void extractDisasterType() {
		checkForFlood();

	}

	private void checkForFlood() {
		Response firstResponse = responses.get(0);
		String firstQuestion = firstResponse.getQuestion();
		String firstUserResponse = firstResponse.getResponse();

		if (firstQuestion.equals(Constants.FLOOD_PRESENT_QUESTION)) {
			this.disaster = Constants.DISASTER_TYPE_FLOOD;

			if (firstUserResponse.equals(Constants.RESPONSE_YES)) {
				String isFloodOngoingResponse = responses.get(1).getResponse();
				if (isFloodOngoingResponse.equals(Constants.RESPONSE_YES)) {
					this.state = Constants.DISASTER_STATE_ONGOING;
				} else {
					this.state = Constants.DISASTER_STATE_POSSIBLE;
				}

			} else {
				this.state = Constants.DISASTER_STATE_NONEXISTENT;
			}
		}
	}

	private String extractCityFromUser() {
		return DbHelper.exctactClosestCity(userId);
	}

}
