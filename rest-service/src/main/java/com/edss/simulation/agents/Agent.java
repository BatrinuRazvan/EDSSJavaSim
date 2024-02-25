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
	protected double immunity;
	protected boolean ableToMeet;
	protected boolean isHospitalized;
	protected int chanceToGoOut;

	public Agent(boolean isInfectious) {
		this.isSick = isInfectious;
		this.isSelfQuarantined = false;
		this.isInfectious = isInfectious;
		this.isRecovered = false;
		this.ableToMeet = true;
		this.chanceToGoOut = 40;
		initDiseaseAtStart(isInfectious);
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

	public double getImmunity() {
		return immunity;
	}

	public void setImmunity(float immunity) {
		this.immunity = immunity;
	}

	public double getChanceToTransmitDisease() {
		return this.getDisease().getChanceToTransmit();
	}

	public double getChanceToHealDisease() {
		return this.getDisease().getChanceToHeal();
	}

	public double getChanceToBeKilled() {
		return this.getDisease().getChanceToKill();
	}

	public boolean willHeal() {
		if (isSick && disease.hasIncubated()) {
			Random heal = new Random();
			if (heal.nextDouble(0, 100) <= getChanceToHealDisease()) {
				isSick = false;
				isRecovered = true;
				isInfectious = false;
				setImmunity(100);
				Hospital.getHospital().removeAgent(this);
				disease = null;
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
		if ((isSick && disease.aggravates(DiseaseState.NORMAL, DiseaseState.NEEDS_HOSPITAL))
				|| (isSick && disease.getState().equals(DiseaseState.NEEDS_HOSPITAL) && !isHospitalized)) {
			if (Hospital.hasFreeNormalBeds()) {
				isHospitalized = true;
				Hospital.getHospital().addNormalBedAgent(this);
				disease.updateVariable(SimConstants.CHANCE_TO_KILL_NORMAL_BED);
				disease.updateVariable(SimConstants.CHANCE_TO_AGGRAVATE, Double.valueOf("25"));
			} else {
				disease.updateVariable(SimConstants.CHANCE_TO_KILL_NO_NORMAL_AVAILABLE);
			}
		}
	}

	public void checkNeedsIcu() {
		if (isSick && disease.aggravates(DiseaseState.NEEDS_HOSPITAL, DiseaseState.NEEDS_ICU)) {
			if (Hospital.hasFreeIcuBeds()) {
				Hospital.getHospital().removeAgent(this);
				Hospital.getHospital().addIcuBedAgent(this);
				disease.updateVariable(SimConstants.CHANCE_TO_KILL_ICU_BED);
			} else {
				disease.updateVariable(SimConstants.CHANCE_TO_KILL_NO_ICU_AVAILABLE);
			}
		}
	}

	protected void selfQuarantine() {
		Random selfQuarantine = new Random();
		if (selfQuarantine.nextInt(0, 100) <= SimConstants.chanceToSelfQuarantine) {
			isSelfQuarantined = true;
		}
	}

	public boolean checkIfGetsSickAtCentralLocation() {
		if (isSick) {
			return true;
		}
		Random infectionAndImmunity = new Random();
		if (infectionAndImmunity.nextDouble(0, 100) <= SimConstants.chanceToTransmitDisease / 2) {
			if (infectionAndImmunity.nextDouble(0, 100) > this.getImmunity()) {
				Disease disease = new Disease();
				this.initDisease(disease);
				isSick = true;
				Simulation.updateGlobalVariables(SimConstants.REMOVE_SUSCEPTIBLE, SimConstants.ADD_SICK);
				return true;
			}
		}
		return false;
	}

	private void initDiseaseAtStart(boolean isInfectious) {
		if (isInfectious) {
			initDisease(new Disease());
		}
	}

	public abstract void updateStateOfDisease();

	public abstract void initImmunity();

	public abstract void initDisease(Disease disease);

}
