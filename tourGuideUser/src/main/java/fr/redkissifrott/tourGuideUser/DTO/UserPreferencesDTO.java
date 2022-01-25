package fr.redkissifrott.tourGuideUser.DTO;

import fr.redkissifrott.tourGuideUser.model.UserPreferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesDTO {
	public UserPreferencesDTO(String userName,
			UserPreferences userPreferences) {
		this.username = userName;
		this.numberOfAdults = userPreferences.getNumberOfAdults();
		this.numberOfChildren = userPreferences.getNumberOfChildren();
		this.tripDuration = userPreferences.getTripDuration();
	}
	private String username;
	private int numberOfAdults;
	private int numberOfChildren;
	private int tripDuration;
}
