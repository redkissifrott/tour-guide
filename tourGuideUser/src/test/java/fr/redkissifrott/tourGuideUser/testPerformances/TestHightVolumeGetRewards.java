package fr.redkissifrott.tourGuideUser.testPerformances;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.RewardsProxy;
import fr.redkissifrott.tourGuideUser.service.RewardsService;
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

@SpringBootTest
public class TestHightVolumeGetRewards {

	@Autowired
	private GpsUtilProxy gpsUtil;
	@Autowired
	private RewardsProxy rewardsProxy;

	@Test
	public void highVolumeGetRewards() {
		// Users should be incremented up to 100,000, and test finishes within
		// 20 minutes
		RewardsService rewardsService = new RewardsService(gpsUtil,
				rewardsProxy);
		rewardsService.setProximityBuffer(10);
		rewardsService.setAttractionProximityRange(200);
		InternalTestHelper.setInternalUserNumber(100000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		TourGuideService tourGuideService = new TourGuideService(gpsUtil,
				rewardsService);

		Attraction attraction = gpsUtil.getAttractions().get(0);
		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		allUsers.forEach(u -> u.addToVisitedLocations(
				new VisitedLocation(u.getUserId(), attraction, new Date())));

		List<CompletableFuture<Void>> rewards = new ArrayList<>();
		allUsers.forEach(u -> {
			rewards.add(rewardsService.calculateRewards(u));
		});

		for (CompletableFuture<Void> reward : rewards) {
			reward.join();
		}

		for (User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}

		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
				+ " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS
				.toSeconds(stopWatch.getTime()));
	}

}
