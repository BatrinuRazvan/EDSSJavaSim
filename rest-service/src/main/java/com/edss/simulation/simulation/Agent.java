package com.edss.simulation.simulation;

import com.edss.simulation.helperclasses.AgeGroup;

public class Agent {

	private Disease disease;
	private boolean isSick;
	private boolean isSelfQuarantined;
	private boolean isInfectious;
	private boolean isRecovered;
	private AgeGroup ageGroup;
	private float immunity;
	private boolean ableToMeet;

	public Agent(boolean isInfectious, AgeGroup ageGroup, float immunity) {
		this.isSick = false;
		this.isSelfQuarantined = false;
		this.isInfectious = isInfectious;
		this.isRecovered = false;
		this.ageGroup = ageGroup;
		this.immunity = immunity;
		this.ableToMeet = false;
	}
}
