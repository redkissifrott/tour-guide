package fr.redkissifrott.tourGuideTripPricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableAutoConfiguration
@EnableFeignClients("fr.redkissifrott.tourGuideTripPricer")
public class TourGuideTripPricerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourGuideTripPricerApplication.class, args);
	}

}
