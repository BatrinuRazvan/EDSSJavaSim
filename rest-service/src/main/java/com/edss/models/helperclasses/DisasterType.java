package com.edss.models.helperclasses;

public enum DisasterType {

	HURRICANE("Is there an ongoing hurricane in your area?", "Is there a possible storm incoming?"), //
	FLOOD("Are you currently caught up in a flood?", "Is there a flood incoming?"), //
	EARTHQUAKE("Are you experiencing an earthquake?", "Is there an incoming earthquake?");

	private String ongoingQuestion;
	private String incomingQuestion;

	DisasterType(String ongoingQuestion, String incomingQuestion) {
		this.ongoingQuestion = ongoingQuestion;
		this.incomingQuestion = incomingQuestion;
	}

	public String getOngoingQuestion() {
		return ongoingQuestion;
	}

	public String getIncomingQuestion() {
		return incomingQuestion;
	}

	public static DisasterType extractType(String currentQestion) {
		for (int i = 0; i < DisasterType.values().length; i++) {
			DisasterType disasterType = DisasterType.values()[i];
			if (disasterType.ongoingQuestion.equals(currentQestion)) {
				return disasterType;
			}
		}
		return null;
	}

}
