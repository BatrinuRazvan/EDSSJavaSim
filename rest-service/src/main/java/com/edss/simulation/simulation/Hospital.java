package com.edss.simulation.simulation;

import java.util.List;

import com.edss.simulation.agents.Agent;

public class Hospital {

	private int normalBeds;
	private int icuBeds;
	private List<Agent> normalBedAgents;
	private List<Agent> icuBedAgents;
	private static Hospital hospital = null;

	public Hospital(int numberOfAgents) {
		this.normalBeds = numberOfAgents / 1000 * 7 / 4 * 3;
		this.icuBeds = getTotalNormalBeds() * 4 / 100;
	}

	public static void initHospital(int nrOfAgents) {
		hospital = new Hospital(nrOfAgents);
	}

	public static synchronized Hospital getHospital() {
		return hospital;
	}

	public int getTotalNormalBeds() {
		return normalBeds;
	}

	public int getTotalIcuBeds() {
		return icuBeds;
	}

	public List<Agent> getNormalBedAgents() {
		return normalBedAgents;
	}

	public List<Agent> getIcuBedAgents() {
		return icuBedAgents;
	}

	public void addNormalBedAgent(Agent agent) {
		normalBeds = getTotalNormalBeds() + 1;
		normalBedAgents.add(agent);
	}

	public void addIcuBedAgent(Agent agent) {
		icuBeds = getTotalIcuBeds() + 1;
		icuBedAgents.add(agent);
	}

	public void removeAgent(Agent agent) {
		if (normalBedAgents.contains(agent)) {
			normalBedAgents.remove(agent);
			normalBeds = getTotalNormalBeds() + 1;
		}
		if (icuBedAgents.contains(agent)) {
			icuBedAgents.remove(agent);
			icuBeds = getTotalIcuBeds() + 1;
		}
	}

}
