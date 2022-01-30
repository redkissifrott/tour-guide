package fr.redkissifrott.tourGuideUser.DTO;

import fr.redkissifrott.tourGuideUser.model.UserPreferences;

public class UserPreferencesDTO {
	public String getUsername() {
		return username;
	}
	public int getNumberOfAdults() {
		return numberOfAdults;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public int getTripDuration() {
		return tripDuration;
	}
	public UserPreferencesDTO(String userName,
			UserPreferences userPreferences) {
		this.username = userName;
		this.numberOfAdults = userPreferences.getNumberOfAdults();
		this.numberOfChildren = userPreferences.getNumberOfChildren();
		this.tripDuration = userPreferences.getTripDuration();
	}
	public UserPreferencesDTO() {
	}
	private String username;
	private int numberOfAdults;
	private int numberOfChildren;
	private int tripDuration;
}
