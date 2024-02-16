package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.DiseaseState;
import com.edss.simulation.simulation.Disease;

public abstract class Agent {

	protected Disease disease;
	protected boolean isSick;
	protected boolean isSelfQuarantined;
	protected boolean isInfectious;
	protected boolean isRecovered;
	protected AgeGroup ageGroup;
	protected float immunity;
	protected boolean ableToMeet;
	protected boolean isAlive;
	protected boolean isHospitalized;
	protected int chanceToGoOut;

	public Agent(boolean isInfectious) {
		this.isSick = false;
		this.isSelfQuarantined = false;
		this.isInfectious = isInfectious;
		this.isRecovered = false;
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

	public float getImmunity() {
		return immunity;
	}

	public void setImmunity(float immunity) {
		this.immunity = immunity;
	}

	public float getChanceToTransmitDisease() {
		return this.getDisease().getChanceToTransmit();
	}

	public float getChanceToHealDisease() {
		return this.getDisease().getChanceToHeal();
	}

	public float getChanceToBeKilled() {
		return this.getDisease().getChanceToKill();
	}

	public boolean willHeal() {
		if (isSick && disease.hasIncubated()) {
			Random heal = new Random();
			if (heal.nextInt(0, 100) <= disease.getChanceToHeal()) {
				isSick = false;
				isRecovered = true;
				isInfectious = false;
				setImmunity(100);
				return true;
			}
		}
		return false;
	}

	public void checkIfInfectious() {
		if (isInfectious || disease == null) {
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

	public boolean checkNeedsHospitalization() {
		if (isSick && disease.aggravates(DiseaseState.NORMAL, DiseaseState.NEEDS_HOSPITAL)) {
			isHospitalized = true;
			ableToMeet = false;
			return true;
		}
		return false;
	}

	public boolean checkNeedsIcu() {
		return isSick && disease.aggravates(DiseaseState.NEEDS_HOSPITAL, DiseaseState.NEEDS_ICU);
	}

	public abstract void updateStateOfDisease();

	public abstract void initImmunity();

}
