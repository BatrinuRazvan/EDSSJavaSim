package com.edss.models;

public class UserResponse {

	private String question;
	private String response;
	private String email;

	public UserResponse(String question, String response, String email) {
		super();
		this.setQuestion(question);
		this.setResponse(response);
		this.setEmail(email);
	}

	public String getEmail() {
		return email;
	}

	public String getResponse() {
		return response;
	}

	public String getQuestion() {
		return question;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
