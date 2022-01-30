package fr.redkissifrott.tourGuideTripPricer.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tripPricer.Provider;

@SpringBootTest
class TripPricerServiceTest {

	@Test
	void getPrice() {
		TripPricerService tripPricerService = new TripPricerService();
		List<Provider> providers = tripPricerService.getPrice(
				"test-server-api-key", UUID.randomUUID(), 2, 3, 21, 0);
		assertTrue(providers.size() > 0);
	}

}
