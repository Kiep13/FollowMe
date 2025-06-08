package com.cyberapple.followme.entities;

import com.cyberapple.followme.records.UserInput;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role = "USER";

    public User(UserInput userInput) {
        this.firstName = userInput.firstName();
        this.lastName = userInput.lastName();
        this.email = userInput.email();
    }
}
