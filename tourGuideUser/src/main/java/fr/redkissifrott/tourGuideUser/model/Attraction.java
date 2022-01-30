package fr.redkissifrott.tourGuideUser.model;

import java.util.UUID;

public class Attraction extends Location {

	public String attractionName;
	public String getAttractionName() {
		return attractionName;
	}

	public String city;
	public String state;
	public UUID attractionId;

	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}

	public Attraction() {
		super();
	}

	public Attraction(String attractionName, String city, String state,
			double attLatitude, double attLongitude) {
		super(attLatitude, attLongitude);
		this.attractionName = attractionName;
		this.city = city;
		this.state = state;
		this.attractionId = UUID.randomUUID();
	}
}
