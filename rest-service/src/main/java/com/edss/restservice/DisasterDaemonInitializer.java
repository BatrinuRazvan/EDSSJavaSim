package com.edss.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edss.models.helperclasses.DisasterType;
import com.edss.restservice.daemons.DisasterDaemon;

@Component
public class DisasterDaemonInitializer {

	private List<DisasterDaemon> daemons = new ArrayList<DisasterDaemon>();

	public DisasterDaemonInitializer(NotificationService notificationService) {
		initDaemons(notificationService);
	}

	private void initDaemons(NotificationService notificationService) {
		daemons.add(new DisasterDaemon(notificationService, DisasterType.FLOOD));
		daemons.add(new DisasterDaemon(notificationService, DisasterType.HURRICANE));
		daemons.add(new DisasterDaemon(notificationService, DisasterType.EARTHQUAKE));
	}

	@Scheduled(fixedRate = 10000)
	public void runAllDaemons() {
		daemons.forEach(daemon -> daemon.runDaemon());
	}

}
