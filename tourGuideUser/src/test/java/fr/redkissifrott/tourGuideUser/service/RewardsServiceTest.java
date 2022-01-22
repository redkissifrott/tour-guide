package fr.redkissifrott.tourGuideUser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;

class RewardsServiceTest {

	private Logger logger = LoggerFactory.getLogger(RewardsServiceTest.class);

	@Autowired
	private GpsUtilProxy gpsUtil;
	@Autowired
	private RewardsService rewardsService;

	@Test
	public void userGetRewards() {

		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		Attraction attraction = gpsUtil.getAttractions().get(0);
		user.addToVisitedLocations(
				new VisitedLocation(user.getUserId(), attraction, new Date()));
		tourGuideService.trackUserLocation(user);
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();
		assertTrue(userRewards.size() == 1);
	}

	@Test
	public void isWithinAttractionProximity() {
		Attraction attraction = gpsUtil.getAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction,
				attraction));
	}

	// @Ignore // Needs fixed - can throw ConcurrentModificationException
	@Test
	public void nearAllAttractions()
			throws InterruptedException, ExecutionException {

		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
		rewardsService.setAttractionProximityRange(Integer.MAX_VALUE);
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil,
				rewardsService);

		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0))
				.get();
		List<UserReward> userRewards = tourGuideService
				.getUserRewards(tourGuideService.getAllUsers().get(0));
		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
	}

}
