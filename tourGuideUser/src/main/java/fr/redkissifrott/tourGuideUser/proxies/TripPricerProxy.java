package fr.redkissifrott.tourGuideUser.proxies;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.redkissifrott.tourGuideUser.model.Provider;

@FeignClient(name = "tourGuideTripPricer", url = "localhost:9004")
public interface TripPricerProxy {

	@GetMapping(value = "/GetPrice/{trippricerapikey}/{userId}/{numberOfAdults}/{numberOfChildren}/{tripDuration}/{cumulatativeRewardPoints}")
	public List<Provider> getPrice(
			@PathVariable("trippricerapikey") String trippricerapikey,
			@PathVariable("userId") UUID userId,
			@PathVariable("numberOfAdults") int numberOfAdults,
			@PathVariable("numberOfChildren") int numberOfChildren,
			@PathVariable("tripDuration") int tripDuration,
			@PathVariable("cumulatativeRewardPoints") int cumulatativeRewardPoints);

}
