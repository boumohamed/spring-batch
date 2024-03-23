package me.bouzri.firstbatch.data.repositories;

import me.bouzri.firstbatch.data.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
