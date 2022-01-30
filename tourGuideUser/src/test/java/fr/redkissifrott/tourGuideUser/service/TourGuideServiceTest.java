package fr.redkissifrott.tourGuideUser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import fr.redkissifrott.tourGuideUser.DTO.UserClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.DTO.UserPreferencesDTO;
import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.Provider;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserPreferences;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.RewardsProxy;
import fr.redkissifrott.tourGuideUser.proxies.TripPricerProxy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TourGuideServiceTest {

	private Logger logger = LoggerFactory.getLogger(TourGuideServiceTest.class);

	@Autowired
	private RewardsService rewardsService;
	@Autowired
	private TourGuideService tourGuideService;
	@MockBean
	private GpsUtilProxy gpsProxy;
	@MockBean
	private RewardsProxy rewardsProxy;
	@MockBean
	private TripPricerProxy tripPricerProxy;

	@Test
	public void getUserLocation() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		when(gpsProxy.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocation(user.getUserId(),
						new Location(33.81, -117.9), new Date()));
		VisitedLocation visitedLocation = tourGuideService
				.trackUserLocation(user).join();
		tourGuideService.tracker.stopTracking();
		assertEquals(visitedLocation.userId, user.getUserId());
	}

	@Test
	public void addUser() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000",
				"jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();

		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}

	@Test
	public void getAllUsers() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000",
				"jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();

		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void getAllCurrentLocations() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		Location location = new Location(
				ThreadLocalRandom.current().nextDouble(-85.05112878D,
						85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "222",
				"jon2@tourGuide.com");
		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		user.addToVisitedLocations(
				new VisitedLocation(user.getUserId(), location, new Date()));
		user2.addToVisitedLocations(
				new VisitedLocation(user.getUserId(), location, new Date()));

		Map<UUID, Location> result = tourGuideService.getAllCurrentLocations();

		assertEquals(result.size(), 2);

	}

	@Test
	public void trackUser() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		when(gpsProxy.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocation(user.getUserId(),
						new Location(33.81, -117.9), new Date()));

		VisitedLocation visitedLocation = tourGuideService
				.trackUserLocation(user).join();

		tourGuideService.tracker.stopTracking();

		assertEquals(user.getUserId(), visitedLocation.userId);
	}

	@Test
	public void getNearbyAttractions() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		Location location = new Location(
				ThreadLocalRandom.current().nextDouble(-85.05112878D,
						85.05112878D),
				ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
		tourGuideService.addUser(user);
		user.addToVisitedLocations(
				new VisitedLocation(user.getUserId(), location, new Date()));

		when(gpsProxy.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocation(user.getUserId(),
						new Location(33.81, -117.9), new Date()));
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

		when(rewardsProxy.getAttractionRewardPoints(Mockito.any(UUID.class),
				Mockito.any(UUID.class))).thenReturn(13);

		VisitedLocation visitedLocation = tourGuideService
				.trackUserLocation(user).join();

		List<Attraction> attractions = tourGuideService
				.getNearByAttractions(visitedLocation);

		UserClosestAttractionsDTO uAttractions = tourGuideService
				.getUserClosestAttractions(user.getUserName());

		tourGuideService.tracker.stopTracking();

		assertEquals(5, attractions.size());
		assertEquals(5, uAttractions.getClosestAttractions().size());
	}

	@Test
	public void getTripDeals() {

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		user.setUserPreferences(new UserPreferences(2, 3, 21));

		List<Provider> providers = new ArrayList<>();
		providers.add(new Provider(UUID.randomUUID(), "TripDeal 1", 1));
		providers.add(new Provider(UUID.randomUUID(), "TripDeal 2", 2));
		providers.add(new Provider(UUID.randomUUID(), "TripDeal 3", 3));
		providers.add(new Provider(UUID.randomUUID(), "TripDeal 4", 4));
		providers.add(new Provider(UUID.randomUUID(), "TripDeal 5", 5));

		when(tripPricerProxy.getPrice("test-server-api-key", user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(), 0))
						.thenReturn(providers);

		List<Provider> userProviders = tourGuideService.getTripDeals(user);

		tourGuideService.tracker.stopTracking();

		assertEquals(5, userProviders.size());
	}

	@Test
	public void postPreferences() {
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsProxy,
				rewardsService);

		User user = new User(UUID.randomUUID(), "jon", "000",
				"jon@tourGuide.com");
		tourGuideService.addUser(user);

		UserPreferences p = new UserPreferences(2, 3, 21);
		UserPreferencesDTO up = new UserPreferencesDTO(user.getUserName(), p);

		tourGuideService.postPreferences(up);

		assertEquals(tourGuideService.getPreferences(user).getTripDuration(),
				21);
	}

}
