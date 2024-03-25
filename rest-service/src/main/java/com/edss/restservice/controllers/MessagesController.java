package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.EdssSubscription;
import com.edss.models.MessageNotification;
import com.edss.models.User;
import com.edss.models.UserLocation;
import com.edss.models.UserResponse;
import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;
import com.edss.restservice.NotificationService;

@RestController
@RequestMapping("/messages")
public class MessagesController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/addNotification")
	public ResponseEntity<?> addNotification(@RequestBody MessageNotification notification) {
		DbHelper.addMessageNotification(notification.getCity(), notification.getTitle(), notification.getColor(),
				notification.getSeverity(), notification.getRange(), notification.getDescription());

//		List<EdssSubscription> subs = DbHelper.getAllSubscriptions();
		List<User> users = DbHelper.getUsersInArea(notification);

		users.forEach(user -> {
			try {
				EdssSubscription sub = user.getSubscription();
				String payload = HelperMethods.constructPayload(notification);
				notificationService.sendNotification(sub, payload);
				System.out.println("Notification sent to endpoint: " + sub.getEndpoint());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return ResponseEntity.ok().build();
	}

	@GetMapping("/getMessages")
	public List<MessageNotification> getMessages() {
		return DbHelper.getMessageNotifications();
	}

	@GetMapping("/getLocalMessages")
	public List<MessageNotification> getLocalMessages(@RequestParam double latitude, @RequestParam double longitude) {
		System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
		UserLocation userLocation = new UserLocation(latitude, longitude); // Assuming you have such a constructor
		return DbHelper.getLocalMessageNotifications(userLocation);
	}

	@PostMapping("/saveResponse")
	public ResponseEntity<?> saveResponse(@RequestBody UserResponse userResponse) {
		// Assuming you have a DataSource or JdbcTemplate configured

//		DbHelper.addUserRespons(userResponse.getEmail(), userResponse.getQuestion(), userResponse.getResponse());

		return ResponseEntity.ok().build();
	}

}
