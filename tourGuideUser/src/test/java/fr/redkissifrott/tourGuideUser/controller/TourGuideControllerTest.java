package fr.redkissifrott.tourGuideUser.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fr.redkissifrott.tourGuideUser.DTO.UserClosestAttractionsDTO;
import fr.redkissifrott.tourGuideUser.DTO.UserPreferencesDTO;
import fr.redkissifrott.tourGuideUser.model.Location;
import fr.redkissifrott.tourGuideUser.model.Provider;
import fr.redkissifrott.tourGuideUser.model.User;
import fr.redkissifrott.tourGuideUser.model.UserReward;
import fr.redkissifrott.tourGuideUser.model.VisitedLocation;
import fr.redkissifrott.tourGuideUser.service.RewardsService;
import fr.redkissifrott.tourGuideUser.service.TourGuideService;

@WebMvcTest(controllers = TourGuideController.class)
public class TourGuideControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TourGuideService tourGuideService;

	@MockBean
	private RewardsService rewardsService;

	private User getUser(String userName) {
		return tourGuideService.getUser(userName);
	}

	private User user = new User(UUID.randomUUID(), "jon", "000",
			"jon@tourGuide.com");

	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void getLocation() throws Exception {
		Location l = new Location(-104.464735, -52.014424);
		VisitedLocation v = new VisitedLocation();
		v.setLocation(l);
		when(tourGuideService.getUserLocation(getUser("jon"))).thenReturn(v);
		this.mockMvc.perform(get("/getLocation?userName=jon"))
				.andExpect(status().isOk());
	}

	@Test
	public void getNearbyAttractions() throws Exception {
		UserClosestAttractionsDTO u = new UserClosestAttractionsDTO(null, null);
		when(tourGuideService.getUserClosestAttractions("jon")).thenReturn(u);
		this.mockMvc.perform(get("/getNearbyAttractions?userName=jon"))
				.andExpect(status().isOk());
	}

	@Test
	public void getRewards() throws Exception {
		List<UserReward> u = new ArrayList<UserReward>();
		when(tourGuideService.getUserRewards(getUser("jon"))).thenReturn(u);
		this.mockMvc.perform(get("/getRewards?userName=jon"))
				.andExpect(status().isOk());
	}

	@Test
	public void getPreferences() throws Exception {
		UserPreferencesDTO u = new UserPreferencesDTO();
		when(tourGuideService.getPreferences(getUser("jon"))).thenReturn(u);
		this.mockMvc.perform(get("/getPreferences?userName=jon"))
				.andExpect(status().isOk());
	}

	@Test
	public void postPreferences() throws Exception {
		mockMvc.perform(post("/postPreferences")
				.contentType(MediaType.APPLICATION_JSON).content(
						"{ \"userName\":\"jon\", \"numberOfAdults\":\"2\", \"numberOfChildren\":\"3\", \"tripDuration\":\"7\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void getAllCurrentLocations() throws Exception {
		HashMap<UUID, Location> h = new HashMap<UUID, Location>();
		when(tourGuideService.getAllCurrentLocations()).thenReturn(h);
		mockMvc.perform(get("/getAllCurrentLocations"))
				.andExpect(status().isOk());
	}

	@Test
	public void getTripDeals() throws Exception {
		List<Provider> p = new ArrayList<Provider>();
		when(tourGuideService.getTripDeals(getUser("jon"))).thenReturn(p);
		this.mockMvc.perform(get("/getTripDeals?userName=jon"))
				.andExpect(status().isOk());
	}
}
