package fr.redkissifrott.tourGuideRewards.proxies;

import java.util.Collection;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.redkissifrott.tourGuideRewards.model.Attraction;
import fr.redkissifrott.tourGuideRewards.model.VisitedLocation;

@FeignClient(name = "tourGuideGps", url = "localhost:9002")
public interface GpsUtilProxy {

	@GetMapping(value = "/UserLocation/{userId}")
	VisitedLocation getUserLocation(@PathVariable("userId") UUID userId);

	@GetMapping(value = "/Attractions")
	Collection<Attraction> getAttractions();

}