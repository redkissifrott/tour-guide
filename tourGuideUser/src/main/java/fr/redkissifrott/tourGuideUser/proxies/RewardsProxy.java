package fr.redkissifrott.tourGuideUser.proxies;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.Location;

@FeignClient(name = "tourGuideRewards", url = "localhost:9003")
public interface RewardsProxy {

	//

	// @GetMapping(value = "/UserLocation/{loc1}/{loc2}")
	// Double getDistance(@PathVariable("loc1") final Location loc1,
	// @PathVariable("loc2") final Location loc2);
	//
	// @RequestMapping(value = "/UserLocation", method = RequestMethod.GET)
	// public Double getDistance(@RequestParam("loc1long") Double Location
	// location,
	// @RequestParam("loc2") Location loc2);

	@GetMapping(value = "/AttractionRewardPoints/{attractionId}/{userId}")
	public int getAttractionRewardPoints(
			@PathVariable("attractionId") UUID attractionId,
			@PathVariable("userId") UUID userId);

	@GetMapping(value = "/GetDistance/{attraction}/{userLocation}")
	public Double getDistance(@PathVariable("attraction") Attraction attraction,
			@PathVariable("userLocation") Location userLocation);

	// @GetMapping(value = "/UserLocation")
	// Double getDistance(@RequestParam("loc1") Location loc1,
	// @RequestParam("loc2") Location loc2);
}
