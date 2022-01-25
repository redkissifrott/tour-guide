package fr.redkissifrott.tourGuideRewards.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideRewards.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
	RewardsService rewardsService;

	@GetMapping(value = "/AttractionRewardPoints/{attractionId}/{userId}")
	public int getAttractionRewardPoints(
			@PathVariable("attractionId") UUID attractionId,
			@PathVariable("userId") UUID userId) {
		return rewardsService.getAttractionRewardPoints(attractionId, userId);

	}
}
