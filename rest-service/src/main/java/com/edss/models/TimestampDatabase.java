package com.edss.models;

public class TimestampDatabase {

	private String originalTimestamp;

	private String day;
	private String month;
	private String year;

	private String hour;
	private String minute;
	private String second;

	public TimestampDatabase(String timestamp) {
		this.originalTimestamp = timestamp;
		this.day = timestamp.substring(9, 11);
		this.month = timestamp.substring(5, 7);
		this.year = timestamp.substring(0, 4);
		this.hour = timestamp.substring(12, 14);
		this.minute = timestamp.substring(15, 17);
		this.second = timestamp.substring(18, 19);
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getOriginalTimestamp() {
		return originalTimestamp;
	}

}
