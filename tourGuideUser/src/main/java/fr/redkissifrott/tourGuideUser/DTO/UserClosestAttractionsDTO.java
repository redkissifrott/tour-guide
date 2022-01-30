package fr.redkissifrott.tourGuideUser.DTO;

import java.util.List;

import fr.redkissifrott.tourGuideUser.model.Location;

public class UserClosestAttractionsDTO {

	public UserClosestAttractionsDTO(Location userPosition,
			List<ClosestAttractionsDTO> closestAttractions) {
		super();
		this.userPosition = userPosition;
		this.closestAttractions = closestAttractions;
	}
	Location userPosition;
	List<ClosestAttractionsDTO> closestAttractions;
	public List<ClosestAttractionsDTO> getClosestAttractions() {
		return closestAttractions;
	}

}
