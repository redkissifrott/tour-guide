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
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	@GetMapping("/getLocation")
	public Location getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService
				.getUserLocation(getUser(userName));
		// return JsonStream.serialize(visitedLocation.location);
		return visitedLocation.location;
	}

	// // Get the closest five tourist attractions to the user - no
	// matter how far away they are.
	// // Return a new JSON object that contains:
	// // Name of Tourist attraction,
	// // Tourist attractions lat/long,
	// // The user's location lat/long,
	// // The distance in miles between the user's location and each of the
	// attractions.
	// // The reward points for visiting each Attraction.
	@GetMapping("/getNearbyAttractions")
	public UserClosestAttractionsDTO getNearbyAttractions(
			@RequestParam String userName) {
		// return JsonStream.serialize(
		// tourGuideService.getUserClosestAttractions(userName));
		return tourGuideService.getUserClosestAttractions(userName);
	}
	//
	@GetMapping("/getRewards")
	public List<UserReward> getRewards(@RequestParam String userName) {
		// return JsonStream
		// .serialize(tourGuideService.getUserRewards(getUser(userName)));
		return tourGuideService.getUserRewards(getUser(userName));
	}

	@GetMapping("/getPreferences")
	public UserPreferencesDTO getPreferences(@RequestParam String userName) {
		return tourGuideService.getPreferences(getUser(userName));
	}

	@PostMapping(value = "/postPreferences", consumes = {
			MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
	// public void postPreference(
	// @RequestBody UserPreferencesDTO userPreferencesDTO) {
	public void postPreference(
			@RequestBody UserPreferencesDTO userPreferencesDTO) {
		tourGuideService.postPreferences(userPreferencesDTO);
	}

	// Return object should be the just a JSON mapping of userId to
	// Locations
	// similar to:
	// {
	// "019b04a9-067a-4c76-8817-ee75088c3822":
	// {"longitude":-48.188821,"latitude":74.84371}
	// ...
	// }
	@GetMapping("/getAllCurrentLocations")
	public HashMap<UUID, Location> getAllCurrentLocations() {
		// return
		// JsonStream.serialize(tourGuideService.getAllCurrentLocations());
		return tourGuideService.getAllCurrentLocations();
	}

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
