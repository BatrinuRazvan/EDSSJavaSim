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

	private final String publicKey = "BGAUGfksW8MR0puO1T-LQuzYRNjmfLrwG9-PStRYckwEU3zVI3P60QOfsY6MoF82zwgqQpHUiLXBlsW425fh6no";
	private final String privateKey = "mY1Nz_eu7xT_Gt61bNSeD8xEnDjpBV0C5vjQT7NSNzc";

	public void sendNotification(EdssSubscription subscription, String payload) throws Exception {
		PushService pushService = new PushService(publicKey, privateKey);

		Notification notification = new Notification(subscription.getEndpoint(), subscription.getP256dh(),
				subscription.getAuth(), payload.getBytes());

		pushService.send(notification);
	}
}