package com.edss.models.helperclasses;

import java.util.List;
import java.util.Map;

public class Constants {

	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "1234";

	public static final String NOTIFICATIONS_PUBLIC = "BGAUGfksW8MR0puO1T-LQuzYRNjmfLrwG9-PStRYckwEU3zVI3P60QOfsY6MoF82zwgqQpHUiLXBlsW425fh6no";
	public static final String NOTIFICATIONS_PRIVATE = "mY1Nz_eu7xT_Gt61bNSeD8xEnDjpBV0C5vjQT7NSNzc";

	public static final String ID_AUTO_INCREMENT = "id INT AUTO_INCREMENT PRIMARY KEY,";
	public static final String ID_DAY_INCREMENT = "DAY_INCREMENT INT AUTO_INCREMENT PRIMARY KEY,";
	public static final String TIMESTAMP_CURRENT = "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP,";
	public static final String MARKERS_TABLE = "MARKERS";
	public static final String NOTIFICATIONS_TABLE = "NOTIFICATIONS";
	public static final String USERS_TABLE = "USERS";
	public static final String SUBSCRIPTION_TABLE = "SUBSCRIPTION";
	public static final String USERRESPONSES_TABLE = "USERRESPONSES";
	public static final String DECISIONRESPONSES_TABLE = "DECISIONRESPONSES";
	public static final String EXITPOINTS_TABLE = "EXITPOINTS";
	public static final String SIMULATION_TABLE = "SIMULATION";
	public static final String DIAGNOSTICS_TABLE = "DIAGNOSTICS";
	public static final String SYMPTOMS_TABLE = "SYMPTOMS";
	public static final String COVID19_TM_TABLE = "COVID19_TM";
	public static final String DIAGNOSTICS_TIMESTAMPS_TABLE = "DIAGNOSTICS_TIMESTAMPS";

	public static Map<String, List<Double>> cities = HelperMethods.initCities();

	public static final String RESPONSE_YES = "Yes";
	public static final String RESPONSE_NO = "No";

	public static final String DISASTER_STATE_POSSIBLE = "POSSIBLE";
	public static final String DISASTER_STATE_ONGOING = "ONGOING";
	public static final String DISASTER_STATE_NONEXISTENT = "NONEXISTENT";

	public static final int SEND_ONGOING_ALERT_THRESHOLD = 1;
	public static final int SEND_POSSIBLE_ALERT_THRESHOLD = 1;

}
