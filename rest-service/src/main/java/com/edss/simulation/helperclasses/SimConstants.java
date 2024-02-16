package com.edss.simulation.helperclasses;

public class SimConstants {

	public static final String NAME_OF_DISEASE = "EPIDEMIC";

	public static int standardIncubationTimeDisease = 6;
	public static float chanceToTransmitDisease = 50;
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

	public static int chanceForAsymptomatic = 5;
}
