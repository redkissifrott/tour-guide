package fr.redkissifrott.tourGuideUser.testPerformances;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.RewardsProxy;
import fr.redkissifrott.tourGuideUser.proxies.TripPricerProxy;
import fr.redkissifrott.tourGuideUser.service.RewardsService;
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

@SpringBootTest
class TestTrackLocation {

	@Autowired
	private GpsUtilProxy gpsUtil;
	@Autowired
	private RewardsProxy rewardsProxy;
	@Autowired
	private TripPricerProxy tripPricerProxy;
	@Autowired
	private RewardsService rewardsService;

	@Test
	public void highVolumeTrackLocation() {
		// Users should be incremented up to 100,000, and test finishes within
		// 15 minutes
		rewardsService.setProximityBuffer(10);
		rewardsService.setAttractionProximityRange(200);
		InternalTestHelper.setInternalUserNumber(100);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil,
				rewardsService);

		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<CompletableFuture<VisitedLocation>> usersLocations = new ArrayList<CompletableFuture<VisitedLocation>>();
		for (User user : allUsers) {
			usersLocations.add(tourGuideService.trackUserLocation(user));
		}
		for (CompletableFuture<VisitedLocation> userLocation : usersLocations) {
			userLocation.join();
		}
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
				+ " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS
				.toSeconds(stopWatch.getTime()));
	}

}
