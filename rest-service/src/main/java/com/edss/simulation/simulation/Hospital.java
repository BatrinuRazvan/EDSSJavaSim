package com.edss.simulation.simulation;

import java.util.List;

import com.edss.simulation.agents.Agent;

public class Hospital {

	private int normalBeds;
	private int icuBeds;
	private List<Agent> normalBedAgents;
	private List<Agent> icuBedAgents;

	public Hospital(int numberOfAgents) {
		this.normalBeds = numberOfAgents / 1000 * 7 / 4 * 3;
		this.icuBeds = normalBeds * 4 / 100;
	}

	public List<Agent> getNormalBedAgents() {
		return normalBedAgents;
	}

	public List<Agent> getIcuBedAgents() {
		return icuBedAgents;
	}

	public void addNormalBedAgent(Agent agent) {
		normalBeds += 1;
		normalBedAgents.add(agent);
	}

	public void addIcuBedAgent(Agent agent) {
		icuBeds += 1;
		icuBedAgents.add(agent);
	}

	public void removeAgent(Agent agent) {
		if (normalBedAgents.contains(agent)) {
			normalBedAgents.remove(agent);
			normalBeds += 1;
		}
		if (icuBedAgents.contains(agent)) {
			icuBedAgents.remove(agent);
			icuBeds += 1;
		}
	}

}
