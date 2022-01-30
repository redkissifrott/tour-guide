package fr.redkissifrott.tourGuideUser.model;

public class Location {

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double longitude;
	public double latitude;

	public Location() {
		super();
	}

	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	// @JsonCreator
	// public Location(@JsonProperty("latitude") int latitude,
	// @JsonProperty("longitude") int longitude) {
	// this.latitude = latitude;
	// this.longitude = longitude;
	// }
}
