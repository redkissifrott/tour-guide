package fr.redkissifrott.tourGuideGps.service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@Service
public class GpsService {

	private final GpsUtil gpsUtil = new GpsUtil();

	public VisitedLocation getUserLocation(UUID userId) {
		Locale.setDefault(new Locale("en", "US"));
		return gpsUtil.getUserLocation(userId);
	}

	public List<Attraction> getAttractions() {
		return gpsUtil.getAttractions();
	}

}
