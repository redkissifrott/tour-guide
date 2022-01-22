package fr.redkissifrott.tourGuideUser.Dto;

import java.util.List;

import fr.redkissifrott.tourGuideUser.model.Location;
import lombok.Data;

@Data
public class UserClosestAttractionsDTO {

	public UserClosestAttractionsDTO(Location userPosition,
			List<ClosestAttractionsDTO> closestAttractions) {
		super();
		this.userPosition = userPosition;
		this.closestAttractions = closestAttractions;
	}
	Location userPosition;
	List<ClosestAttractionsDTO> closestAttractions;

}
