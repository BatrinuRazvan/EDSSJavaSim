package com.edss.simulation.helperclasses;

public class SimConstants {

	public static final String NAME_OF_DISEASE = "EPIDEMIC";

	public static int standardIncubationTimeDisease = 6;
	public static float chanceToTransmitDisease = 70;
	public static int healingTimeDisease = 15;
	public static float initialChanceToHeal = 0;
	public static float initialChanceToKill = 0;

	public static final String ADD_SUSCEPTIBLE = "ADD_SUSCEPTIBLE";
	public static final String ADD_DEAD = "ADD_DEAD";
	public static final String ADD_SICK = "ADD_SICK";
	public static final String ADD_RECOVERED = "ADD_RECOVERED";

	public static final String REMOVE_SUSCEPTIBLE = "REMOVE_SUSCEPTIBLE";
	public static final String REMOVE_DEAD = "REMOVE_DEAD";
	public static final String REMOVE_SICK = "REMOVE_SICK";

	// for Disease class
	public static final String CHANCE_TO_KILL_NORMAL_BED = "CHANCE_TO_KILL_NORMAL_BED";
	public static final String CHANCE_TO_KILL_ICU_BED = "CHANCE_TO_KILL_ICU_BED";

	public static final String CHANCE_TO_HEAL = "CHANCE_TO_HEAL";
	public static final String CHANCE_TO_KILL = "CHANCE_TO_KILL";

	public static final String CHANCE_TO_AGGRAVATE = "CHANCE_TO_AGGRAVATE";

	public static int chanceForAsymptomatic = 5;

	public static double childAggravationChance = Double.valueOf("0.01");
	public static double adultAggravationChance = Double.valueOf("0.05");
	public static double elderAggravationChance = Double.valueOf("0.08");

	public static int chanceToSelfQuarantine = 95; // 95% chance to self quarantine

	public static int agentsAtCentralLocation_atSameTime = 100;
}
