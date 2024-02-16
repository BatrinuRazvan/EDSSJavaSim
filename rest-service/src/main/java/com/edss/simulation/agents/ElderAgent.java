package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;

public class ElderAgent extends Agent {

	public ElderAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ELDER;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(40 - 5) + 5;
	}

}
