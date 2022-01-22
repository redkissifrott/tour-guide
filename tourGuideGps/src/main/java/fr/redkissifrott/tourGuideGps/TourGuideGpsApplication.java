package fr.redkissifrott.tourGuideGps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAutoConfiguration
@EnableFeignClients("fr.redkissifrott.tourGuideGps")
@SpringBootApplication
public class TourGuideGpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourGuideGpsApplication.class, args);
	}

}
