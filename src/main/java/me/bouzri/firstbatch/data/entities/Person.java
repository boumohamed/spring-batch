package me.bouzri.firstbatch.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    private String personId = UUID.randomUUID().toString();
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate creationDate = LocalDate.now();
}
