package fr.redkissifrott.tourGuideUser.DTO;

import java.util.List;

import fr.redkissifrott.tourGuideUser.model.Location;

public class UserClosestAttractionsDTO {

	public UserClosestAttractionsDTO(Location userLocation,
			List<ClosestAttractionsDTO> closestAttractions) {
		super();
		this.userLocation = userLocation;
		this.closestAttractions = closestAttractions;
	}
	Location userLocation;
	List<ClosestAttractionsDTO> closestAttractions;
	public List<ClosestAttractionsDTO> getClosestAttractions() {
		return closestAttractions;
	}

}
