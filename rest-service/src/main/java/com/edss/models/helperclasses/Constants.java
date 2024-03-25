package com.edss.models.helperclasses;

import java.util.List;
import java.util.Map;

public class Constants {

	public static final String USERRESPONSES_TABLE = "USERRESPONSES";
	public static final String ID_AUTO_INCREMENT = "id INT AUTO_INCREMENT PRIMARY KEY,";
	public static final String TIMESTAMP_CURRENT = "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP,";
	public static final String MARKERS_TABLE = "MARKERS";
	public static final String NOTIFICATIONS_TABLE = "NOTIFICATIONS";
	public static final String USERS_TABLE = "USERS";
	public static final String SUBSCRIPTION_TABLE = "SUBSCRIPTION";

	public static Map<String, List<Double>> cities = HelperMethods.initCities();

}
