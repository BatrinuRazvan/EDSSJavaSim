package com.edss.simulation.helperclasses;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import com.edss.simulation.agents.Agent;
import com.edss.simulation.simulation.CentralLocation;
import com.edss.simulation.simulation.Hospital;

public class SimHelper {

	private static String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static String USERNAME = "root";
	private static String PASSWORD = "1234";

	public static int initMonthsToDays(int simulationPeriodMonths) {

		int result = 0;
		LocalDate now = LocalDate.now();
		Month month = now.getMonth();
		int dayOfMonth = now.getDayOfMonth();

		for (int iterate = 0; iterate < simulationPeriodMonths; iterate++) {
			if (iterate == 0) {
				result += month.maxLength() - dayOfMonth;
			} else if (iterate == simulationPeriodMonths - 1) {
				result += dayOfMonth;
			} else {
				result += month.maxLength();
			}
			month = Month.of(month.getValue() + 1);
		}

		return result;
	}

	public static Date initCurrentDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Date nextDay(Date todaysDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean isOneOfAgentsSick(Agent agent1, Agent agent2) {
//		check to see if one of the agents is sick, otherwise there is no point
		if (agent1.isSick() && !agent2.isSick()) {
			return true;
		}
		if (agent2.isSick() && !agent1.isSick()) {
			return true;
		}
		return false;
	}

	public static boolean developAsymptomatic(int chanceForAsymptomatic) {
		Random asymptomatic = new Random();
		return asymptomatic.nextInt(0, 100) <= chanceForAsymptomatic;
	}

	public static void initalizeHospital(int numberOfAgents) {
		Hospital.initHospital(numberOfAgents);
	}

	public static void initalizeCentralLocation() {
		CentralLocation.initCentralLocation();
	}

	public static void dailyStats(int dayIncrement, Date date, int susceptibleAgentsTotal, int sickAgentsTotal,
			int recoveredAgentsTotal, int deadAgentsTotal, int sickAgentsDaily, int recoveredAgentsDaily,
			int deadAgentsDaily, int normalBedOcc, int icuBedOcc) {

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

			String insertQuery = "INSERT INTO SIMULATION VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setInt(1, dayIncrement);
				preparedStatement.setDate(2, date);
				preparedStatement.setInt(3, deadAgentsTotal);
				preparedStatement.setInt(4, deadAgentsDaily);
				preparedStatement.setInt(5, sickAgentsTotal);
				preparedStatement.setInt(6, sickAgentsDaily);
				preparedStatement.setInt(7, recoveredAgentsTotal);
				preparedStatement.setInt(8, recoveredAgentsDaily);
				preparedStatement.setInt(9, 0);// totalHospitalizations
				preparedStatement.setInt(10, 0);// dailyHospitalizations
				preparedStatement.setFloat(11, 0);// maskUse
				preparedStatement.setLong(12, 0);// totalVaccinations
				preparedStatement.setInt(13, 0);// dailyVaccinations
				preparedStatement.setInt(14, Hospital.getHospital().getTotalNormalBeds());
				preparedStatement.setInt(15, Hospital.getHospital().getTotalIcuBeds());
				preparedStatement.setInt(16, normalBedOcc);
				preparedStatement.setInt(17, icuBedOcc);
				preparedStatement.setFloat(18, (deadAgentsTotal + 0.000f) / (sickAgentsTotal + 0.000f));

				preparedStatement.executeUpdate();

				System.out.println("Row inserted successfully.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void initDatabase() {

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS SIMULATION");

			String createTableQuery = "CREATE TABLE SIMULATION (" + "DAY_INCREMENT INT," + "DATE_ID DATE,"
					+ "TOTAL_DEATHS INT," + "DAILY_DEATHS INT," + "TOTAL_CASES INT," + "DAILY_CASES INT,"
					+ "TOTAL_RECOVERED INT," + "DAILY_RECOVERED INT," + "TOTAL_HOSPITALIZATIONS INT,"
					+ "DAILY_HOSPITALIZATIONS INT," + "MASK_USE FLOAT," + "TOTAL_VACCINATIONS BIGINT,"
					+ "DAILY_VACCINATIONS INT," + "HOSPITAL_CAPACITY INT," + "ICU_CAPACITY INT,"
					+ "CURRENT_HOSP_OCCUPANCY INT," + "CURRENT_ICU_OCCUPANCY INT," + "INFECTION_FATALITY FLOAT,"
					+ "PRIMARY KEY (DAY_INCREMENT)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println("SIMULATION table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
