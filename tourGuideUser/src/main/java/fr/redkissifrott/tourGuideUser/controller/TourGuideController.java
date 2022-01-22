package fr.redkissifrott.tourGuideUser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import fr.redkissifrott.tourGuideUser.Dto.UserClosestAttractionsDTO;
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

	@RequestMapping("/getLocation")
	public String getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService
				.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
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
	@RequestMapping("/getNearbyAttractions")
	public UserClosestAttractionsDTO getNearbyAttractions(
			@RequestParam String userName) {
		// return JsonStream.serialize(
		// tourGuideService.getUserClosestAttractions(userName));
		return tourGuideService.getUserClosestAttractions(userName);
	}
	//
	@RequestMapping("/getRewards")
	public List<UserReward> getRewards(@RequestParam String userName) {
		// return JsonStream
		// .serialize(tourGuideService.getUserRewards(getUser(userName)));
		return tourGuideService.getUserRewards(getUser(userName));
	}

	// @RequestMapping("/getPreferences")

	//
	// @RequestMapping("/getAllCurrentLocations")
	// public String getAllCurrentLocations() {
	// // TODO: Get a list of every user's most recent location as JSON
	// //- Note: does not use gpsUtil to query for their current location,
	// // but rather gathers the user's current location from their stored
	// location history.
	// //
	// // Return object should be the just a JSON mapping of userId to Locations
	// similar to:
	// // {
	// // "019b04a9-067a-4c76-8817-ee75088c3822":
	// {"longitude":-48.188821,"latitude":74.84371}
	// // ...
	// // }
	//
	// return JsonStream.serialize("");
	// }
	//
	@RequestMapping("/getTripDeals")
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
