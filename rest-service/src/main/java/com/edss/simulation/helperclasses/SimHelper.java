package com.edss.simulation.helperclasses;

import java.time.LocalDate;
import java.time.Month;

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

}
