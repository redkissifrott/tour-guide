package fr.redkissifrott.tourGuideTripPricer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.redkissifrott.tourGuideTripPricer.service.TripPricerService;
import tripPricer.Provider;

@WebMvcTest(controllers = TripPricerController.class)
class TripPricerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TripPricerService tripPricerService;

	@Test
	public void getPrice() throws Exception {
		List<Provider> p = new ArrayList<Provider>();
		when(tripPricerService.getPrice(null, null, 0, 0, 0, 0)).thenReturn(p);
		this.mockMvc.perform(get(
				"/GetPrice/test-server-api-key/ecc2db96-fdf1-4f47-81f0-d41e98f4640e/3/7/21/50"))
				.andExpect(status().isOk());
	}

}
