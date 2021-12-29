package fr.redkissifrott.tourGuideUser.model;

import java.util.UUID;

public class Attraction extends Location {

	public String attractionName;
	public String city;
	public String state;
	public UUID attractionId;
	public double longitude;
	public double latitude;

	@Override
	public String toString() {
		return "Attraction [longitude=" + longitude + ", latitude=" + latitude
				+ "]";
	}

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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	// @Override
	// public String toString() {
	// return "Attraction [toString()=" + super.toString() + "]";
	// }

	public Attraction() {
		super();
	}

	public Attraction(double latitude, double longitude) {
		super(latitude, longitude);
	}

	public Attraction(String attractionName, String city, String state,
			double latitude, double longitude) {
		super(latitude, longitude);
		this.attractionName = attractionName;
		this.city = city;
		this.state = state;
		this.attractionId = UUID.randomUUID();
	}
}
