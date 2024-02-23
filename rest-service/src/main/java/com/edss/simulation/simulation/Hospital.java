package com.edss.simulation.simulation;

import java.util.ArrayList;
import java.util.List;

import com.edss.simulation.agents.Agent;

public class Hospital {

	private int normalBeds;
	private int icuBeds;
	private int dailyHospitalization;
	private int totalHospitalizations;
	private List<Agent> normalBedAgents = new ArrayList<>();
	private List<Agent> icuBedAgents = new ArrayList<>();
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

	public int getTotalHospitalizations() {
		return totalHospitalizations;
	}

	public int getDailyHospitalization() {
		return dailyHospitalization;
	}

	public void addNormalBedAgent(Agent agent) {
		dailyHospitalization += 1;
		totalHospitalizations += 1;
		normalBedAgents.add(agent);
	}

	public void addIcuBedAgent(Agent agent) {
		icuBedAgents.add(agent);
	}

	public void removeAgent(Agent agent) {
		if (normalBedAgents.contains(agent)) {
			normalBedAgents.remove(agent);
		}
		if (icuBedAgents.contains(agent)) {
			icuBedAgents.remove(agent);
		}
	}

	public void resetDailyVariables() {
		dailyHospitalization = 0;
	}

	public static boolean hasFreeIcuBeds() {
		return getHospital().icuBedAgents.size() <= getHospital().icuBeds;
	}

	public static boolean hasFreeNormalBeds() {
		return getHospital().icuBedAgents.size() <= getHospital().icuBeds;
	}

}
