package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.simulation.Disease;

public class ElderAgent extends Agent {

	public ElderAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ELDER;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease != null && immunity < 100) {
			if (disease.hasIncubated() && !isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 50.0f);
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.08f);
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 2.0f);
				}
				selfQuarantine();
			}
			if (disease.hasIncubated() && isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 25.0f);
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.08f);
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 3.0f);
				}
				if (disease.getPeriod() < disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 1.0f);
				}
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(40 - 5) + 5;
	}

	@Override
	public void initDisease(Disease disease) {
		this.disease = disease;
		this.disease.setChanceToAggravate(SimConstants.elderAggravationChance);
	}

}
