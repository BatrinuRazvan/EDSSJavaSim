package com.edss.models;

import java.util.List;

import com.edss.models.helperclasses.Constants;

public class UserResponse {

	private String userId;
	private String disaster;
	private String state;
	private TimestampDatabase timestamp;
	private List<Response> responses;

	public UserResponse(String userId, List<Response> responses) {
		this.userId = userId;
		this.responses = responses;
		extractDisasterType();
	}

	public UserResponse(String userId, String disaster, String state, String timestamp) {
		this.userId = userId;
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

}
