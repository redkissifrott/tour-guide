package fr.redkissifrott.tourGuideRewards.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardsService {
	private final RewardCentral rewardsCentral;

	public RewardsService(RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;
	}

	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
