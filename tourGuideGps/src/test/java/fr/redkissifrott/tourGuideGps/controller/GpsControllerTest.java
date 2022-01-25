package fr.redkissifrott.tourGuideGps.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.redkissifrott.tourGuideGps.service.GpsService;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@WebMvcTest(controllers = GpsController.class)
class GpsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GpsService gpsService;

	@Test
	public void getUserLocation() throws Exception {
		VisitedLocation v = new VisitedLocation(null, null, null);
		when(gpsService.getUserLocation(UUID.randomUUID())).thenReturn(v);
		this.mockMvc
				.perform(get(
						"/UserLocation/ecc2db96-fdf1-4f47-81f0-d41e98f4640e"))
				.andExpect(status().isOk());
	}

	@Test
	public void getAttractions() throws Exception {
		List<Attraction> a = new ArrayList<Attraction>();
		when(gpsService.getAttractions()).thenReturn(a);
		this.mockMvc.perform(get("/Attractions")).andExpect(status().isOk());
	}

}
