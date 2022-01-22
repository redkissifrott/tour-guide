package fr.redkissifrott.tourGuideGps.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.redkissifrott.tourGuideGps.service.GpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsController {

	@Autowired
	GpsService gpsService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	@RequestMapping(value = "/UserLocation/{userId}", method = RequestMethod.GET)
	public VisitedLocation getUserLocation(
			@PathVariable(required = true, name = "userId") UUID userId) {
		return gpsService.getUserLocation(userId);
	}

	@RequestMapping(value = "/Attractions", method = RequestMethod.GET)
	List<Attraction> getAttractions() throws Exception {
		List<Attraction> attractions = gpsService.getAttractions();
		if (attractions.isEmpty())
			throw new Exception("Aucune attraction trouvée");
		return attractions;
	}

}
