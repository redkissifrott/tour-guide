package fr.redkissifrott.tourGuideGps.model;

import java.util.UUID;

public class User {
	private final UUID userId;
	private final String userName;

	public User(UUID userId, String userName, String phoneNumber,
			String emailAddress) {
		this.userId = userId;
		this.userName = userName;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

}
