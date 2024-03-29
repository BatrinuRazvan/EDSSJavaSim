package com.edss.restservice.daemons;

public interface Daemon {

	public abstract void runDaemon();

	public abstract void checkForOngoing();

	public abstract void checkForPossible();

	public abstract void sendOngoingAlert();

	public abstract void sendPossibleAlert();

}
