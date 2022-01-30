package fr.redkissifrott.tourGuideRewards.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import rewardCentral.RewardCentral;

@SpringBootTest
class RewardsServiceTest {

	private final RewardCentral rewardsCentral = new RewardCentral();

	@Test
	void getAttractionRewardPoints() {
		RewardsService rewardsService = new RewardsService(rewardsCentral);
		int rewardsPoints = rewardsService.getAttractionRewardPoints(
				UUID.randomUUID(), UUID.randomUUID());
		assertTrue(rewardsPoints != 0);
	}

}
