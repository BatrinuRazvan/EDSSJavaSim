package com.edss.simulation.simulation;

import java.util.Random;

import com.edss.simulation.helperclasses.DiseaseState;
import com.edss.simulation.helperclasses.SimConstants;

public class Disease {

	private String name;
	private int periodOfDisease;
	private int incubationTime;
	private float chanceToTransmit;
	private int healingTime;
	private float chanceToHeal;
	private float chanceToKill;
	private DiseaseState state;
	private float chanceToAggravate;

	public Disease() {
		this.name = SimConstants.NAME_OF_DISEASE;
		this.periodOfDisease = 0;
		this.incubationTime = SimConstants.standardIncubationTimeDisease;
		this.chanceToTransmit = SimConstants.chanceToTransmitDisease;
		this.healingTime = SimConstants.healingTimeDisease;
		this.chanceToHeal = SimConstants.initialChanceToHeal;
		this.chanceToKill = SimConstants.initialChanceToKill;
	}

	public float getChanceToKill() {
		return chanceToKill;
	}

	public float getChanceToHeal() {
		return chanceToHeal;
	}

	public float getChanceToTransmit() {
		return chanceToTransmit;
	}

	public boolean hasIncubated() {
		return periodOfDisease >= incubationTime ? true : false;
	}

	public int getPeriod() {
		return periodOfDisease;
	}

	public int getIncubationTime() {
		return incubationTime;
	}

	public boolean aggravates(DiseaseState stateToCheck, DiseaseState nextState) {
		if (state.equals(stateToCheck)) {
			Random aggravate = new Random();
			if (aggravate.nextInt(0, 100) <= chanceToAggravate) {
				state = nextState;
			}
		}
		return false;
	}
}
