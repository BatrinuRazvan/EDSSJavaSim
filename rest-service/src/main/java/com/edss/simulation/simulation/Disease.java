package com.edss.simulation.simulation;

import java.util.Random;

import com.edss.simulation.helperclasses.DiseaseState;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.helperclasses.SimHelper;

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
	private boolean isAsymptomatic;

	public Disease() {
		this.name = SimConstants.NAME_OF_DISEASE;
		this.periodOfDisease = 0;
		this.incubationTime = SimConstants.standardIncubationTimeDisease;
		this.chanceToTransmit = SimConstants.chanceToTransmitDisease;
		this.healingTime = SimConstants.healingTimeDisease;
		this.chanceToHeal = SimConstants.initialChanceToHeal;
		this.chanceToKill = SimConstants.initialChanceToKill;
		this.isAsymptomatic = SimHelper.developAsymptomatic(SimConstants.chanceForAsymptomatic);
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

	public int getHealingTime() {
		return healingTime;
	}

	public void setChanceToAggravate(float chanceToAggravate) {
		this.chanceToAggravate = chanceToAggravate;
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

	public void updateVariable(String varibleToChange, Float... optinalIncrementer) {
		switch (varibleToChange) {
		case "CHANCE_TO_KILL_NORMAL_BED":
			chanceToKill += 5;
			break;
		case "CHANCE_TO_KILL_ICU_BED":
			chanceToKill += 10;
			break;
		case "CHANCE_TO_HEAL":
			chanceToHeal += optinalIncrementer[0];
			break;
		case "CHANCE_TO_KILL":
			chanceToKill += optinalIncrementer[0];
			break;
		case "CHANCE_TO_AGGRAVATE":
			setChanceToAggravate(chanceToAggravate + optinalIncrementer[0]);
		default:
			throw new IllegalArgumentException("Unexpected value: " + varibleToChange);
		}
	}

}
