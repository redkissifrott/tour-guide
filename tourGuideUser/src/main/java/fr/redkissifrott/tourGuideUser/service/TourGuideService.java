package fr.redkissifrott.tourGuideUser.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.redkissifrott.tourGuideUser.Dto.ClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.Dto.UserClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.helper.InternalTestHelper;
import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.Provider;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.proxies.GpsUtilProxy;
import fr.redkissifrott.tourGuideUser.proxies.TripPricerProxy;
import fr.redkissifrott.tourGuideUser.tracker.Tracker;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);

	@Autowired
	private final GpsUtilProxy gpsProxy;
	@Autowired
	private final RewardsService rewardsService;
	@Autowired
	private TripPricerProxy tripPricerProxy;
	public final Tracker tracker;
	boolean testMode = true;

	@Autowired
	public TourGuideService(GpsUtilProxy gpsProxy,
			RewardsService rewardsService) {
		this.gpsProxy = gpsProxy;
		this.rewardsService = rewardsService;

		if (testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}

	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}

	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}

	public void addUser(User user) {
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}

	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}

	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream()
				.mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricerProxy.getPrice(tripPricerApiKey,
				user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(),
				cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}

	public VisitedLocation getUserLocation(User user) {

		VisitedLocation visitedLocation = (user.getVisitedLocations()
				.size() > 0)
						? user.getLastVisitedLocation()
						: trackUserLocation(user).join();
		return visitedLocation;
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(100);

	public CompletableFuture<VisitedLocation> trackUserLocation(User user) {
		Locale.setDefault(new Locale("en", "US"));

		return CompletableFuture.supplyAsync(() -> {
			logger.debug("Track1");
			VisitedLocation visitedLocation = gpsProxy
					.getUserLocation(user.getUserId());
			logger.debug("Track2");
			user.addToVisitedLocations(visitedLocation);
			logger.debug("Track3");
			rewardsService.calculateRewards(user).join();
			logger.debug("Track4");
			logger.debug("user :" + user.getUserRewards().size());

			return visitedLocation;
		}, executorService);
	}

	public List<Attraction> getNearByAttractions(
			VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = gpsProxy.getAttractions().stream()
				.sorted(Comparator.comparing(attraction -> rewardsService
						.getDistance(attraction, visitedLocation.location)))
				.limit(5).collect(Collectors.toList());

		return nearbyAttractions;
	}

	public UserClosestAttractionsDTO getUserClosestAttractions(
			String userName) {
		VisitedLocation visitedLocation = getUserLocation(getUser(userName));
		List<Attraction> nearbyAttractions = getNearByAttractions(
				visitedLocation);
		List<ClosestAttractionsDTO> closestAttractionsDTOs = new ArrayList<>();
		nearbyAttractions.forEach(attraction -> closestAttractionsDTOs
				.add(new ClosestAttractionsDTO(attraction.attractionName,
						attraction.latitude, attraction.longitude,
						rewardsService.getDistance(attraction,
								visitedLocation.location),
						rewardsService.getAttractionRewardPoints(
								attraction.attractionId,
								getUser(userName).getUserId()))));
		return new UserClosestAttractionsDTO(visitedLocation.location,
				closestAttractionsDTOs);

	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				tracker.stopTracking();
			}
		});
	}
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing
	// purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber())
				.forEach(i -> {
					String userName = "internalUser" + i;
					String phone = "000";
					String email = userName + "@tourGuide.com";
					User user = new User(UUID.randomUUID(), userName, phone,
							email);
					generateUserLocationHistory(user);

					internalUserMap.put(userName, user);
					// logger.debug("Created " + user.getLastVisitedLocation());
				});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber()
				+ " internal test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.addToVisitedLocations(
					new VisitedLocation(user.getUserId(),
							new Location(generateRandomLatitude(),
									generateRandomLongitude()),
							getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
		double rightLimit = 180;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
		double rightLimit = 85.05112878;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now()
				.minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

}
