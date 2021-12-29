package fr.redkissifrott.tourGuideRewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableAutoConfiguration
@EnableFeignClients("fr.redkissifrott.tourGuideRewards")
public class TourGuideRewardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourGuideRewardsApplication.class, args);
	}

}
