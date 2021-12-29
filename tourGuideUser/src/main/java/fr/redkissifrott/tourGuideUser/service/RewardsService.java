package fr.redkissifrott.tourGuideUser.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.RewardsProxy;

@Service
public class RewardsService {

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	// private int defaultProximityBuffer = 10;
	// private int proximityBuffer = defaultProximityBuffer;
	// private int attractionProximityRange = 200;
	private int defaultProximityBuffer = Integer.MAX_VALUE;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = Integer.MAX_VALUE;

	@Autowired
	private GpsUtilProxy gpsProxy;
	@Autowired
	private final RewardsProxy rewardsProxy;

	public RewardsService(GpsUtilProxy gpsUtil, RewardsProxy rewardsService) {
		this.gpsProxy = gpsUtil;
		this.rewardsProxy = rewardsService;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public int getAttractionProximityRange() {
		return attractionProximityRange;
	}

	public void setAttractionProximityRange(int attractionProximityRange) {
		this.attractionProximityRange = attractionProximityRange;
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(100);

	public CompletableFuture<Void> calculateRewards(User user) {

		// logger.debug("loc :" + userLocations.size());
		// logger.debug("att :" + attractions.size());
		// logger.debug("REWARDS : " + user.getUserRewards().size());
		return CompletableFuture.runAsync(() -> {
			List<VisitedLocation> userLocations = new CopyOnWriteArrayList<>(
					user.getVisitedLocations());
			List<Attraction> attractions = new CopyOnWriteArrayList<>(
					gpsProxy.getAttractions());
			attractions.stream()
					.filter(attraction -> user.getUserRewards().stream()
							.filter(r -> r.attraction.attractionName
									.equals(attraction.attractionName))
							.count() == 0)
					.forEach(attraction -> {
						userLocations.stream()
								.filter(visitedLocation -> nearAttraction(
										visitedLocation, attraction))
								.forEach(visitedLocation -> user.addUserReward(
										new UserReward(visitedLocation,
												attraction, getRewardPoints(
														attraction, user))));
					});
		}, executorService);
	}

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

		return rewardsProxy.getAttractionRewardPoints(attraction.attractionId,
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