package fr.redkissifrott.tourGuideUser;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public Locale getLocale() {
		Locale.setDefault(Locale.US);
		return Locale.getDefault();
	}

	// @Bean
	// public RewardsService getRewardsService() {
	// return new RewardsService(getGpsUtil(), getRewardCentral());
	// }
}
