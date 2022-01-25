package fr.redkissifrott.tourGuideUser.Dto;

import lombok.Data;

@Data
public class ClosestAttractionsDTO {

	String name;
	double latitude;
	double longitude;
	double distance;
	int rewardPoints;

	public ClosestAttractionsDTO(String name, double latitude, double longitude,
			double distance, int rewardPoints) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.rewardPoints = rewardPoints;
	}
}
