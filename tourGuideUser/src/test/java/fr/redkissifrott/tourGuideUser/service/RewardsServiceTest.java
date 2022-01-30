package fr.redkissifrott.tourGuideUser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.RewardsProxy;

@RunWith(SpringRunner.class)
@SpringBootTest
class RewardsServiceTest {

	private Logger logger = LoggerFactory.getLogger(RewardsServiceTest.class);

	@MockBean
	private GpsUtilProxy gpsProxy;
	@MockBean
	private RewardsProxy rewardsProxy;
	@Autowired
	private RewardsService rewardsService;

	@BeforeEach
	public void init() {

		List<Attraction> attractionMock = new ArrayList<>();
		attractionMock.add(new Attraction("Disneyland", "Anaheim", "CA",
				33.817595D, -117.922008D));
		attractionMock.add(new Attraction("Jackson Hole", "Jackson Hole", "WY",
				43.582767D, -110.821999D));
		attractionMock.add(new Attraction("Mojave National Preserve", "Kelso",
				"CA", 35.141689D, -115.510399D));
		attractionMock.add(new Attraction("Neyland Stadium", "Knoxville", "TN",
				35.955013D, -83.925011D));
		attractionMock.add(new Attraction("Kyle Field", "College Station", "TX",
				30.61025D, -96.339844D));
		attractionMock.add(new Attraction("San Diego Zoo", "San Diego", "CA",
				32.735317D, -117.149048D));
		attractionMock.add(new Attraction("Zoo Tampa at Lowry Park", "Tampa",
				"FL", 28.012804D, -82.469269D));
		attractionMock.add(new Attraction("Franklin Park Zoo", "Boston", "MA",
				42.302601D, -71.086731D));
		attractionMock.add(new Attraction("El Paso Zoo", "El Paso", "TX",
				31.769125D, -106.44487D));

		attractionMock.forEach(
				attraction -> attraction.setAttractionId(UUID.randomUUID()));
		rewardsService.setAttractionsList(attractionMock);

		when(gpsProxy.getAttractions()).thenReturn(attractionMock);

		when(gpsProxy.getUserLocation(null)).thenReturn(new VisitedLocation(
				UUID.fromString("211468fa-b61f-4f9d-999f-fb17a2896633"),
				new Location(33.81, -117.9), new Date()));
	}

	@Test
	public void userGetRewards() {

		InternalTestHelper.setInternalUserNumber(0);
		RewardsService rewardsService = new RewardsService(gpsProxy,
				rewardsProxy);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		when(gpsProxy.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocation(
						UUID.fromString("211468fa-b61f-4f9d-999f-fb17a2896633"),
						new Location(33.81, -117.9), new Date()));

		Attraction attraction = gpsProxy.getAttractions().get(0);
		logger.debug("attractions " + attraction.attractionName);

		user.addToVisitedLocations(
				new VisitedLocation(user.getUserId(), attraction, new Date()));

		tourGuideService.trackUserLocation(user).join();
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();
		assertTrue(userRewards.size() == 9);
	}

	@Test
	public void isWithinAttractionProximity() {
		Attraction attraction = gpsProxy.getAttractions().get(0);
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
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0))
				.get();
		List<UserReward> userRewards = tourGuideService
				.getUserRewards(tourGuideService.getAllUsers().get(0));
		tourGuideService.tracker.stopTracking();

		assertEquals(gpsProxy.getAttractions().size(), userRewards.size());
	}

}
