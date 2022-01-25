package fr.redkissifrott.tourGuideUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("fr.redkissifrott.tourGuideUser")
public class TourGuideUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourGuideUserApplication.class, args);
	}

}
