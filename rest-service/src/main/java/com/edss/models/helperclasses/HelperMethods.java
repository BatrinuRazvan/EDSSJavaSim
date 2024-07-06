package com.edss.models.helperclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;

import com.edss.models.MessageNotification;
import com.edss.models.User;

public class HelperMethods {

	public static Map<String, List<Double>> initCities() {
		String[] ct = { "London", "Paris", "Berlin", "Rome", "Timisoara" };
		double[] lat = { 51.5074, 48.8566, 52.5200, 41.9028, 45.760696 };
		double[] lon = { -0.1278, 2.3522, 13.4050, 12.4964, 21.226788 };

		Map<String, List<Double>> result = new HashMap<>();
		for (int i = 0; i < ct.length; i++) {
			List<Double> latLon = new ArrayList<>();
			latLon.add(lat[i]);
			latLon.add(lon[i]);
			result.put(ct[i], latLon);
		}
		return result;
	}

	public static boolean userAlreadyExists(User newUser) {
		List<User> allUsers = DbHelper.getAllUsers();
		return allUsers.stream().filter(user -> user.getUserId().equals(newUser.getUserId())).findAny().isPresent();
	}

	public static String constructPayload(MessageNotification notification) {
		JSONObject payloadJson = new JSONObject();
		payloadJson.put("city", notification.getCity());
		payloadJson.put("title", notification.getTitle());
		payloadJson.put("color", notification.getColor());
		payloadJson.put("severity", notification.getSeverity());
		payloadJson.put("range", notification.getRange());
		payloadJson.put("description", notification.getDescription());

		return payloadJson.toString();
	}

	public static List<String> parseSymptoms(String symptoms) {
		return List.of(symptoms.split(","));
	}

	public static int isZeroValue(int currentValue, int valueToCheck) {
		return valueToCheck != 0 ? valueToCheck : currentValue;
	}

	public static double isZeroValue(double currentValue, double valueToCheck) {
		return valueToCheck != Double.valueOf(0) ? valueToCheck : currentValue;
	}
}