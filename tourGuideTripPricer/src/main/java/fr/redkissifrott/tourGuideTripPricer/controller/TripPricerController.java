package fr.redkissifrott.tourGuideTripPricer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideTripPricer.service.TripPricerService;
import tripPricer.Provider;

@RestController
public class TripPricerController {
	@Autowired
	TripPricerService tripPricerService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	@GetMapping(value = "/GetPrice/{trippricerapikey}/{userId}/{numberOfAdults}/{numberOfChildren}/{tripDuration}/{cumulatativeRewardPoints}")
	public List<Provider> getPrice(
			@PathVariable("trippricerapikey") String trippricerapikey,
			@PathVariable("userId") UUID userId,
			@PathVariable("numberOfAdults") int numberOfAdults,
			@PathVariable("numberOfChildren") int numberOfChildren,
			@PathVariable("tripDuration") int tripDuration,
			@PathVariable("cumulatativeRewardPoints") int cumulatativeRewardPoints) {
		List<Provider> providers = tripPricerService.getPrice(trippricerapikey,
				userId, numberOfAdults, numberOfChildren, tripDuration,
				cumulatativeRewardPoints);
		return providers;
	}
}
