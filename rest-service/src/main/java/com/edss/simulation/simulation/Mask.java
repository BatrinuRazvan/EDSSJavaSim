package com.edss.simulation.simulation;

import com.edss.simulation.helperclasses.SimConstants;

public class Mask {

	private int distributionTime;
	private int cooldownTime;
	private int period;
	private int distributionNumberPerDay;
	private int cooldownNumberPerDay;

	public Mask(int numberOfAgents) {
		super();
		this.distributionTime = SimConstants.maskDistributionTime;
		this.cooldownTime = SimConstants.maskCooldownTime;
		this.period = 0;
		this.distributionNumberPerDay = (numberOfAgents * 8 / 10) / distributionTime;
		this.cooldownNumberPerDay = (numberOfAgents * 8 / 10) / cooldownTime;
	}

	public void changeOnEnforcement(boolean enforceMaskUse, boolean disableMaskUse) {
		if (enforceMaskUse) {

		}

		if (disableMaskUse) {

		}
	}

}
