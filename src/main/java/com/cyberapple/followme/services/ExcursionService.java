package com.cyberapple.followme.services;

import com.cyberapple.followme.entities.Excursion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcursionService {
    List<Excursion> excursions = new ArrayList<>();

    public ExcursionService() {
        Excursion excursion1 = new Excursion();
        excursion1.setId("1");
        excursion1.setTitle("High Tatras Hike (Slovakia)");
        excursion1.setDescription("Spend a day in the heart of the Slovak mountains, hiking scenic trails, spotting waterfalls, and enjoying panoramic views from alpine peaks.");
        excursion1.setDate(LocalDate.parse("2025-04-15"));

        excursions.add(excursion1);

        Excursion excursion2 = new Excursion();
        excursion2.setId("2");
        excursion2.setTitle("Lake Bled Adventure (Slovenia)");
        excursion2.setDescription("Explore the stunning Lake Bled, take a boat ride to the island, and hike up to Bled Castle for breathtaking views.");
        excursion2.setDate(LocalDate.parse("2025-05-20"));

        excursions.add(excursion2);

        Excursion excursion3 = new Excursion();
        excursion3.setId("3");
        excursion3.setTitle("Plitvice Lakes National Park (Croatia)");
        excursion3.setDescription("Discover the beauty of Plitvice Lakes, with its cascading waterfalls and vibrant turquoise lakes, on a guided tour.");
        excursion3.setDate(LocalDate.parse("2025-06-10"));

        excursions.add(excursion3);
    }

    public List<Excursion> getAllExcursions() {
        return excursions;
    }

    public Excursion getExcursionById(String id) {
        // TODO: Add error handling here
        for (Excursion excursion : excursions) {
            if (excursion.getId().equals(id)) {
                return excursion;
            }
        }
        return null;
    }
}
