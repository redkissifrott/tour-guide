package fr.redkissifrott.tourGuideUser.model;

import lombok.Data;

@Data
public class Location {
	// @Override
	// public String toString() {
	// return "Location [longitude=" + longitude + ", latitude=" + latitude
	// + "]";
	// }

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
