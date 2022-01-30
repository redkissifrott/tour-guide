package fr.redkissifrott.tourGuideUser.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.redkissifrott.tourGuideUser.DTO.UserClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.Provider;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserPreferences;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.TripPricerProxy;

@SpringBootTest
public class TourGuideIntegrationTest {

	@Autowired
	GpsUtilProxy gpsProxy;

	@Autowired
	TripPricerProxy tripPricerProxy;

	@Autowired
	RewardsService rewardsService;

	@Autowired
	TourGuideService tourGuideService;

	User user;

	@Test
	public void getClosestAttraction() throws InterruptedException {
		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(),
				new Location(28.012804D, -82.469269D), new Date()));
		tourGuideService.addUser(user);

		UserClosestAttractionsDTO uAttractions = tourGuideService
				.getUserClosestAttractions(user.getUserName());

		assertEquals(5, uAttractions.getClosestAttractions().size());
	}

	@Test
	public void getTripDeals() {

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		user.setUserPreferences(new UserPreferences(2, 3, 21));

		List<Provider> userProviders = tourGuideService.getTripDeals(user);

		tourGuideService.tracker.stopTracking();

		assertEquals(5, userProviders.size());
	}

	@Test
	public void trackUser() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");

		VisitedLocation visitedLocation = tourGuideService
				.trackUserLocation(user).join();

		tourGuideService.tracker.stopTracking();

		assertEquals(user.getUserId(), visitedLocation.userId);
	}

}
