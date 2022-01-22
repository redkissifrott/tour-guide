package fr.redkissifrott.tourGuideUser.model;

import java.util.UUID;

public class Attraction extends Location {

	public String attractionName;
	public String city;
	public String state;
	public UUID attractionId;

	// @Override
	// public String toString() {
	// return "Attraction [attLongitude=" + attLongitude + ", attLatitude=" +
	// attLatitude
	// + "]";
	// }

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UUID getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}

	// @Override
	// public String toString() {
	// return "Attraction [toString()=" + super.toString() + "]";
	// }

	public Attraction() {
		super();
	}

	public Attraction(double attLatitude, double attLongitude) {
		super(attLatitude, attLongitude);
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
