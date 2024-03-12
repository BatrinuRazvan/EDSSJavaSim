package com.edss.models.helperclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
