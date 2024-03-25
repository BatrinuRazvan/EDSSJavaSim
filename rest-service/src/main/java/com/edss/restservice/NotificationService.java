package com.edss.restservice;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.edss.models.EdssSubscription;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

public class NotificationService {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private final String publicKey = "BJo28K3NruwmTtCrPPnf-rjd_YXd0ukt5ATkke_gIYbwfBmcIVaTJ181jvEnBho2WdamjNaP2CuSRBhndJrnIaI";
	private final String privateKey = "HPqF_drHZRgyi2jmaFInTUk4jFl8oD43_dSSjCYl2k4";

	public void sendNotification(EdssSubscription subscription, String payload) throws Exception {
		PushService pushService = new PushService(publicKey, privateKey);

		Notification notification = new Notification(subscription.getEndpoint(), subscription.getP256dh(),
				subscription.getAuth(), payload.getBytes());

		pushService.send(notification);
	}
}