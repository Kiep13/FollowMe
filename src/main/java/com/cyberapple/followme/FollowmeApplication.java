package com.cyberapple.followme;

import com.cyberapple.followme.entities.Excursion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class FollowmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowmeApplication.class, args);

		System.out.println("Testing Excursion entity");

		Excursion excursion1 = new Excursion();
		excursion1.setId("1");
		excursion1.setTitle("High Tatras Hike (Slovakia)");
		excursion1.setDescription("Spend a day in the heart of the Slovak mountains, hiking scenic trails, spotting waterfalls, and enjoying panoramic views from alpine peaks.");
		excursion1.setDate(LocalDate.parse("2025-04-15"));

		System.out.println(excursion1);

		Excursion excursion2 = new Excursion();
		excursion2.setId("2");
		excursion2.setTitle("Lake Bled Adventure (Slovenia)");
		excursion2.setDescription("Explore the stunning Lake Bled, take a boat ride to the island, and hike up to Bled Castle for breathtaking views.");
		excursion2.setDate(LocalDate.parse("2025-05-20"));

		System.out.println(excursion2);

		Excursion excursion3 = new Excursion();
		excursion3.setId("3");
		excursion3.setTitle("Plitvice Lakes National Park (Croatia)");
		excursion3.setDescription("Discover the beauty of Plitvice Lakes, with its cascading waterfalls and vibrant turquoise lakes, on a guided tour.");
		excursion3.setDate(LocalDate.parse("2025-06-10"));

		System.out.println(excursion3);

	}

}
