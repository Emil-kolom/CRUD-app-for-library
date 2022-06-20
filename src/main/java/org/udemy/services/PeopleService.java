package org.udemy.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udemy.models.Person;
import org.udemy.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Person> findByFullName(Person person){
        return peopleRepository.findByFullName(person.getFullName());
    }

    @Transactional(readOnly = true)
    public List<Person> list(){
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person findById(Integer id){
        Person person = peopleRepository.findById(id).orElse(new Person());
        Hibernate.initialize(person.getBookList());
        return person;
    }

    public void save(Person person){
        peopleRepository.save(person);
    }

    public void update(Integer id, Person updatePerson){
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if(!optionalPerson.isPresent())
            return;

        Person person = optionalPerson.get();
        person.setFullName(updatePerson.getFullName());
        person.setYearBirth(updatePerson.getYearBirth());
    }

    public void deleteById(Integer id){
        peopleRepository.deleteById(id);
    }
}
