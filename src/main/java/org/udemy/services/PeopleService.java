package org.udemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udemy.models.Person;
import org.udemy.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findByFullName(Person person){
        return peopleRepository.findByFullName(person.getFullName());
    }

    public Person findById(Integer id){
        return peopleRepository.findById(id).orElse(null);
    }
}
