package com.cyberapple.followme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.repositories.ExcursionRepository;

@SpringBootApplication
public class FollowMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowMeApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(ExcursionRepository excursionRepository) {
		return (args) -> {
			Excursion excursion1 = new Excursion();
			excursion1.setTitle("High Tatras Hike (Slovakia)");
			excursion1.setImageUrl("https://breaking-the-borders.com/wp-content/uploads/2023/10/tatras-slovakia-hikes-beginners.webp");
			excursion1.setDescription("Spend a day hiking scenic trails, spotting waterfalls, and enjoying panoramic views from alpine peaks in the High Tatras.");
			excursion1.setDate(LocalDate.parse("2025-06-05"));
			excursionRepository.save(excursion1);

			Excursion excursion2 = new Excursion();
			excursion2.setTitle("Plitvice Lakes National Park (Croatia)");
			excursion2.setImageUrl("https://www.akvillas.com/-/media/akvillas/experiences/croatia/explore-the-stunning-plitvice-lakes/main_plitvice-lakes-national-park--4.jpg?la=en&hash=F83198B9EC159C2B34122648744CFA698321D201");
			excursion2.setDescription("Discover the beauty of Plitvice Lakes, with its cascading waterfalls and vibrant turquoise lakes, on a guided tour.");
			excursion2.setDate(LocalDate.parse("2025-06-10"));
			excursionRepository.save(excursion2);

			Excursion excursion3 = new Excursion();
			excursion3.setTitle("Vienna City Tour (Austria)");
			excursion3.setImageUrl("https://cdn.getyourguide.com/img/location/5ffebf9a37c53.jpeg/88.jpg");
			excursion3.setDescription("Discover the imperial charm of Vienna: Schönbrunn Palace, St. Stephen’s Cathedral, and a taste of classic Viennese coffee culture.");
			excursion3.setDate(LocalDate.parse("2025-06-15"));
			excursionRepository.save(excursion3);

			Excursion excursion4 = new Excursion();
			excursion4.setTitle("Kraków Old Town & Wawel Castle (Poland)");
			excursion4.setImageUrl("https://cdn-imgix.headout.com/media/images/02c29dbbd22083c5b54d1b127bdbb1a9-26296-krakow-krakow-wawel-castle--cathedral-and-old-town-with-st.-mary-s-basilica-visiting-03.jpg?auto=format&w=1051.2&h=540&q=90&fit=fit");
			excursion4.setDescription("Explore cobbled streets, medieval churches, and the legendary Wawel Castle in one of Europe’s most beautiful historic cities.");
			excursion4.setDate(LocalDate.parse("2025-06-18"));
			excursionRepository.save(excursion4);

			Excursion excursion5 = new Excursion();
			excursion5.setTitle("Tuscany Flavors Day Trip (Italy)");
			excursion5.setImageUrl("https://media-cdn.tripadvisor.com/media/attractions-splice-spp-674x446/09/c8/9f/58.jpg");
			excursion5.setDescription("Indulge in a full-day escape to the Tuscan countryside with wine tastings, local cheeses, and a rustic Italian lunch.");
			excursion5.setDate(LocalDate.parse("2025-06-20"));
			excursionRepository.save(excursion5);

			Excursion excursion6 = new Excursion();
			excursion6.setTitle("Paris City Walk & Seine Cruise (France)");
			excursion6.setImageUrl("https://cdn.tourcms.com/a/11676/2130/1/default.jpg");
			excursion6.setDescription("Discover Parisian charm on foot, visiting iconic landmarks like the Eiffel Tower and Notre-Dame, finishing with a relaxing Seine river cruise.");
			excursion6.setDate(LocalDate.parse("2025-06-22"));
			excursionRepository.save(excursion6);

			Excursion excursion7 = new Excursion();
			excursion7.setTitle("Black Forest Adventure (Germany)");
			excursion7.setImageUrl("https://d1hirb55zrpywb.cloudfront.net/macs-adventure-tours/tours/WSBOSCHW/tourimagegallery/ss-1500-typical-black-forest-scene-rsz-15032023132529449.jpg");
			excursion7.setDescription("Hike through lush forests, see traditional villages, and taste authentic Black Forest cake in Germany’s famous Schwarzwald region.");
			excursion7.setDate(LocalDate.parse("2025-06-25"));
			excursionRepository.save(excursion7);

			Excursion excursion8 = new Excursion();
			excursion8.setTitle("Lake Bled & Spa Relaxation (Slovenia)");
			excursion8.setImageUrl("https://cdn.relaxos.sk/image/1/737x493.78/43753.jpeg");
			excursion8.setDescription("Visit the fairy-tale Lake Bled, explore its charming island church, and unwind with a thermal spa experience.");
			excursion8.setDate(LocalDate.parse("2025-06-28"));
			excursionRepository.save(excursion8);

			Excursion excursion9 = new Excursion();
			excursion9.setTitle("Danube River Kayak Tour (Austria/Slovakia)");
			excursion9.setImageUrl("https://media.tacdn.com/media/attractions-splice-spp-674x446/10/6f/35/30.jpg");
			excursion9.setDescription("Enjoy a scenic kayak trip along the Danube River, passing castles, cliffs, and nature reserves on a fun outdoor adventure.");
			excursion9.setDate(LocalDate.parse("2025-07-01"));
			excursionRepository.save(excursion9);

			Excursion excursion10 = new Excursion();
			excursion10.setTitle("Caving Adventure in Aggtelek (Hungary)");
			excursion10.setImageUrl("https://media-cdn.tripadvisor.com/media/attractions-splice-spp-674x446/06/70/57/90.jpg");
			excursion10.setDescription("Explore the mystical Baradla Cave system, discovering stunning underground formations and fascinating local legends.");
			excursion10.setDate(LocalDate.parse("2025-07-03"));
			excursionRepository.save(excursion10);
		};
	}
}
