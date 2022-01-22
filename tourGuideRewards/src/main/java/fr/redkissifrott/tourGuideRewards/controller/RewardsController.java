package fr.redkissifrott.tourGuideRewards.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideRewards.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
	RewardsService rewardsService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	// @GetMapping(value = "/UserLocation/{loc1}/{loc2}")
	// Double getDistance(@PathVariable Location loc1,
	// @PathVariable Location loc2) {
	// System.out.println("KILROY WAS HERE !!!!!!!!!!!!!!!!!!!");
	// return rewardsService.getDistance(loc1, loc2);
	// }

	@GetMapping(value = "/AttractionRewardPoints/{attractionId}/{userId}")
	public int getAttractionRewardPoints(
			@PathVariable("attractionId") UUID attractionId,
			@PathVariable("userId") UUID userId) {
		return rewardsService.getAttractionRewardPoints(attractionId, userId);

	}
}
