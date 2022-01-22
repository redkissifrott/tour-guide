package fr.redkissifrott.tourGuideUser.testPerformances;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.service.RewardsService;
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

class TestTrackLocation {

	@Autowired
	private GpsUtilProxy gpsUtil;
	@Autowired
	private RewardsService rewardsService;

	@Test
	public void highVolumeTrackLocation() {
		// GpsUtilProxy gpsUtil = new GpsUtilProxy();
		// final GpsUtilProxy gpsUtil;
		// final RewardsProxy rewardsService;
		// Users should be incremented up to 100,000, and test finishes within
		// 15 minutes
		InternalTestHelper.setInternalUserNumber(100);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil,
				rewardsService);

		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			tourGuideService.trackUserLocation(user);
		}
		stopWatch.stop();
		// tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
				+ " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS
				.toSeconds(stopWatch.getTime()));
	}

}
