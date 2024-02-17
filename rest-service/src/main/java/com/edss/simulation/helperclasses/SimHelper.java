package com.edss.simulation.helperclasses;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import com.edss.simulation.agents.Agent;
import com.edss.simulation.simulation.Hospital;

public class SimHelper {

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

}
