package com.edss.simulation.simulation;

import java.util.Random;

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
	private boolean isAlive;
	private boolean isHospitalized;
	private int chanceToGoOut;

	public Agent(boolean isInfectious, AgeGroup ageGroup, float immunity) {
		this.isSick = false;
		this.isSelfQuarantined = false;
		this.isInfectious = isInfectious;
		this.isRecovered = false;
		this.ageGroup = ageGroup;
		this.immunity = immunity;
		this.ableToMeet = true;
		this.isAlive = true;
	}

	public boolean isSick() {
		return isSick;
	}

	public boolean isInfectious() {
		return isInfectious;
	}

	public boolean isRecovered() {
		return isRecovered;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public int getChanceToGoOut() {
		return chanceToGoOut;
	}

	public void setChanceToGoOut(int chanceToGoOut) {
		this.chanceToGoOut = chanceToGoOut;
	}

	public boolean willHeal() {
		if (isSick && disease.hasIncubated()) {
			Random heal = new Random();
			if (heal.nextInt(0, 100) <= disease.getChanceToHeal()) {
				isSick = false;
				isRecovered = true;
				isInfectious = false;
				immunity = 100;
				return true;
			}
		}
		return false;
	}

	public void checkIfInfectious() {
		if (isInfectious) {
			return;
		}
		if (disease.getPeriod() >= disease.getIncubationTime()) {
			isInfectious = true;
		}
	}

	public boolean ableToMeet() {
		return ableToMeet;
	}

	public void checkIfAbleToMeet() {
		if (isSelfQuarantined || isHospitalized) {
			ableToMeet = false;
		} else {
			ableToMeet = true;
		}
	}

}
