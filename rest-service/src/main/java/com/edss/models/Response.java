package com.edss.models;

public class Response {

	private String question;
	private String response;

	public Response() {
	}

	public Response(String question, String response) {
		this.question = question;
		this.response = response;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
