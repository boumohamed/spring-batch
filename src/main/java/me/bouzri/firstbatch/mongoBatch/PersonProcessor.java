package me.bouzri.firstbatch.mongoBatch;

import me.bouzri.firstbatch.data.entities.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person,Person> {

    @Override
    public Person process(Person student) {
        return student;
    }
}