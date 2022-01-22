package fr.redkissifrott.tourGuideRewards.model;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class VisitedLocation {

	public final UUID userId;
	public final Location location;
	public final Date timeVisited;

	public VisitedLocation(UUID userId, Location location, Date timeVisited) {
		this.userId = userId;
		this.location = location;
		this.timeVisited = timeVisited;
	}

}
