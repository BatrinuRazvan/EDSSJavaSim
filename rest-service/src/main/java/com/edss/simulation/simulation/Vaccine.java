package com.edss.simulation.simulation;

import java.util.List;
import java.util.Random;

import com.edss.simulation.agents.Agent;
import com.edss.simulation.helperclasses.SimConstants;

public class Vaccine {

	private double immunityBuffer;
	private int distributionTime;
	private int distributionPeriod;
	private int distributionNumberPerDay;
	private int shotAdministeredTotal;
	private int shotAdministeredToday;

	public Vaccine(int numberOfAgents) {
		this.immunityBuffer = SimConstants.vaccineImmunityBuffer;
		this.distributionTime = SimConstants.vaccineDistributionTime;
		this.distributionPeriod = 0;
		this.distributionNumberPerDay = (numberOfAgents * 8 / 10) / distributionTime;
		this.shotAdministeredTotal = 0;
	}

	public void changeOnEnforcement(boolean enforceVaccine, List<Agent> agents) {
		if (enforceVaccine || distributionPeriod > 0) {
			if (distributionPeriod < distributionTime) {
				shotAdministeredToday = 0;
				Random randomModifyer = new Random();
				for (int iterate = 0; iterate < distributionNumberPerDay + randomModifyer.nextInt(-5, 5); iterate++) {
					agents.get(iterate + getShotAdministeredTotal()).setImmunity(immunityBuffer);
					shotAdministeredTotal = getShotAdministeredTotal() + 1;
					shotAdministeredToday = getShotAdministeredToday() + 1;
				}
				distributionPeriod += 1;
			}
		}
	}

	public int getShotAdministeredToday() {
		return shotAdministeredToday;
	}

	public int getShotAdministeredTotal() {
		return shotAdministeredTotal;
	}

}
