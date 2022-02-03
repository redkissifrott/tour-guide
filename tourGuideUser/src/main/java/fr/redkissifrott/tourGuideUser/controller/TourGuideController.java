package fr.redkissifrott.tourGuideUser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideUser.DTO.UserClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.DTO.UserPreferencesDTO;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.Provider;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.service.RewardsService;
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;

	@Autowired
	RewardsService rewardsService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	/**
	 * Controller method used to return the last saved user location.
	 *
	 * @param userName
	 * @return user location or error 400
	 */
	@GetMapping("/getLocation")
	public Location getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService
				.getUserLocation(getUser(userName));
		// return JsonStream.serialize(visitedLocation.location);
		return visitedLocation.location;
	}
	/**
	 * Method controller used to get the five user's closest attractions.
	 *
	 * @param userName
	 * @return JSON object that contains: the 5 user's closest attractions Name
	 *         of Tourist attraction, Tourist attractions lat/long, The user's
	 *         location lat/long, The distance in miles between the user's
	 *         location and each of the attractions. The reward points for
	 *         visiting each Attraction
	 */
	@GetMapping("/getNearbyAttractions")
	public UserClosestAttractionsDTO getNearbyAttractions(
			@RequestParam String userName) {
		// return JsonStream.serialize(
		// tourGuideService.getUserClosestAttractions(userName));
		return tourGuideService.getUserClosestAttractions(userName);
	}

	/**
	 * Method controller used to get user's rewards.
	 *
	 * @param userName
	 * @return user's rewards
	 */
	@GetMapping("/getRewards")
	public List<UserReward> getRewards(@RequestParam String userName) {
		// return JsonStream
		// .serialize(tourGuideService.getUserRewards(getUser(userName)));
		return tourGuideService.getUserRewards(getUser(userName));
	}

	/**
	 * @param userName
	 * @return user preferences
	 */
	@GetMapping("/getPreferences")
	public UserPreferencesDTO getPreferences(@RequestParam String userName) {
		return tourGuideService.getPreferences(getUser(userName));
	}

	/**
	 * Method controller used to update user preferences
	 * 
	 * @param userPreferencesDTO
	 */
	@PostMapping(value = "/postPreferences", consumes = {
			MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void postPreference(
			@RequestBody UserPreferencesDTO userPreferencesDTO) {
		tourGuideService.postPreferences(userPreferencesDTO);
	}

	/**
	 * Controller method used to return all the last users locations saved (the
	 * last visited location saved in the history).
	 *
	 * @return all users locations
	 */
	@GetMapping("/getAllCurrentLocations")
	public HashMap<UUID, Location> getAllCurrentLocations() {
		// return
		// JsonStream.serialize(tourGuideService.getAllCurrentLocations());
		return tourGuideService.getAllCurrentLocations();
	}

	/**
	 * Method controller used to get user's trip deals.
	 *
	 * @param userName
	 * @return user's trip deals
	 */
	@GetMapping("/getTripDeals")
	public List<Provider> getTripDeals(@RequestParam String userName) {
		List<Provider> providers = tourGuideService
				.getTripDeals(getUser(userName));
		// return JsonStream.serialize(providers);
		return providers;
	}

	private User getUser(String userName) {
		return tourGuideService.getUser(userName);
	}
}
