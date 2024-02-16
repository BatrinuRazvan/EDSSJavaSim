package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;

public class AdultAgent extends Agent {

	public AdultAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ADULT;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(70 - 35) + 35;
	}

}
