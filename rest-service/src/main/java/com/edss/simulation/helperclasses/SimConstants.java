package com.edss.simulation.helperclasses;

public class SimConstants {

	public static final String NAME_OF_DISEASE = "EPIDEMIC";

	public static int standardIncubationTimeDisease = 6;
	public static double chanceToTransmitDisease = Double.valueOf("50");
	public static int healingTimeDisease = 15;
	public static double initialChanceToHeal = Double.valueOf("0");
	public static double initialChanceToKill = Double.valueOf("0");
	public static int chanceForAsymptomatic = 5;

	public static final String ADD_SUSCEPTIBLE = "ADD_SUSCEPTIBLE";
	public static final String ADD_DEAD = "ADD_DEAD";
	public static final String ADD_SICK = "ADD_SICK";
	public static final String ADD_RECOVERED = "ADD_RECOVERED";

	public static final String REMOVE_SUSCEPTIBLE = "REMOVE_SUSCEPTIBLE";
	public static final String REMOVE_DEAD = "REMOVE_DEAD";

	// for Disease class
	public static final String CHANCE_TO_KILL_NORMAL_BED = "CHANCE_TO_KILL_NORMAL_BED";
	public static final String CHANCE_TO_KILL_ICU_BED = "CHANCE_TO_KILL_ICU_BED";
	public static final String CHANCE_TO_KILL_NO_ICU_AVAILABLE = "CHANCE_TO_KILL_NO_ICU_AVAILABLE";
	public static final String CHANCE_TO_KILL_NO_NORMAL_AVAILABLE = "CHANCE_TO_KILL_NO_NORMAL_AVAILABLE";

	public static final String CHANCE_TO_HEAL = "CHANCE_TO_HEAL";
	public static final String CHANCE_TO_KILL = "CHANCE_TO_KILL";

	public static final String CHANCE_TO_AGGRAVATE = "CHANCE_TO_AGGRAVATE";

	public static double childAggravationChance = Double.valueOf("0.1");
	public static double adultAggravationChance = Double.valueOf("0.5");
	public static double elderAggravationChance = Double.valueOf("0.8");

	public static int chanceToSelfQuarantine = 50; // 95% chance to self quarantine

	public static int agentsAtCentralLocation_atSameTime = 100;

	// for use of Mask
	public static double maskHelpingPercent = Double.valueOf("5");
	public static int maskDistributionTime = 45;
	public static int maskCooldownTime = 25;
}
