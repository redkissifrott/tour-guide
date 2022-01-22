package fr.redkissifrott.tourGuideUser.proxies;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.redkissifrott.tourGuideUser.model.Attraction;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;

@FeignClient(name = "tourGuideGps", url = "localhost:9002")
public interface GpsUtilProxy {

	@GetMapping(value = "/UserLocation/{userId}")
	VisitedLocation getUserLocation(
			@PathVariable(required = true, name = "userId") UUID userId);

	@GetMapping(value = "/Attractions")
	List<Attraction> getAttractions();

}
