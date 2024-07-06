package com.edss.restservice;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.edss.models.EdssSubscription;
import com.edss.models.helperclasses.Constants;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

public class NotificationService {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public void sendNotification(EdssSubscription subscription, String payload) throws Exception {
		PushService pushService = new PushService(Constants.NOTIFICATIONS_PUBLIC, Constants.NOTIFICATIONS_PRIVATE);

		Notification notification = new Notification(subscription.getEndpoint(), subscription.getP256dh(),
				subscription.getAuth(), payload.getBytes());

		pushService.send(notification);
	}
}