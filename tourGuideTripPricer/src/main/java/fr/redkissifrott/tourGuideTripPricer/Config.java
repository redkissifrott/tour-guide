package fr.redkissifrott.tourGuideTripPricer;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import tripPricer.TripPricer;

@Configuration
public class Config {
	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}
}
