package com.edss.simulation.simulation;

import java.util.List;

public class Hospital {

	private int normalBeds;
	private int icuBeds;
	private List<Agent> normalBedAgents;
	private List<Agent> icuBedAgents;

	public Hospital(int numberOfAgents) {
		this.normalBeds = numberOfAgents / 1000 * 7;
		this.icuBeds = normalBeds * 4 / 100;
	}

}
