package fr.redkissifrott.tourGuideGps.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@SpringBootTest
class GpsServiceTest {

	@Test
	void getUserLocation() {
		GpsService gpsService = new GpsService();
		VisitedLocation location = gpsService
				.getUserLocation(UUID.randomUUID());
		assertTrue(location != null);
	}

	@Test
	void getAttractions() {
		GpsService gpsService = new GpsService();
		List<Attraction> attractions = gpsService.getAttractions();
		assertTrue(attractions.size() == 26);
	}

}
