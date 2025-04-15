package com.cyberapple.followme.entities;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Excursion {

    private String id;

    private String title;

    private String description;

    private LocalDate date;

    @Override
    public String toString() {
        return "Excursion{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
