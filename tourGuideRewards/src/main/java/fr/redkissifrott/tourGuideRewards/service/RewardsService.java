package fr.redkissifrott.tourGuideRewards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.tourGuideRewards.model.Attraction;
import fr.redkissifrott.tourGuideRewards.model.Location;
import fr.redkissifrott.tourGuideRewards.model.User;
import fr.redkissifrott.tourGuideRewards.model.UserReward;
import fr.redkissifrott.tourGuideRewards.model.VisitedLocation;
import fr.redkissifrott.tourGuideRewards.proxies.GpsUtilProxy;
import rewardCentral.RewardCentral;

@Service
public class RewardsService {

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final RewardCentral rewardsCentral;
	@Autowired
	private final GpsUtilProxy gpsUtil;

	public RewardsService(GpsUtilProxy gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {

		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = (List<Attraction>) gpsUtil
				.getAttractions();

		for (int i = 0; i < userLocations.size(); i++) {
			VisitedLocation visitedLocation = userLocations.get(i);
			attractions.parallelStream()
					.filter(attraction -> user.getUserRewards().parallelStream()
							.filter(r -> r.attraction.attractionName
									.equals(attraction.attractionName))
							.count() == 0)
					.filter(attraction -> nearAttraction(visitedLocation,
							attraction))
					.forEach(attraction -> user.addUserReward(
							new UserReward(visitedLocation, attraction,
									getRewardPoints(attraction, user))));
		}
	}
	// public void calculateRewards(User user) {
	// List<VisitedLocation> userLocations = user.getVisitedLocations();
	// List<Attraction> attractions = gpsUtil.getAttractions();
	//
	// for (VisitedLocation visitedLocation : userLocations) {
	// for (Attraction attraction : attractions) {
	// long count = 0L;
	// for (UserReward r : user.getUserRewards()) {
	// if (r.attraction.attractionName
	// .equals(attraction.attractionName)) {
	// count++;
	// }
	// }
	// if (count == 0) {
	// if (nearAttraction(visitedLocation, attraction)) {
	// user.addUserReward(new UserReward(visitedLocation,
	// attraction, getRewardPoints(attraction, user)));
	// }
	// }
	// }
	// }
	// }

	public boolean isWithinAttractionProximity(Attraction attraction,
			Location location) {
		return getDistance(attraction, location) > attractionProximityRange
				? false
				: true;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation,
			Attraction attraction) {
		return getDistance(attraction,
				visitedLocation.location) > proximityBuffer ? false : true;
	}

	private int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId,
				user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}

}
