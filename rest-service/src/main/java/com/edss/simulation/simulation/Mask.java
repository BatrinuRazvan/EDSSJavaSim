package com.edss.simulation.simulation;

import java.util.List;

import com.edss.simulation.agents.Agent;
import com.edss.simulation.helperclasses.SimConstants;

public class Mask {

	private int distributionTime;
	private int cooldownTime;
	private int distributionPeriod;
	private int cooldownPeriod;
	private int distributionNumberPerDay;
	private int cooldownNumberPerDay;
	private int alreadyWithMask;

	public Mask(int numberOfAgents) {
		this.distributionTime = SimConstants.maskDistributionTime;
		this.cooldownTime = SimConstants.maskCooldownTime;
		this.distributionPeriod = 0;
		this.cooldownPeriod = 0;
		this.distributionNumberPerDay = (numberOfAgents * 8 / 10) / distributionTime;
		this.cooldownNumberPerDay = (numberOfAgents * 8 / 10) / cooldownTime;
		this.alreadyWithMask = 0;
	}

	public void changeOnEnforcement(boolean enforceMaskUse, List<Agent> agents) {
		if (enforceMaskUse) {
			resetMaskDisableVariables();
		}
		if (enforceMaskUse || distributionPeriod > 0) {
			if (distributionPeriod < distributionTime) {
				for (int iterate = 0; iterate < distributionNumberPerDay; iterate++) {
					agents.get(iterate + alreadyWithMask).setMask(true);
					alreadyWithMask += 1;
				}
				distributionPeriod += 1;
			} else {
				resetMaskEnableVariables();
			}
		}
	}

	public void checkDisablement(boolean disableMaskUse, List<Agent> agents) {
		if (disableMaskUse) {
			resetMaskEnableVariables();
		}
		if (disableMaskUse || cooldownPeriod > 0) {
			if (cooldownPeriod < cooldownTime) {
				for (int iterate = cooldownNumberPerDay; iterate > 0; iterate--) {
					if (alreadyWithMask > 0) {
						Agent checkAgent = agents.get(alreadyWithMask);
						if (checkAgent != null) {
							checkAgent.setMask(false);
							alreadyWithMask -= 1;
						}
					}
				}
				cooldownPeriod += 1;
			} else {
				resetMaskDisableVariables();
			}
		}
	}

	private void resetMaskEnableVariables() {
		distributionPeriod = 0;
	}

	private void resetMaskDisableVariables() {
		cooldownPeriod = 0;
	}

	public double getPercentageOfMaskUse(int nrOfAgents) {
		return Double.valueOf(alreadyWithMask) / Double.valueOf(nrOfAgents);
	}

}
