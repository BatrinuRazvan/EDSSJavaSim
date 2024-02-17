package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.DiseaseState;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.simulation.Disease;
import com.edss.simulation.simulation.Hospital;
import com.edss.simulation.simulation.Simulation;

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
				Hospital.getHospital().removeAgent(this);
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

	public void checkNeedsHospitalization() {
		if (isSick && disease.aggravates(DiseaseState.NORMAL, DiseaseState.NEEDS_HOSPITAL)) {
			isHospitalized = true;
			Hospital.getHospital().addNormalBedAgent(this);
			disease.updateVariable(SimConstants.CHANCE_TO_KILL_NORMAL_BED);
			disease.updateVariable(SimConstants.CHANCE_TO_AGGRAVATE, 25.0f);
		}
	}

	public void checkNeedsIcu() {
		if (isSick && disease.aggravates(DiseaseState.NEEDS_HOSPITAL, DiseaseState.NEEDS_ICU)) {
			Hospital.getHospital().removeAgent(this);
			Hospital.getHospital().addIcuBedAgent(this);
			disease.updateVariable(SimConstants.CHANCE_TO_KILL_ICU_BED);
		}
	}

	protected void selfQuarantine() {
		Random selfQuarantine = new Random();
		if (selfQuarantine.nextInt(0, 100) <= SimConstants.chanceToSelfQuarantine) {
			isSelfQuarantined = true;
		}
	}

	public abstract void updateStateOfDisease();

	public abstract void initImmunity();

	public abstract void initDisease(Disease disease);

	public void checkIfGetsSickAtCentralLocation() {
		Random infectionAndImmunity = new Random();
		if (infectionAndImmunity.nextFloat(0, 100) <= SimConstants.chanceToTransmitDisease / 2) {
			if (infectionAndImmunity.nextFloat(0, 100) > this.getImmunity()) {
				Disease disease = new Disease();
				this.initDisease(disease);
				Simulation.updateGlobalVariables(SimConstants.REMOVE_SUSCEPTIBLE, SimConstants.ADD_SICK);
			}
		}
	}

}
