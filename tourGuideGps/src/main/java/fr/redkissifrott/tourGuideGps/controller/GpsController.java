package fr.redkissifrott.tourGuideGps.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideGps.service.GpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsController {

	@Autowired
	GpsService gpsService;

	@GetMapping(value = "/UserLocation/{userId}")
	public VisitedLocation getUserLocation(
			@PathVariable("userId") UUID userId) {
		return gpsService.getUserLocation(userId);
	}

	@GetMapping(value = "/Attractions")
	List<Attraction> getAttractions() throws Exception {
		List<Attraction> attractions = gpsService.getAttractions();
		if (attractions.isEmpty())
			throw new Exception("Aucune attraction trouvée");
		return attractions;
	}

}
