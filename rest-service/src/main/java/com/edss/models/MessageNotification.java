package com.edss.models;

public class MessageNotification {

	private String title;
	private String city;
	private String severity;
	private int range;
	private String description;
	private String color;

	public MessageNotification(String title, String city, String color, String severity, int range,
			String description) {
		this.title = title;
		this.city = city;
		this.color = color;
		this.severity = severity;
		this.range = range;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
