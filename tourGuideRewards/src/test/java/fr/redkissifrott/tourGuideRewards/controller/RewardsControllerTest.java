package fr.redkissifrott.tourGuideRewards.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.redkissifrott.tourGuideRewards.service.RewardsService;

@WebMvcTest(controllers = RewardsController.class)
class RewardsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RewardsService rewardsService;

	@Test
	public void getAttractionRewardPoints() throws Exception {
		when(rewardsService.getAttractionRewardPoints(UUID.randomUUID(),
				UUID.randomUUID())).thenReturn(13);
		this.mockMvc.perform(get(
				"/AttractionRewardPoints/ecc2db96-fdf1-4f47-81f0-d41e98f4640e/ecc2db96-fdf1-4f47-81f0-d41e98f4640e"))
				.andExpect(status().isOk());
	}

}
