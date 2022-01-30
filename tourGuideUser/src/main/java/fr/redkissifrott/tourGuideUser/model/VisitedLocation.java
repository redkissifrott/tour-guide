package fr.redkissifrott.tourGuideUser.model;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class VisitedLocation {

	public UUID userId;
	public Location location;
	public Date timeVisited;

	public VisitedLocation(UUID userId, Location location, Date timeVisited) {
		this.userId = userId;
		this.location = location;
		this.timeVisited = timeVisited;
	}

	public VisitedLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

}
