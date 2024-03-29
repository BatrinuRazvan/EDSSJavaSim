package com.edss.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edss.restservice.daemons.Daemon;
import com.edss.restservice.daemons.FloodDaemon;

@Component
public class DisasterDaemon {

	private List<Daemon> daemons = new ArrayList<Daemon>();

	public DisasterDaemon(NotificationService notificationService) {
		initDaemons(notificationService);
	}

	private void initDaemons(NotificationService notificationService) {
		daemons.add(new FloodDaemon(notificationService));
	}

	@Scheduled(fixedRate = 10000)
	public void runAllDaemons() {
		daemons.forEach(daemon -> daemon.runDaemon());
	}

}
