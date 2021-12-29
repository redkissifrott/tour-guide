package fr.redkissifrott.tourGuideRewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideRewards.model.Location;
import fr.redkissifrott.tourGuideRewards.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
	RewardsService rewardsService;

	// @GetMapping(value = "/UserLocation/{loc1}/{loc2}")
	// Double getDistance(@PathVariable Location loc1,
	// @PathVariable Location loc2) {
	// System.out.println("KILROY WAS HERE !!!!!!!!!!!!!!!!!!!");
	// return rewardsService.getDistance(loc1, loc2);
	// }

	@RequestMapping(value = "/UserLocation", method = RequestMethod.GET)
	public Double getDistance(@RequestParam("loc1") Location loc1,
			@RequestParam("loc2") Location loc2) {
		System.out.println("KILROY WAS HERE !!!!!!!!!!!!!!!!!!!");
		return rewardsService.getDistance(loc1, loc2);
	}
	// @GetMapping(value = "/UserLocation")
	// Double getDistance(@RequestParam("loc1") Location loc1,
	// @RequestParam("loc2") Location loc2) {
	// return rewardsService.getDistance(loc1, loc2);
	// };
}
